package com.example.cooperativarivermall.ui.pantallas

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.cooperativarivermall.ubicacion.UbicacionManager

@Composable
fun PantallaRegistrarServicio(
    mensajeViewModel: String?,
    onGuardar: (
        clienteId: Int,
        conductorId: Int,
        taxiId: Int,
        origen: String,
        destino: String,
        fecha: String,
        hora: String,
        valor: Double
    ) -> Unit,
    onLimpiarMensaje: () -> Unit,
    onRegresar: () -> Unit
) {
    val context = LocalContext.current

    val ubicacionManager = remember {
        UbicacionManager(context)
    }

    var clienteId by remember { mutableStateOf("") }
    var conductorId by remember { mutableStateOf("") }
    var taxiId by remember { mutableStateOf("") }
    var origen by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var mensajeUbicacion by remember {
        mutableStateOf("")
    }

    fun obtenerUbicacion() {
        mensajeUbicacion = "Obteniendo ubicación..."

        ubicacionManager.obtenerUbicacionActual(
            onResultado = { latitud, longitud ->
                origen = "$latitud, $longitud"
                mensajeUbicacion =
                    "Ubicación obtenida correctamente"
            },
            onError = { mensaje ->
                mensajeUbicacion = mensaje
            }
        )
    }

    val solicitarPermisos =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts
                    .RequestMultiplePermissions()
        ) { permisos ->
            val permisoConcedido =
                permisos[
                    Manifest.permission
                        .ACCESS_FINE_LOCATION
                ] == true ||
                    permisos[
                        Manifest.permission
                            .ACCESS_COARSE_LOCATION
                    ] == true

            if (permisoConcedido) {
                obtenerUbicacion()
            } else {
                mensajeUbicacion =
                    "Permiso de ubicación rechazado"
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Registrar servicio",
            fontSize = 29.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF123B66)
        )

        Text(
            text = "Ingrese los datos de la carrera"
        )

        Spacer(modifier = Modifier.height(18.dp))

        CampoNumero(
            valor = clienteId,
            etiqueta = "ID del cliente",
            onCambio = { clienteId = it }
        )

        CampoNumero(
            valor = conductorId,
            etiqueta = "ID del conductor",
            onCambio = { conductorId = it }
        )

        CampoNumero(
            valor = taxiId,
            etiqueta = "ID del taxi",
            onCambio = { taxiId = it }
        )

        OutlinedTextField(
            value = origen,
            onValueChange = { origen = it },
            label = { Text("Lugar de origen") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = {
                val permisoFino =
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission
                            .ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED

                val permisoAproximado =
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission
                            .ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED

                if (permisoFino || permisoAproximado) {
                    obtenerUbicacion()
                } else {
                    solicitarPermisos.launch(
                        arrayOf(
                            Manifest.permission
                                .ACCESS_FINE_LOCATION,
                            Manifest.permission
                                .ACCESS_COARSE_LOCATION
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Usar mi ubicación actual")
        }

        if (mensajeUbicacion.isNotEmpty()) {
            Text(
                text = mensajeUbicacion,
                color = Color(0xFF123B66),
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }

        OutlinedTextField(
            value = destino,
            onValueChange = { destino = it },
            label = { Text("Lugar de destino") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha: 2026-07-24") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = hora,
            onValueChange = { hora = it },
            label = { Text("Hora: 14:30") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = valor,
            onValueChange = { valor = it },
            label = { Text("Valor del servicio") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {
                onLimpiarMensaje()

                onGuardar(
                    clienteId.toIntOrNull() ?: 0,
                    conductorId.toIntOrNull() ?: 0,
                    taxiId.toIntOrNull() ?: 0,
                    origen,
                    destino,
                    fecha,
                    hora,
                    valor.toDoubleOrNull() ?: 0.0
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar servicio")
        }

        if (mensajeViewModel != null) {
            Text(
                text = mensajeViewModel,
                color = Color(0xFF148A35),
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }

        OutlinedButton(
            onClick = onRegresar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar y regresar")
        }

        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
private fun CampoNumero(
    valor: String,
    etiqueta: String,
    onCambio: (String) -> Unit
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        label = { Text(etiqueta) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(10.dp))
}
