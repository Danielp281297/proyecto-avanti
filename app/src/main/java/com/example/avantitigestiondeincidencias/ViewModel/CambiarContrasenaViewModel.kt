package com.example.avantitigestiondeincidencias.ViewModel

import com.example.avantitigestiondeincidencias.AVANTI.Usuario
import com.example.avantitigestiondeincidencias.Supabase.UsuarioRequest
import com.example.avantitigestiondeincidencias.ui.screens.componentes.SHA512

class CambiarContrasenaViewModel: UsuarioFormularioViewModel() {


    private fun datosContrasenaIncorrectos(): String
    {
         return if(!validarContrasena())
            "Contraseña inválida, por favor intente de nuevo"
        else if(!validarConfirmarContrasena())
            "Las contraseñas no coinciden, por favor intente de nuevo"
        else
            ""

    }

    fun setCambiarContrasenaMensajeError() { _mensajeError.value = datosContrasenaIncorrectos() }

    suspend fun cambiarContrasenaUsuario(resultado: (String) -> Unit)
    {

        val usuario = Usuario (nombre = nombreUsuario.value, password = (SHA512(contrasenaUsuario.value)))
        UsuarioRequest().cambiarContrasenaUsuario(usuario)
        {

            if (it != null)
                resultado("La contraseña fue cambiada con éxito.")
            else
                resultado("La contraseña no fue cambiada. Intentelo de nuevo.")

        }

    }

}