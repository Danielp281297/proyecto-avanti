package com.example.avantitigestiondeincidencias.Supabase

import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class EmpleadoRequest(): SupabaseClient() {

    val columnasTecnico = Columns.raw("""
        id_técnico,
        grupo_atención(
            id_grupo_atención,
            nombre_grupo_atención
        ),
        empleado(
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
            )
        )
            
        """.trimIndent())

    suspend fun buscarEmpleadoById(id: Int): Empleado {

        return getSupabaseClient().from("empleado").select(columns = columnasTecnico).decodeSingle<Empleado>()

    }

}