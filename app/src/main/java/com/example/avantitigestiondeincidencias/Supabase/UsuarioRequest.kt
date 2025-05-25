package com.example.avantitigestiondeincidencias.Supabase

import android.util.Log
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.AVANTI.InsertarEmpleado
import com.example.avantitigestiondeincidencias.AVANTI.InsertarTecnico
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.AVANTI.TelefonoEmpleado
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.AVANTI.Usuario
import com.example.avantitigestiondeincidencias.ui.screens.componentes.SHA512
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UsuarioRequest(): SupabaseClient() {


    private val columnaUsuario = Columns.raw("""
        
        id_usuario,
        nombre_usuario,
        id_tipo_usuario,
        usuario_habilitado,
        tipo_usuario(
            tipo_usuario
        )
            
    """.trimIndent())

    suspend fun insertarUsuarioClienteInterno(clienteInterno: ClienteInterno)
    {
        // Primero se crea el usuario
        val nuevoUsuario = getSupabaseClient().from("usuario").insert(clienteInterno.empleado.usuario){
            select()
        }.decodeSingle<Usuario>()

        // ... los datos del telefono
        val nuevoTelefono = getSupabaseClient().from("teléfono_empleado").insert(clienteInterno.empleado.telefonoEmpleado){
            select()
        }.decodeSingle<TelefonoEmpleado>()

        // ... los datos del empleado
        var insertarEmpleado = InsertarEmpleado()

        clienteInterno.apply {
            insertarEmpleado = InsertarEmpleado(
                cedula = empleado.cedula,
                primerNombre = empleado.primerNombre,
                segundoNombre = empleado.segundoNombre,
                primerApellido = empleado.primerApellido,
                segundoApellido = empleado.segundoApellido,
                correoElectronico = empleado.correoElectronico,
                idTeléfonoEmpleado = nuevoTelefono.id,
                idDepartamento = empleado.idDepartamento,
                idCargoEmpleado = empleado.idCargoEmpleado,
                idUsuario = nuevoUsuario.id
            )
        }

        val nuevoEmpleado = getSupabaseClient().from("empleado").insert(insertarEmpleado){
            select()
        }.decodeSingle<Empleado>()

        // Por, ultimo se crea la fila del cliente interno
        val nuevoClienteInterno = getSupabaseClient().from("cliente_interno").insert(ClienteInterno(idEmpleado = nuevoEmpleado.id)){
            select()
        }.decodeSingle<ClienteInterno>()


    }

    suspend fun insertarUsuarioTecnico(tecnico: Tecnico)
    {

        // Primero se crea el usuario
        val nuevoUsuario = getSupabaseClient().from("usuario").insert(tecnico.empleado.usuario){
            select()
        }.decodeSingle<Usuario>()

        //Log.d("NUEVO USUARIO", nuevoUsuario.toString())
        // ... los datos del telefono
        val nuevoTelefono = getSupabaseClient().from("teléfono_empleado").insert(tecnico.empleado.telefonoEmpleado){
            select()
        }.decodeSingle<TelefonoEmpleado>()

        // ... los datos del empleado

        var insertarEmpleado = InsertarEmpleado()

        tecnico.apply {
            insertarEmpleado = InsertarEmpleado(
                cedula = empleado.cedula,
                primerNombre = empleado.primerNombre,
                segundoNombre = empleado.segundoNombre,
                primerApellido = empleado.primerApellido,
                segundoApellido = empleado.segundoApellido,
                correoElectronico = empleado.correoElectronico,
                idTeléfonoEmpleado = nuevoTelefono.id,
                idDepartamento = empleado.idDepartamento,
                idCargoEmpleado = empleado.idCargoEmpleado,
                idUsuario = nuevoUsuario.id
            )
        }

        //Log.d("INSERTAR EMPLEADO", insertarEmpleado.toString())

        val nuevoEmpleado = getSupabaseClient().from("empleado").insert(insertarEmpleado){
            select()
        }.decodeSingle<Empleado>()

        //Log.d("NUEVO EMPLEADO", nuevoEmpleado.toString())

        // Por, ultimo se crea la fila del cliente interno
        val nuevoTecnico = getSupabaseClient().from("técnico").insert(
            InsertarTecnico(
                idGrupoAtencion = tecnico.idGrupoAtencion,
                idEmpleado = nuevoEmpleado.id
            )
        ){
            select()
        }.decodeSingle<InsertarTecnico>()

    }

    suspend fun borrarUsuarioTecnico(tecnico: Tecnico)
    {
        // Primero, se borra la fila del tecnico...
        getSupabaseClient().from("técnico").delete{
            filter {
                eq("id_técnico", tecnico.id)
            }
        }

        // la fila del empleado
        getSupabaseClient().from("empleado").delete{
            filter {
                eq("id_empleado", tecnico.empleado.id)
            }
        }

        // la fila del telefono
        getSupabaseClient().from("teléfono_empleado").delete{
            filter {
                eq("id_teléfono_empleado", tecnico.empleado.telefonoEmpleado.id)
            }
        }

        // la fila del usuario
        getSupabaseClient().from("usuario").delete{
            filter {
                eq("id_usuario", tecnico.empleado.usuario.id)
            }
        }
    }

    suspend fun borrarUsuarioClienteInterno(clienteInterno: ClienteInterno)
    {
        // Primero, se borra la fila del cliente...
        getSupabaseClient().from("cliente_interno").delete{
            filter {
                eq("id_cliente_interno", clienteInterno.id)
            }
        }

        // la fila del empleado
        getSupabaseClient().from("empleado").delete{
            filter {
                eq("id_empleado", clienteInterno.empleado.id)
            }
        }

        // la fila del telefono
        getSupabaseClient().from("teléfono_empleado").delete{
            filter {
                eq("id_teléfono_empleado", clienteInterno.empleado.telefonoEmpleado.id)
            }
        }

        // la fila del usuario
        getSupabaseClient().from("usuario").delete{
            filter {
                eq("id_usuario", clienteInterno.empleado.usuario.id)
            }
        }
    }



    suspend fun buscarNombreUsuario(nombre: String, salida: (Usuario?) -> Unit)
    {
        val resultado = getSupabaseClient().from("usuario").
        select(columns = Columns.raw("nombre_usuario")){
            filter {
                eq("nombre_usuario", nombre)
            }
        }.decodeSingleOrNull<Usuario>()

        salida(resultado)

    }

    suspend fun cambiarContrasenaUsuario(usuario: Usuario, actualizacion: (Usuario?) -> Unit)
    {

        val resultados = getSupabaseClient().from("usuario").update({

            set("contraseña_usuario", usuario.password)

        }){
            select()
            filter {
                eq("nombre_usuario", usuario.nombre)
            }
        }.decodeSingleOrNull<Usuario>()

        actualizacion(resultados)

    }

    suspend fun seleccionarUsuarioById(id: Int, usuario: (Usuario) -> Unit){

        val resultado = getSupabaseClient().from("usuario").select(columnaUsuario){
            filter{
                eq("id_usuario", id)
            }
        }.decodeSingle<Usuario>()

        usuario(resultado)

    }

    suspend fun seleccionarUsuarioByNombre(nombre: String, usuario: (Usuario?) -> Unit)
    {
        val resultados = getSupabaseClient().from("usuario").select(columnaUsuario){
            filter {
                eq("nombre_usuario", nombre)
            }
        }.decodeSingleOrNull<Usuario>()

        usuario(resultados)
    }

    suspend fun deshabilitarUsuarioById(id: Int)
    {
        // Se obtiene los datos del usuario
        val usuario = getSupabaseClient().from("usuario").select(){
            filter {
                eq("id_usuario", id)
            }
        }.decodeSingle<Usuario>()

        // Se crea la consulta con sus datos hasheados
        getSupabaseClient().from("usuario").update({

            set("nombre_usuario", SHA512(SHA512(usuario.nombre)).subSequence(0, 20).toString())
            set("contraseña_usuario", SHA512(usuario.password))
            set("usuario_habilitado", false)

        }){
            filter {
                eq("id_usuario", id)
            }
        }
    }

    suspend fun comprobarUsuarioInhabilitado(usuario: Usuario): Boolean
    {
        val resultado = getSupabaseClient().from("usuario").select {
            filter {
                eq("id_usuario", usuario.id)
            }
        }.decodeSingle<Usuario>()

        return resultado.habilitado
    }

    @OptIn(SupabaseExperimental::class)
    suspend fun realTimeUsuarioInhabiitado(scope: CoroutineScope, usuario: Usuario, resultado: (Boolean) -> Unit)
    {

        val channel = getSupabaseClient().channel(channelId = "ChannelTicket")

        val channelFlow = channel.postgresChangeFlow<PostgresAction>(schema = "public"){
            table = "usuario"
        }

        channelFlow.onEach {

            // Se comprueba que el usuario no haya sido inhabilitado
            resultado(comprobarUsuarioInhabilitado(usuario))

        }.launchIn(scope)

        channel.subscribe()

    }

}