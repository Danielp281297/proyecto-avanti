package com.example.avantitigestiondeincidencias.ViewModel

import com.example.avantitigestiondeincidencias.AVANTI.TelefonoEmpleado
import com.example.avantitigestiondeincidencias.Supabase.EmpleadoRequest
import com.example.avantitigestiondeincidencias.Supabase.TelefonoRequest
import com.example.avantitigestiondeincidencias.Supabase.UsuarioRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrearUsuarioViewModel: UsuarioFormularioViewModel() {

    // Se buscan los datos repetidos en la base de datos
    fun datosRepetidosBBDD(resultado:(String) -> Unit)
    {
        val numeroTelefono = TelefonoEmpleado(
            idCodigoOperadoraTelefono = idCodigoOperadoraTelefono.value,
            extension = extensionTelefono.value,
        )

        //Nombre de usuario
        CoroutineScope(Dispatchers.IO).launch {
            var avisoRepetido = ""


            UsuarioRequest().buscarNombreUsuario(nombreUsuario.value) {

                if (it != null)
                    avisoRepetido = "El nombre de usuario ya existe"

            }

            EmpleadoRequest().buscarCorreoElectronico(correoElectronico.value) {

                if (it != null)
                    avisoRepetido = "El correo electrónico ya existe"

            }


            EmpleadoRequest().buscarCedula(cedula.value) {

                if (it != null)
                    avisoRepetido = "El número de cédula ya existe"

            }

            TelefonoRequest().buscarTelefonoEmpleado(numeroTelefono)
            {

                if (it != null)
                    avisoRepetido = "El número de teléfono ya existe"

            }

            resultado(avisoRepetido)
        }

    }

    suspend fun guardarDatos()
    {

        crearUsuario(
            accionTecnico = { tecnico ->
                UsuarioRequest().insertarUsuarioTecnico(tecnico)

            },
            accionClienteInterno = { clienteInterno ->
                UsuarioRequest().insertarUsuarioClienteInterno(clienteInterno)
            }
        )


    }

}
