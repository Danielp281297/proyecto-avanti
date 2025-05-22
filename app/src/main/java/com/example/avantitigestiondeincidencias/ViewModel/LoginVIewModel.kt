package com.example.avantitigestiondeincidencias.ViewModel

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avantitigestiondeincidencias.AVANTI.Usuario
import com.example.avantitigestiondeincidencias.Supabase.EmpleadoRequest
import com.example.avantitigestiondeincidencias.ui.screens.usuario.enviarPantalla
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Se van a usar los FLows para interactuar con los estados
class LoginVIewModel: ViewModel() {

    private val _nombreUsuario = MutableStateFlow("")
    val nombreUsuario: StateFlow<String> = _nombreUsuario.asStateFlow()

    private val _contrasenaUsuario = MutableStateFlow("")
    val contrasena: StateFlow<String> = _contrasenaUsuario.asStateFlow()

    fun nombreUsusarioOnTextChange(entrada: String){ _nombreUsuario.value = entrada }
    fun contrasenaOnTextChange(entrada: String){ _nombreUsuario.value = entrada }



    suspend fun datosUsuarioIncorrectos(): Boolean
    {

        var bandera = false

        var usuario: Usuario?

        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                usuario =
                    EmpleadoRequest().obtenerDatosUsuario(nombreUsuario.value, contrasena.value)

                if (usuario.value != null) {

                    Log.d("USUARIO", "Encontrado")
                    enviarPantalla(usuario.value!!) { json ->
                        jsonState.value = json
                        enviarPantallaState.value = true

                    }

                } else {
                    Log.d("USUARIO", "No encontrado")
                    datosIncorrectosAlertDialogState.value = true
                }

            }
        }

        return bandera

    }

}