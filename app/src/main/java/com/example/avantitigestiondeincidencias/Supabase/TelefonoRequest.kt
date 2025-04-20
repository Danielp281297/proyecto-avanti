package com.example.avantitigestiondeincidencias.Supabase

import androidx.compose.foundation.layout.Column
import com.example.avantitigestiondeincidencias.AVANTI.CodigoOperadoraTelefono
import com.example.avantitigestiondeincidencias.AVANTI.TelefonoEmpleado
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class TelefonoRequest: SupabaseClient() {

    private val columnaTelefono = Columns.raw("""
        id_código_operadora_teléfono,
        extensión_teléfono
    """.trimIndent())
    // Metodo para obtener los codigos de las operadoras de los celulares
    suspend fun seleccionarOperadorasTelefono(): List<CodigoOperadoraTelefono>
    {

        return getSupabaseClient().from("código_operadora_teléfono").select().decodeList<CodigoOperadoraTelefono>()

    }

    suspend fun buscarTelefonoEmpleado(telefonoEmpleado: TelefonoEmpleado, resultado: (TelefonoEmpleado?) -> Unit)
    {

        val resultado = getSupabaseClient().from("teléfono_empleado").select(columnaTelefono){
            filter{
                eq("id_código_operadora_teléfono", telefonoEmpleado.idCodigoExtensionTelefono)
                eq("extensión_teléfono", telefonoEmpleado.extension)
            }
        }.decodeSingleOrNull<TelefonoEmpleado>()

        resultado(resultado)
    }

}