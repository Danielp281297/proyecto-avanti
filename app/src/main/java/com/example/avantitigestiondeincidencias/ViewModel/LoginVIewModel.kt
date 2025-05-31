package com.example.avantitigestiondeincidencias.ViewModel

import androidx.lifecycle.ViewModel
import com.example.avantitigestiondeincidencias.AVANTI.Usuario
import com.example.avantitigestiondeincidencias.Supabase.EmpleadoRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

// Se van a usar los FLows para interactuar con los estados
class LoginViewModel: ViewModel() {

    private val _nombreUsuario = MutableStateFlow("")
    val nombreUsuario: StateFlow<String> = _nombreUsuario.asStateFlow()

    private val _contrasenaUsuario = MutableStateFlow("")
    val contrasena: StateFlow<String> = _contrasenaUsuario.asStateFlow()

    fun setNombreUsuario(entrada: String){
        if (entrada.all { !it.isWhitespace() && it!='\'' && it!='\"' && it!='=' && it.isLetter() || it.isDigit() } && entrada.length <= 20)
            _nombreUsuario.value = entrada
    }
    fun setContrasena(entrada: String){
        if (entrada.all { !it.isWhitespace()  && it!='\'' && it!='\"' && it!='=' } && entrada.length <= 20)
            _contrasenaUsuario.value = entrada
    }

    suspend fun validarDatosUsuario(): Usuario?
    {

        val usuario: Usuario? = EmpleadoRequest().obtenerDatosUsuario(nombreUsuario.value, contrasena.value)

        return usuario

    }

    suspend fun obtenerDatosEmpleado(usuario: Usuario, resultados: (String) -> Unit)
    {

        var json = " "

        when(usuario.idTipoUsuario)
        {

            1 -> {
                var tecnico = EmpleadoRequest().seleccionarTecnicobyUsuarioId(usuario.id)
                // Se crea el objeto json
                json = Json { ignoreUnknownKeys = true }.encodeToString(tecnico)
            }

            2 -> {
                var clienteInterno = EmpleadoRequest().seleccionarClienteInternobyUsuarioId(usuario.id)

                json = Json { ignoreUnknownKeys = true }.encodeToString(clienteInterno)
            }

            3 ->{
                var administrador = EmpleadoRequest().seleccionarTecnicobyUsuarioId(usuario.id)

                json = Json { ignoreUnknownKeys = true }.encodeToString(administrador)
            }
        }

        resultados(json)
    }

}