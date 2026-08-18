package com.example.cooperativarivermall.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servicios")
data class ServicioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val clienteId: Int,
    val conductorId: Int,
    val taxiId: Int,
    val origen: String,
    val destino: String,
    val fecha: String,
    val hora: String,
    val valor: Double,
    val estado: String = "Pendiente",
    val sincronizado: Boolean = false
)
