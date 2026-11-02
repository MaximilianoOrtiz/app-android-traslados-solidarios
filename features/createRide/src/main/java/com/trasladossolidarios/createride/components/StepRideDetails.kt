package com.trasladossolidarios.createride.components

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.trasladossolidarios.createride.CreateRideUiState
import com.trasladossolidarios.domain.model.Location
import com.trasladossolidarios.ui.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepRideDetails(
    uiState: CreateRideUiState,
    originAddress: String,
    originSuggestions: List<Location>,
    onOriginSuggestionSelected: (Location) -> Unit,
    destinationAddress: String,
    destinationSuggestions: List<Location>,
    date: String,
    time: String,
    observations: String,
    isLoadingLocation: Boolean,
    onOriginAddressChange: (String) -> Unit,
    onDestinationAddressChange: (String) -> Unit,
    onDestinationSuggestionSelected: (Location) -> Unit,
    onDateChange: (String) -> Unit,
    onTimeChange: (String) -> Unit,
    onObservationsChange: (String) -> Unit,
    onFetchCurrentLocation: () -> Unit,
    modifier: Modifier = Modifier,
    onNext: () -> Unit,
    previousStep: () -> Unit,

) {
    val focusManager = LocalFocusManager.current

    DisposableEffect(Unit) {
        onDispose { focusManager.clearFocus() }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (isGranted) onFetchCurrentLocation()
    }

    var isOriginDropdownExpanded by rememberSaveable { mutableStateOf(false) }
    var isDestinationDropdownExpanded by rememberSaveable { mutableStateOf(false) }

    // Estados para controlar la visibilidad de los diálogos
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var showTimePicker by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- 1. Origen (Obligatorio) ---
        ExposedDropdownMenuBox(
            expanded = isOriginDropdownExpanded && originSuggestions.isNotEmpty(),
            onExpandedChange = { isOriginDropdownExpanded = it }
        ) {
            OutlinedTextField(
                value = originAddress,
                onValueChange = {
                    onOriginAddressChange(it)
                    isOriginDropdownExpanded = true
                },
                label = { Text("Origen del traslado *") },
                placeholder = { Text("Ej: Av. Calchaquí 1400 o tocar GPS...") },//fix
                isError = originAddress.isBlank(),
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                singleLine = true,
                trailingIcon = {
                    if (isLoadingLocation) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        IconButton(onClick = {
                            locationPermissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        }) {
                            Icon(Icons.Default.MyLocation, contentDescription = "Ubicación actual")
                        }
                    }
                }
            )

            ExposedDropdownMenu(
                expanded = isOriginDropdownExpanded && originSuggestions.isNotEmpty(),
                onDismissRequest = { isOriginDropdownExpanded = false }
            ) {
                originSuggestions.forEach { suggestion ->
                    DropdownMenuItem(
                        text = { Text(suggestion.address ?: "Ubicación sin nombre") },
                        onClick = {
                            onOriginSuggestionSelected(suggestion)
                            isOriginDropdownExpanded = false
                        }
                    )
                }
            }
        }

        // --- 2. Destino (Obligatorio) ---
        ExposedDropdownMenuBox(
            expanded = isDestinationDropdownExpanded && destinationSuggestions.isNotEmpty(),
            onExpandedChange = { isDestinationDropdownExpanded = it }
        ) {
            OutlinedTextField(
                value = destinationAddress,
                onValueChange = {
                    onDestinationAddressChange(it)
                    isDestinationDropdownExpanded = true
                },
                label = { Text("Destino del traslado *") },
                placeholder = { Text("Buscar calle, localidad o refugio...") },
                isError = destinationAddress.isBlank(),
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                singleLine = true,
                trailingIcon = {
                    Icon(Icons.Default.LocationOn, contentDescription = "Buscar destino")
                }
            )

            ExposedDropdownMenu(
                expanded = isDestinationDropdownExpanded && destinationSuggestions.isNotEmpty(),
                onDismissRequest = { isDestinationDropdownExpanded = false }
            ) {
                destinationSuggestions.forEach { suggestion ->
                    DropdownMenuItem(
                        text = { Text(suggestion.address ?: "Ubicación sin nombre") },
                        onClick = {
                            onDestinationSuggestionSelected(suggestion)
                            isDestinationDropdownExpanded = false
                        }
                    )
                }
            }
        }

        // --- 3. Fecha con Calendario (DatePicker) ---
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = date,
                onValueChange = {},
                readOnly = true,
                enabled = false,
                label = { Text("Fecha del traslado *") },
                placeholder = { Text("Seleccionar en el calendario") },
                isError = date.isBlank(),
                trailingIcon = {
                    Icon(
                        Icons.Default.CalendarMonth,
                        contentDescription = "Calendario"
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = if (date.isBlank()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            )
            // Capa transparente para capturar el click sobre todo el campo
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { showDatePicker = true }
            )
        }

        // --- 4. Hora con Reloj (TimePicker) ---
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = time,
                onValueChange = {},
                readOnly = true,
                enabled = false,
                label = { Text("Hora aproximada *") },
                placeholder = { Text("Seleccionar en el reloj") },
                isError = time.isBlank(),
                trailingIcon = { Icon(Icons.Default.AccessTime, contentDescription = "Reloj") },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = if (time.isBlank()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { showTimePicker = true }
            )
        }

        // --- 5. Observaciones ---
        OutlinedTextField(
            value = observations,
            onValueChange = onObservationsChange,
            label = { Text("Observaciones extras (opcional)") },
            placeholder = { Text("Ej: Llevar canil mediano, el perro es tranquilo...") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(AppTheme.spacing.md))

        // --- 6. Botones de Navegación ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm)
        ) {
            OutlinedButton(
                onClick = previousStep,
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp),
                shape = AppTheme.shapes.large
            ) {
                Text(
                    text = "← Anterior",
                    style = AppTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = onNext,
                enabled = uiState.isStep1Valid,
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp),
                shape = AppTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.primary,
                    contentColor = AppTheme.colors.onPrimary
                )
            ) {
                Text(
                    text = "Continuar →",//fix
                    style = AppTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    // Diálogo de Calendario (DatePicker)
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        sdf.timeZone = TimeZone.getTimeZone("UTC")
                        onDateChange(sdf.format(Date(millis)))
                    }
                    showDatePicker = false
                }) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // Diálogo de Reloj (TimePicker)
    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(is24Hour = true)
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", timePickerState.hour, timePickerState.minute)
                    onTimeChange(formattedTime)
                    showTimePicker = false
                }) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("Cancelar") }
            },
            text = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    TimePicker(state = timePickerState)
                }
            }
        )
    }
}