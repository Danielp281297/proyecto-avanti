package com.example.avantitigestiondeincidencias.ViewModel

import android.util.Log
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.AVANTI.Departamento
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.AVANTI.TelefonoEmpleado
import com.example.avantitigestiondeincidencias.Supabase.EmpleadoRequest
import com.example.avantitigestiondeincidencias.Supabase.TelefonoRequest
import com.example.avantitigestiondeincidencias.Supabase.UsuarioRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CambiarDatosUsuarioViewModel: UsuarioFormularioViewModel() {

    private fun editarPerfilDatosIncorrectos(): String
    {
        return if(!validarCedula())
            "Cédula inválida, por favor intente de nuevo"
        else if (!validarPrimerNombre())
            "Por favor, ingrese un primer nombre válido"
        else if (!validarSegundoNombre())
            "Por favor, ingrese un segundo nombre válido"
        else if (!validarPrimerApellido())
            "Por favor, ingrese un primer apellido válido"
        else if (!validarSegundoApellido())
            "Por favor, ingrese un segundo apellido válido"
        else if(!validarCorreo())
            "Correo electrónico inválido, por favor intente de nuevo"
        else if(!validarIdCodigoOperadoraTelefono() || !validarExtensionTelefono())
            "Por favor, ingrese un número de teléfono válido"

        else if (idTipoUsuario.value == 2 && !validarIdDepartamento())
            "Por favor, ingrese un departamento"

        else if(!validarIdCargo())
            "Por favor, ingrese un cargo laboral"
        else if(idTipoUsuario.value == 1 && !validarIdGrupoAtencion())
            "Por favor, ingrese el grupo de atención"
        else if(!validarNombreUsuario())
            "Por favor, ingrese un nombre de usuario válido"
        else
            ""

    }

    fun setMensajeErrorEditarPerfil() { _mensajeError.value = editarPerfilDatosIncorrectos() }

    suspend fun validarDatosUsuarioActualizar(idEmpleado: Int,
                                              idTelefonoEmpleado: Int,
                                              idUsuario: Int,
                                              resultados: (String) -> Unit)
    {

        val numeroTelefono = TelefonoEmpleado(
            idCodigoOperadoraTelefono = idCodigoOperadoraTelefono.value,
            extension = extensionTelefono.value,
        )

        var repetido = ""

        // Se comprueba que haya alguna congruencia pero con diferentes identificadores. De ser cierto, se retorna un valor para mostrar el mensaje
        // Hay que ser valores nulleables, para evitar problemas al generar la consulta, OJO...
        UsuarioRequest().seleccionarUsuarioByNombre(nombreUsuario.value){
            if (it != null && (it.nombre == nombreUsuario.value && it.id != idUsuario))
                repetido = "El nombre de usuario ya existe"
        }

        EmpleadoRequest().buscarEmpleadoByCorreoElectronico(correoElectronico.value){
            if (it != null && (it.correoElectronico == correoElectronico.value && it.id != idEmpleado)) {
                Log.d("CORREO ELECTRONICO", it.toString())
                repetido = "El correo electrónico ya existe"
            }
        }

        EmpleadoRequest().buscarEmpleadoByCedula(cedula.value.toInt()){
            if (it != null && (it.cedula == cedula.value.toInt() && it.id != idEmpleado))
                repetido = "El número de cédula ya existe"
        }

        TelefonoRequest().buscarTelefonoEmpleado(numeroTelefono){

            if(it != null && (
                        it.idCodigoOperadoraTelefono == numeroTelefono.idCodigoOperadoraTelefono &&
                                it.extension == numeroTelefono.extension &&
                                it.id != idTelefonoEmpleado))
            {
                repetido = "El número de teléfono ya existe"
            }

        }

        resultados(repetido)

    }

    suspend fun actualizarDatos(empleado: Empleado){

        crearUsuario(
            accionTecnico = { tecnico ->
                EmpleadoRequest().actualizarDatosTecnico(empleado, tecnico)
            },
            accionClienteInterno = { clienteInterno ->
                val clienteAnterior = ClienteInterno(
                    idEmpleado = empleado.id,
                    empleado = empleado
                )
                EmpleadoRequest().actualizarDatosClienteInterno(clienteAnterior, clienteInterno)
            }
        )

    }

}