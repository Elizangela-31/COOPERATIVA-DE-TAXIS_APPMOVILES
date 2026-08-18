package com.example.cooperativarivermall.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cooperativarivermall.R

data class OpcionInicio(
    val titulo: String,
    val descripcion: String,
    val accion: () -> Unit
)

@Composable
fun PantallaInicio(
    onServicios: () -> Unit,
    onConfiguracion: () -> Unit
) {
    val opciones = listOf(
        OpcionInicio(
            titulo = "Servicios",
            descripcion = "Consultar y registrar carreras",
            accion = onServicios
        ),
        OpcionInicio(
            titulo = "Configuración",
            descripcion = "Preferencias de la aplicación",
            accion = onConfiguracion
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 25.dp,
                        bottom = 18.dp
                    ),
                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.logo_cooperativa
                    ),
                    contentDescription =
                        "Logo de la Cooperativa River Mall",
                    modifier = Modifier
                        .size(215.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Text(
                    text = "COOPERATIVA DE TAXIS",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF123B66),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "RIVER MALL",
                    fontSize = 31.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFF2B705)
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text =
                        "Sistema móvil de gestión de servicios",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        items(opciones) { opcion ->
            Card(
                onClick = opcion.accion,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F7FA)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(22.dp)
                ) {
                    Text(
                        text = opcion.titulo,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF123B66)
                    )

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    Text(
                        text = opcion.descripcion,
                        fontSize = 15.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }

        item {
            Text(
                text = "Sangolquí - Ecuador",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 25.dp),
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }
    }
}
