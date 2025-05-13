package com.example.avantitigestiondeincidencias.DataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStore(private val context: Context) {

    companion object
    {
        val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore("app_avanti_prefs")
        // Nombre de las variables
        val TIPO_USUARIO = intPreferencesKey("tipoUsuario")
        val JSON = stringPreferencesKey("json")
        val SESION_ABIERTA = booleanPreferencesKey("sesionAbierta")
    }

    //Metodos para almacenar los datos en las variables
    suspend fun setData(tipo: Int, json: String, sesionAbierta: Boolean)
    {
        context.dataStore.edit {

            it[TIPO_USUARIO] = tipo
            it[JSON] = json
            it[SESION_ABIERTA] = sesionAbierta

        }
    }

    // Metodos para obtener los datos en las variables
    suspend fun getTipoUsuario(): Int
    {
        return context.dataStore.data.map{
            it[TIPO_USUARIO] ?: -1
        }.first()
    }

    suspend fun getJson(): String
    {
        return context.dataStore.data.map {
            it[JSON] ?: ""
        }.first()
    }

    suspend fun getSesionAbierta(): Boolean
    {
        return context.dataStore.data.map {
            it[SESION_ABIERTA] ?: false
        }.first()
    }

}