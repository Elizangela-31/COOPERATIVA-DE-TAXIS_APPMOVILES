package com.example.cooperativarivermall.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cooperativarivermall.data.local.ServicioEntity
import com.example.cooperativarivermall.data.repository.ServicioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface EstadoServiciosUi {
    data object Cargando : EstadoServiciosUi
    data object Exito : EstadoServiciosUi

    data class Error(
        val mensaje: String
    ) : EstadoServiciosUi
}

class ServicioViewModel(
    private val repository: ServicioRepository
) : ViewModel() {

    val servicios: StateFlow<List<ServicioEntity>> =
        repository.servicios.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _estado =
        MutableStateFlow<EstadoServiciosUi>(
            EstadoServiciosUi.Cargando
        )

    val estado: StateFlow<EstadoServiciosUi> =
        _estado.asStateFlow()

    private val _mensaje =
        MutableStateFlow<String?>(null)

    val mensaje: StateFlow<String?> =
        _mensaje.asStateFlow()

    init {
        cargarServicios()
    }

    fun cargarServicios() {
        viewModelScope.launch {
            _estado.value = EstadoServiciosUi.Cargando

            repository.actualizarDesdeApi()
                .onSuccess {
                    _estado.value = EstadoServiciosUi.Exito
                }
                .onFailure { error ->
                    /*
                     * Aunque falle internet, Room puede mostrar
                     * los servicios guardados localmente.
                     */
                    _estado.value = EstadoServiciosUi.Error(
                        mensaje = error.message
                            ?: "Error de conexión"
                    )
                }
        }
    }

    fun registrarServicio(
        clienteId: Int,
        conductorId: Int,
        taxiId: Int,
        origen: String,
        destino: String,
        fecha: String,
        hora: String,
        valor: Double
    ) {
        if (
            clienteId <= 0 ||
            conductorId <= 0 ||
            taxiId <= 0 ||
            origen.isBlank() ||
            destino.isBlank() ||
            fecha.isBlank() ||
            hora.isBlank() ||
            valor <= 0
        ) {
            _mensaje.value =
                "Complete correctamente todos los campos"
            return
        }

        val nuevoServicio = ServicioEntity(
            clienteId = clienteId,
            conductorId = conductorId,
            taxiId = taxiId,
            origen = origen.trim(),
            destino = destino.trim(),
            fecha = fecha.trim(),
            hora = hora.trim(),
            valor = valor,
            estado = "Pendiente"
        )

        viewModelScope.launch {
            _estado.value = EstadoServiciosUi.Cargando

            repository.registrarServicio(nuevoServicio)
                .onSuccess {
                    _estado.value = EstadoServiciosUi.Exito
                    _mensaje.value =
                        "Servicio registrado correctamente"
                }
                .onFailure {
                    /*
                     * El dato quedó guardado en Room aunque
                     * la sincronización con Laravel haya fallado.
                     */
                    _estado.value = EstadoServiciosUi.Error(
                        "Guardado localmente. Sin conexión al servidor."
                    )

                    _mensaje.value =
                        "Servicio guardado en el teléfono"
                }
        }
    }

    fun limpiarMensaje() {
        _mensaje.value = null
    }
}

class ServicioViewModelFactory(
    private val repository: ServicioRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (
            modelClass.isAssignableFrom(
                ServicioViewModel::class.java
            )
        ) {
            return ServicioViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "ViewModel desconocido"
        )
    }
}
