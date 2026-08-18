package com.example.cooperativarivermall.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore:
    DataStore<Preferences> by preferencesDataStore(
    name = "configuracion"
)

class ConfiguracionRepository(
    private val context: Context
) {
    private companion object {
        val MODO_OSCURO =
            booleanPreferencesKey("modo_oscuro")
    }

    val modoOscuro: Flow<Boolean> =
        context.dataStore.data
            .catch { error ->
                if (error is IOException) {
                    emit(
                        androidx.datastore.preferences.core
                            .emptyPreferences()
                    )
                } else {
                    throw error
                }
            }
            .map { preferencias ->
                preferencias[MODO_OSCURO] ?: false
            }

    suspend fun guardarModoOscuro(
        activado: Boolean
    ) {
        context.dataStore.edit { preferencias ->
            preferencias[MODO_OSCURO] = activado
        }
    }
}
