package auth

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import model.User
import repository.AuthRepository

class FirebaseAuthRepository(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) : AuthRepository {

    override suspend fun register(email: String,password: String): Result<User> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            val  firebaseUser = result.user
                ?: return Result.failure(IllegalStateException("No se pudo crear el usuario"))
            Result.success(firebaseUser.toDomain())
            //si la pantalla se cierra las corrutinas se cancelan y lanzan la excepción
        } catch (e: CancellationException) {throw e}
        catch (e: Exception) {
            Result.failure(Exception(e.toFriendlyMessage()))
        }
    }

    override suspend fun logIn(email: String, password: String): Result<User> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user
                ?: return Result.failure(IllegalStateException("No se pudo iniciar sesión"))
            Result.success(firebaseUser.toDomain())
        } catch (e: CancellationException) {throw e}
        catch (e : Exception) {
            Result.failure(Exception(e.toFriendlyMessage()))
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUser(): User? = firebaseAuth.currentUser?.toDomain()

    //mapea un objeto de la Firebase a User y asi ninguna otra capa conoce a Firebase
    private fun FirebaseUser.toDomain() = User(
        id = uid,
        email = email.orEmpty()
    )

    private fun Exception.toFriendlyMessage(): String = when (this) {
        is FirebaseAuthWeakPasswordException -> "Contraseña demasiado débil"
        is FirebaseAuthUserCollisionException -> "Ya existe una cuenta con ese email"
        is FirebaseAuthInvalidUserException,
        is FirebaseAuthInvalidCredentialsException -> "Email o contraseña incorrectos"
        is FirebaseNetworkException -> "Sin conexión a internet"
        else -> "ocurrió un error inesperado"
    }
}