package com.example.cooperativarivermall.data.remote

import com.google.gson.annotations.SerializedName

data class ServicioDto(
    val id: Int,

    @SerializedName("cliente_id")
    val clienteId: Int,

    @SerializedName("conductor_id")
    val conductorId: Int,

    @SerializedName("taxi_id")
    val taxiId: Int,

    val origen: String,
    val destino: String,
    val fecha: String,
    val hora: String,
    val valor: Double,
    val estado: String
)

data class CrearServicioRequest(
    @SerializedName("cliente_id")
    val clienteId: Int,

    @SerializedName("conductor_id")
    val conductorId: Int,

    @SerializedName("taxi_id")
    val taxiId: Int,

    val origen: String,
    val destino: String,
    val fecha: String,
    val hora: String,
    val valor: Double,

    val estado: String = "Pendiente"
)
