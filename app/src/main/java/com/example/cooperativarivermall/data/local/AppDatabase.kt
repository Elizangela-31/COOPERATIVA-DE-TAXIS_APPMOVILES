package com.example.cooperativarivermall.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ServicioEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun servicioDao(): ServicioDao

    companion object {
        @Volatile
        private var INSTANCIA: AppDatabase? = null

        fun obtenerInstancia(context: Context): AppDatabase {
            return INSTANCIA ?: synchronized(this) {
                val nuevaInstancia = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cooperativa_database"
                ).build()

                INSTANCIA = nuevaInstancia
                nuevaInstancia
            }
        }
    }
}
