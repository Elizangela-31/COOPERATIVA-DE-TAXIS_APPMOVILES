package com.example.cooperativarivermall.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ServicioApi {

    @GET("servicios")
    suspend fun obtenerServicios():
        Response<List<ServicioDto>>

    @POST("servicios")
    suspend fun registrarServicio(
        @Body servicio: CrearServicioRequest
    ): Response<ServicioDto>
}
