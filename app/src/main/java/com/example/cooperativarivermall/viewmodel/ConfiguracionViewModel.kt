package com.example.cooperativarivermall.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cooperativarivermall.data.preferences.ConfiguracionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ConfiguracionViewModel(
    private val repository: ConfiguracionRepository
) : ViewModel() {

    val modoOscuro: StateFlow<Boolean> =
        repository.modoOscuro.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun cambiarModoOscuro(activado: Boolean) {
        viewModelScope.launch {
            repository.guardarModoOscuro(activado)
        }
    }
}

class ConfiguracionViewModelFactory(
    private val repository: ConfiguracionRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (
            modelClass.isAssignableFrom(
                ConfiguracionViewModel::class.java
            )
        ) {
            return ConfiguracionViewModel(
                repository
            ) as T
        }

        throw IllegalArgumentException(
            "ViewModel desconocido"
        )
    }
}
