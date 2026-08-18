package com.example.cooperativarivermall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cooperativarivermall.data.local.AppDatabase
import com.example.cooperativarivermall.data.preferences.ConfiguracionRepository
import com.example.cooperativarivermall.data.remote.RetrofitClient
import com.example.cooperativarivermall.data.repository.ServicioRepository
import com.example.cooperativarivermall.ui.pantallas.PantallaConfiguracion
import com.example.cooperativarivermall.ui.pantallas.PantallaInicio
import com.example.cooperativarivermall.ui.pantallas.PantallaRegistrarServicio
import com.example.cooperativarivermall.ui.pantallas.PantallaServicios
import com.example.cooperativarivermall.ui.theme.CooperativaRiverMallTheme
import com.example.cooperativarivermall.viewmodel.ConfiguracionViewModel
import com.example.cooperativarivermall.viewmodel.ConfiguracionViewModelFactory
import com.example.cooperativarivermall.viewmodel.ServicioViewModel
import com.example.cooperativarivermall.viewmodel.ServicioViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val baseDatos = AppDatabase.obtenerInstancia(
            applicationContext
        )

        val servicioRepository = ServicioRepository(
            servicioDao = baseDatos.servicioDao(),
            servicioApi = RetrofitClient.servicioApi
        )

        val configuracionRepository =
            ConfiguracionRepository(
                applicationContext
            )

        val servicioFactory =
            ServicioViewModelFactory(
                servicioRepository
            )

        val configuracionFactory =
            ConfiguracionViewModelFactory(
                configuracionRepository
            )

        setContent {
            val servicioViewModel:
                ServicioViewModel = viewModel(
                factory = servicioFactory
            )

            val configuracionViewModel:
                ConfiguracionViewModel = viewModel(
                factory = configuracionFactory
            )

            val modoOscuro by
            configuracionViewModel
                .modoOscuro
                .collectAsState()

            CooperativaRiverMallTheme(
                darkTheme = modoOscuro
            ) {
                AplicacionCooperativa(
                    servicioViewModel =
                        servicioViewModel,
                    configuracionViewModel =
                        configuracionViewModel
                )
            }
        }
    }
}

@Composable
fun AplicacionCooperativa(
    servicioViewModel: ServicioViewModel,
    configuracionViewModel: ConfiguracionViewModel
) {
    val navController = rememberNavController()

    val servicios by
    servicioViewModel
        .servicios
        .collectAsState()

    val estado by
    servicioViewModel
        .estado
        .collectAsState()

    val mensaje by
    servicioViewModel
        .mensaje
        .collectAsState()

    val modoOscuro by
    configuracionViewModel
        .modoOscuro
        .collectAsState()

    Scaffold { espacioInterior ->
        Box(
            modifier = Modifier.padding(
                espacioInterior
            )
        ) {
            NavHost(
                navController = navController,
                startDestination = Rutas.INICIO
            ) {
                composable(Rutas.INICIO) {
                    PantallaInicio(
                        onServicios = {
                            navController.navigate(
                                Rutas.SERVICIOS
                            )
                        },
                        onConfiguracion = {
                            navController.navigate(
                                Rutas.CONFIGURACION
                            )
                        }
                    )
                }

                composable(Rutas.SERVICIOS) {
                    PantallaServicios(
                        servicios = servicios,
                        estado = estado,
                        onActualizar = {
                            servicioViewModel
                                .cargarServicios()
                        },
                        onRegistrar = {
                            navController.navigate(
                                Rutas.REGISTRAR_SERVICIO
                            )
                        },
                        onRegresar = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(
                    Rutas.REGISTRAR_SERVICIO
                ) {
                    PantallaRegistrarServicio(
                        mensajeViewModel = mensaje,
                        onGuardar = {
                                clienteId,
                                conductorId,
                                taxiId,
                                origen,
                                destino,
                                fecha,
                                hora,
                                valor ->

                            servicioViewModel
                                .registrarServicio(
                                    clienteId = clienteId,
                                    conductorId = conductorId,
                                    taxiId = taxiId,
                                    origen = origen,
                                    destino = destino,
                                    fecha = fecha,
                                    hora = hora,
                                    valor = valor
                                )
                        },
                        onLimpiarMensaje = {
                            servicioViewModel
                                .limpiarMensaje()
                        },
                        onRegresar = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(
                    Rutas.CONFIGURACION
                ) {
                    PantallaConfiguracion(
                        modoOscuro = modoOscuro,
                        onCambiarModoOscuro = {
                            configuracionViewModel
                                .cambiarModoOscuro(it)
                        },
                        onRegresar = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
