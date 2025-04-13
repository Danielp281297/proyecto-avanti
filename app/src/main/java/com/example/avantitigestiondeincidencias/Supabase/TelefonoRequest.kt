package com.example.avantitigestiondeincidencias.Supabase

import androidx.compose.foundation.layout.Column
import com.example.avantitigestiondeincidencias.AVANTI.CodigoOperadoraTelefono
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class TelefonoRequest: SupabaseClient() {

    // Metodo para obtener los codigos de las operadoras de los celulares
    suspend fun seleccionarOperadorasTelefono(): List<CodigoOperadoraTelefono>
    {

        return getSupabaseClient().from("código_operadora_teléfono").select().decodeList<CodigoOperadoraTelefono>()

    }

}