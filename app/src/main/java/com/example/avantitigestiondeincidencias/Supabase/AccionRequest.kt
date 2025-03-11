package com.example.avantitigestiondeincidencias.Supabase

import android.util.Log
import com.example.avantitigestiondeincidencias.AVANTI.Accion
import com.example.avantitigestiondeincidencias.AVANTI.DescripcionAccion
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.delay
import kotlinx.coroutines.selects.select
import java.time.LocalTime

class AccionRequest: SupabaseClient() {

    val columnasAccion = Columns.raw("""
        id_acción,
        descripción_acción(
            id_descripción_acción,
            descripción_acción_ejecutada
        )   
        """.trimIndent())

    suspend fun buscarDescripcionAccion(): List<DescripcionAccion> {

        return getSupabaseClient().from("descripción_acción").select().decodeList<DescripcionAccion>()

    }

    suspend fun cerrarTicket(ticket: Ticket, accion: String)
    {

        val accion = Accion(
            hora = LocalTime.now().toString(),
            fecha = java.time.LocalDate.now().toString(),
            idDescripcionAccion = seleccionarAccion(accion),
            idTicket = ticket.id
        )

        // Se crea la accion
        getSupabaseClient().from("acción").insert(accion)


    }

    suspend fun seleccionarAccion(accionEjecutada: String): Int
    {

        var indice: Int = 0

        // Se obtienen los datos de la tabla descripcion_accion
        val descripciones = buscarDescripcionAccion()

        descripciones.forEach { item ->
            Log.d("DESCRIPCION", item.toString())
        }

        // Se comparan las cadenas de caracteres
        for (item in descripciones)
        {
            // Si la accionEjecutada es igual a alguna descripcion en la fila
            // Se devuelve el id del mismo
            if (accionEjecutada == item.descripcion) {
                indice = item.id
                break
            }
        }

        // En caso contrario, se crea la fila, y se devuelve el id
        if (indice == 0) {
            val nuevaDescripcion =
                getSupabaseClient().from("descripción_acción")
                    .insert(DescripcionAccion(descripcion = accionEjecutada)){ select() }
                    .decodeSingle<DescripcionAccion>()

            indice = nuevaDescripcion.id
        }

        return indice

    }

}