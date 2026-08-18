package com.example.cooperativarivermall.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cooperativarivermall.data.local.ServicioEntity
import com.example.cooperativarivermall.viewmodel.EstadoServiciosUi

@Composable
fun PantallaServicios(
    servicios: List<ServicioEntity>,
    estado: EstadoServiciosUi,
    onActualizar: () -> Unit,
    onRegistrar: () -> Unit,
    onRegresar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Servicios",
            fontSize = 29.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF123B66)
        )

        Text(
            text = "Carreras registradas en la cooperativa"
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = onRegistrar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar nuevo servicio")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onActualizar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Actualizar desde Laravel")
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (estado) {
            EstadoServiciosUi.Cargando -> {
                if (servicios.isEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            is EstadoServiciosUi.Error -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFE9E7)
                    )
                ) {
                    Text(
                        text = estado.mensaje,
                        modifier = Modifier.padding(12.dp),
                        color = Color(0xFFB3261E)
                    )
                }
            }

            EstadoServiciosUi.Exito -> Unit
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (
            servicios.isEmpty() &&
            estado !is EstadoServiciosUi.Cargando
        ) {
            Text(
                text = "No existen servicios registrados.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = servicios,
                key = { it.id }
            ) { servicio ->
                TarjetaServicio(servicio)
            }
        }

        OutlinedButton(
            onClick = onRegresar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Regresar al inicio")
        }
    }
}

@Composable
private fun TarjetaServicio(
    servicio: ServicioEntity
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(17.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Servicio #${servicio.id}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF123B66)
                )

                Text(
                    text = servicio.estado,
                    fontWeight = FontWeight.Bold,
                    color = colorEstado(servicio.estado)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("Origen: ${servicio.origen}")
            Text("Destino: ${servicio.destino}")
            Text("Fecha: ${servicio.fecha}")
            Text("Hora: ${servicio.hora}")

            Text(
                text = "Valor: $${servicio.valor}",
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = if (servicio.sincronizado) {
                    "Sincronizado con Laravel"
                } else {
                    "Guardado solamente en el teléfono"
                },
                fontSize = 13.sp,
                color = if (servicio.sincronizado) {
                    Color(0xFF148A35)
                } else {
                    Color(0xFFCE7B00)
                }
            )
        }
    }
}

private fun colorEstado(estado: String): Color {
    return when (estado) {
        "Finalizado" -> Color(0xFF148A35)
        "En curso" -> Color(0xFFCE7B00)
        "Cancelado" -> Color(0xFFB3261E)
        else -> Color(0xFF123B66)
    }
}
