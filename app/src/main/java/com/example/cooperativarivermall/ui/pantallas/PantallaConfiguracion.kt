package com.example.cooperativarivermall.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaConfiguracion(
    modoOscuro: Boolean,
    onCambiarModoOscuro: (Boolean) -> Unit,
    onRegresar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Configuración",
            fontSize = 29.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF123B66)
        )

        Text(
            text = "Preferencias de la aplicación"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Modo oscuro",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = if (modoOscuro) {
                            "El modo oscuro está activado"
                        } else {
                            "El modo claro está activado"
                        },
                        fontSize = 14.sp
                    )
                }

                Switch(
                    checked = modoOscuro,
                    onCheckedChange =
                        onCambiarModoOscuro
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "La selección queda guardada con DataStore " +
                "aunque cierres la aplicación.",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            onClick = onRegresar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Regresar al inicio")
        }
    }
}
