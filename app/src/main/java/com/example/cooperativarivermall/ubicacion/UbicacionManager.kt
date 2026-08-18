package com.example.cooperativarivermall.ubicacion

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class UbicacionManager(
    context: Context
) {
    private val clienteUbicacion =
        LocationServices.getFusedLocationProviderClient(
            context
        )

    @SuppressLint("MissingPermission")
    fun obtenerUbicacionActual(
        onResultado: (
            latitud: Double,
            longitud: Double
        ) -> Unit,
        onError: (String) -> Unit
    ) {
        val token = CancellationTokenSource()

        clienteUbicacion.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            token.token
        ).addOnSuccessListener { ubicacion ->
            if (ubicacion != null) {
                onResultado(
                    ubicacion.latitude,
                    ubicacion.longitude
                )
            } else {
                onError(
                    "No fue posible obtener la ubicación"
                )
            }
        }.addOnFailureListener { error ->
            onError(
                error.message
                    ?: "Error al obtener la ubicación"
            )
        }
    }
}
