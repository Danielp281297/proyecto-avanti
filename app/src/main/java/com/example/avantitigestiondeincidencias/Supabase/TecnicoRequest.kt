package com.example.avantitigestiondeincidencias.Supabase

import com.example.avantitigestiondeincidencias.AVANTI.GrupoAtencion
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns


class TecnicoRequest: SupabaseClient() {

    val columnasSpinnerTecnicos = Columns.raw("""
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
            segundo_apellido_empleado
        )
    """.trimIndent())

    suspend fun seleccionarTecnicos(lambda: (tecnicos: List<Tecnico>) -> Unit)
    {

        val resultados = getSupabaseClient().from("técnico").select(columns = columnasSpinnerTecnicos){
            filter {
                gt(column = "id_técnico", 1)
            }
        }.decodeList<Tecnico>()

        lambda(resultados)

    }

    suspend fun seleccionarTecnicoById(id: Int): Tecnico {
        return getSupabaseClient().from("técnico").select(columnasSpinnerTecnicos){
            filter {
                eq(column = "id_empleado", id)
            }
        }.decodeSingle<Tecnico>()

    }

    suspend fun seleccionarGrupoAtencion(): List<GrupoAtencion> {

        return getSupabaseClient().from("grupo_atención").select().decodeList<GrupoAtencion>()

    }

    suspend fun seleccionarTecnicoByGrupoEmpleado(idGrupoAtencion: Int, resultado: (List<Tecnico>) -> Unit)
    {
        val resultado = getSupabaseClient().from("técnico").select(columnasSpinnerTecnicos){
            filter {
                eq(column = "id_grupo_atención", idGrupoAtencion)
            }
        }.decodeList<Tecnico>()

        resultado(resultado)

    }

}