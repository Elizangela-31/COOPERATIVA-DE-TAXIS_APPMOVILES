package com.example.cooperativarivermall.data.repository

import com.example.cooperativarivermall.data.local.ServicioDao
import com.example.cooperativarivermall.data.local.ServicioEntity
import com.example.cooperativarivermall.data.remote.CrearServicioRequest
import com.example.cooperativarivermall.data.remote.ServicioApi
import com.example.cooperativarivermall.data.remote.ServicioDto
import kotlinx.coroutines.flow.Flow

class ServicioRepository(
    private val servicioDao: ServicioDao,
    private val servicioApi: ServicioApi
) {

    val servicios: Flow<List<ServicioEntity>> =
        servicioDao.obtenerServicios()

    suspend fun actualizarDesdeApi(): Result<Unit> {
        return try {
            val respuesta = servicioApi.obtenerServicios()

            if (respuesta.isSuccessful) {
                val datosRemotos = respuesta.body().orEmpty()

                val datosLocales = datosRemotos.map {
                    it.aEntity()
                }

                servicioDao.guardarTodos(datosLocales)

                Result.success(Unit)
            } else {
                Result.failure(
                    Exception(
                        "Error del servidor: ${respuesta.code()}"
                    )
                )
            }
        } catch (error: Exception) {
            Result.failure(error)
        }
    }

    suspend fun registrarServicio(
        servicio: ServicioEntity
    ): Result<Unit> {
        val idLocal = servicioDao.guardar(
            servicio.copy(sincronizado = false)
        )

        return try {
            val solicitud = CrearServicioRequest(
                clienteId = servicio.clienteId,
                conductorId = servicio.conductorId,
                taxiId = servicio.taxiId,
                origen = servicio.origen,
                destino = servicio.destino,
                fecha = servicio.fecha,
                hora = servicio.hora,
                valor = servicio.valor,
                estado = servicio.estado
            )

            val respuesta =
                servicioApi.registrarServicio(solicitud)

            if (respuesta.isSuccessful) {
                val servicioRemoto = respuesta.body()

                if (servicioRemoto != null) {
                    // Actualizamos la MISMA fila local (mismo id) en vez de
                    // borrarla e insertar una nueva con el id del servidor.
                    // Si insertáramos con el id remoto tal cual, y ese id
                    // coincidiera con el id autogenerado de otro servicio ya
                    // guardado localmente, REPLACE lo sobrescribiría sin avisar.
                    servicioDao.guardar(
                        servicioRemoto
                            .aEntity()
                            .copy(id = idLocal.toInt())
                    )
                }

                Result.success(Unit)
            } else {
                Result.failure(
                    Exception(
                        "No se pudo sincronizar: " +
                            respuesta.code()
                    )
                )
            }
        } catch (error: Exception) {
            /*
             * Si no existe conexión, el servicio permanece
             * guardado en Room como no sincronizado.
             */
            Result.failure(error)
        }
    }

    suspend fun obtenerServicio(
        id: Int
    ): ServicioEntity? {
        return servicioDao.obtenerPorId(id)
    }

    suspend fun eliminarServicio(
        servicio: ServicioEntity
    ) {
        servicioDao.eliminar(servicio)
    }
}

private fun ServicioDto.aEntity(): ServicioEntity {
    return ServicioEntity(
        id = id,
        clienteId = clienteId,
        conductorId = conductorId,
        taxiId = taxiId,
        origen = origen,
        destino = destino,
        fecha = fecha,
        hora = hora,
        valor = valor,
        estado = estado,
        sincronizado = true
    )
}
