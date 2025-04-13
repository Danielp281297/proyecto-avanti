package com.example.avantitigestiondeincidencias.Supabase

import android.util.Log
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
import kotlinx.coroutines.delay

class EmpleadoRequest(): SupabaseClient() {

    val columnasTecnico = Columns.raw("""
        id_técnico,
        grupo_atención(
            id_grupo_atención,
            nombre_grupo_atención
        ),
        empleado (
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
            usuario(
                id_usuario, 
                nombre_usuario
            )
        )
            
        """.trimIndent())

    val columnasClienteInterno = Columns.raw("""
        id_cliente_interno,
        empleado (
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
            usuario(
                id_usuario, 
                nombre_usuario
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

    suspend fun obtenerDatosUsuario(usuario: String, pass: String): Empleado? {

        // Se convierte la contrasena en un hash2 de 256 bits
        val contrasena = SHA512(pass)

        Log.d("Usuario", usuario)
        Log.d("Contrasena", contrasena)

        var empleado: Empleado? = getSupabaseClient().from("empleado").
            select(columns = Columns.raw("""
                id_empleado,
                primer_nombre_empleado,
                primer_nombre_empleado,
                usuario!inner (
                    id_usuario,
                    id_tipo_usuario
                )
            """.trimIndent())){

                filter {
                    eq("usuario.nombre_usuario", usuario)
                    eq("usuario.contraseña_usuario", contrasena)
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

}