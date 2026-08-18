package com.example.cooperativarivermall.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    /*
     * Esta dirección funciona con el emulador de Android.
     * Al conectar el teléfono colocaremos la IP de la computadora.
     */
    private const val URL_BASE =
        "http://10.0.2.2:8000/api/"

    val servicioApi: ServicioApi by lazy {
        Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(ServicioApi::class.java)
    }
}
