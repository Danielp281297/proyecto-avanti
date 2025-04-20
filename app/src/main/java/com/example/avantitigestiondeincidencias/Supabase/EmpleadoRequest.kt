package com.example.avantitigestiondeincidencias.Supabase

import android.util.Log
import androidx.compose.foundation.layout.Column
import com.example.avantitigestiondeincidencias.AVANTI.CargoEmpleado
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.AVANTI.Departamento
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.AVANTI.GrupoAtencion
import com.example.avantitigestiondeincidencias.AVANTI.Sede
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.AVANTI.Usuario
import com.example.avantitigestiondeincidencias.ui.screens.componentes.SHA512
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.Login
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.delay

class EmpleadoRequest(): SupabaseClient() {

    val columnasTecnico = Columns.raw("""
        id_técnico,
        grupo_atención(
            id_grupo_atención,
            nombre_grupo_atención
        ),
        empleado!inner (
            id_empleado,
            cédula_empleado,
            primer_nombre_empleado,
            segundo_nombre_empleado,
            primer_apellido_empleado,
            segundo_apellido_empleado,
            correo_electrónico_empleado,
            id_usuario,
            teléfono_empleado(
                extensión_teléfono,
                código_operadora_teléfono(
                    operadora_teléfono
                )
            ),
            departamento(
                nombre_departamento,
                piso_departamento,
                sede(
                    nombre_sede
                )
            ),
            cargo_empleado(
                tipo_cargo_empleado
            ),
            usuario!inner(
                id_usuario, 
                nombre_usuario,
                id_tipo_usuario
            )
        )
            
        """.trimIndent())

    val columnasClienteInterno = Columns.raw("""
        id_cliente_interno,
        empleado!inner (
            id_empleado,
            cédula_empleado,
            primer_nombre_empleado,
            segundo_nombre_empleado,
            primer_apellido_empleado,
            segundo_apellido_empleado,
            correo_electrónico_empleado,
            teléfono_empleado(
                extensión_teléfono,
                código_operadora_teléfono(
                    operadora_teléfono
                )
            ),
            departamento(
                nombre_departamento,
                piso_departamento,
                sede(
                    nombre_sede
                )
            ),
            cargo_empleado(
                tipo_cargo_empleado
            ),
            usuario!inner(
                id_usuario, 
                nombre_usuario,
                id_tipo_usuario
            )
        )
            
        """.trimIndent())

    val columnasDepartamento = Columns.raw("""
        nombre_departamento, 
        piso_departamento,
        sede(
            nombre_sede
        )
    """.trimIndent())

    suspend fun seleccionarTecnicobyUsuarioId(idUsuario: Int): Tecnico {
        return getSupabaseClient().from("técnico").select(columns = columnasTecnico){
            filter {
                eq("empleado.id_usuario", idUsuario)
            }
        }.decodeSingle<Tecnico>()
    }

    suspend fun seleccionarClienteInternobyUsuarioId(idUsuario: Int): ClienteInterno {
        return getSupabaseClient().from("cliente_interno").select(columns = columnasClienteInterno){
            filter {
                eq("empleado.id_usuario", idUsuario)
            }
        }.decodeSingle<ClienteInterno>()
    }

    suspend fun seleccionarEmpleadoTecnicoById(id: Int): Tecnico {

        return getSupabaseClient().from("técnico").select(columns = columnasTecnico){

            filter {
                eq("id_técnico", id)
            }

        }.decodeSingle<Tecnico>()

    }

    suspend fun seleccionarEmpleadoClienteById(id: Int): ClienteInterno {

        return getSupabaseClient().from("cliente_interno").select(columns = columnasClienteInterno){

            filter {
                eq("id_cliente_interno", id)
            }

        }.decodeSingle<ClienteInterno>()

    }

    suspend fun seleccionarCargosEmpleado(): List<CargoEmpleado> {
        return getSupabaseClient().from("cargo_empleado").select().decodeList<CargoEmpleado>()
    }

    suspend fun seleccionarGrupoAtencion(): List<GrupoAtencion> {
        return getSupabaseClient().from("grupo_atención").select().decodeList<GrupoAtencion>()
    }

    suspend fun seleccionarDepartamentos(): List<Departamento> {

        return getSupabaseClient().from("departamento")
            .select(columns = columnasDepartamento)
            .decodeList<Departamento>()
    }

    suspend fun obtenerDatosUsuario(usuario: String, pass: String): Usuario? {

        // Se convierte la contrasena en un hash2 de 256 bits
        val contrasena = SHA512(pass)

        var empleado: Usuario? = getSupabaseClient().from("usuario").
            select(columns = Columns.raw("""
                    id_usuario,
                    id_tipo_usuario
            """.trimIndent())){
                filter {
                    eq("nombre_usuario", usuario)
                    eq("contraseña_usuario", contrasena)
                }

            }.decodeSingleOrNull()

        return empleado

    }

    suspend fun seleccionarClienteById(id: Int): ClienteInterno {

        return getSupabaseClient().from("cliente_interno").select(columns = columnasClienteInterno){

            filter {
                eq("id_empleado", id)
            }

        }.decodeSingle<ClienteInterno>()

    }

    suspend fun buscarCorreoElectronico(correo: String, salida: (Empleado?) -> Unit)
    {
        val resultado = getSupabaseClient().from("empleado").
        select(columns = Columns.raw("correo_electrónico_empleado")){
            filter {
                eq("correo_electrónico_empleado", correo)
            }
        }.decodeSingleOrNull<Empleado>()

        salida(resultado)

    }

    suspend fun buscarCedula(cedula: String, salida: (Empleado?) -> Unit)
    {
        val resultado = getSupabaseClient().from("empleado").
        select(Columns.raw("cédula_empleado")){
            filter {
                eq("cédula_empleado", cedula)
            }
        }.decodeSingleOrNull<Empleado>()

        salida(resultado)

    }

    suspend fun buscarTecnicoByNombreUsuario(nombre: String, tecnicos: (List<Tecnico>) -> Unit)
    {

        val resultados = getSupabaseClient().from("técnico").select(columnasTecnico){
            filter {
                like("empleado.usuario.nombre_usuario", "%${nombre}%")
            }
        }.decodeList<Tecnico>()

        tecnicos(resultados)

    }

    suspend fun buscarClienteByNombreUsuario(nombre: String, clientes: (List<ClienteInterno>) -> Unit)
    {

        val resultados = getSupabaseClient().from("cliente_interno").select(columnasClienteInterno){
            filter {
                like("empleado.usuario.nombre_usuario", "%${nombre}%")
            }
        }.decodeList<ClienteInterno>()

        clientes(resultados)

    }

}