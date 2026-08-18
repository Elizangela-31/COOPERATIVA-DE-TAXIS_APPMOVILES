package com.example.cooperativarivermall.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ServicioDao {

    @Query("SELECT * FROM servicios ORDER BY id DESC")
    fun obtenerServicios(): Flow<List<ServicioEntity>>

    @Query("SELECT * FROM servicios WHERE id = :id")
    suspend fun obtenerPorId(id: Int): ServicioEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardar(servicio: ServicioEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarTodos(
        servicios: List<ServicioEntity>
    )

    @Update
    suspend fun actualizar(servicio: ServicioEntity)

    @Delete
    suspend fun eliminar(servicio: ServicioEntity)

    @Query("DELETE FROM servicios")
    suspend fun eliminarTodos()
}
