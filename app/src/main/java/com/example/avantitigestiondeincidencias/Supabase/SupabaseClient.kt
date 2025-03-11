package com.example.avantitigestiondeincidencias.Supabase

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime


abstract class SupabaseClient {


    private val supabaseClient = createSupabaseClient(
            supabaseUrl = "https://xlmisuuihmqqqtjxairh.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InhsbWlzdXVpaG1xcXF0anhhaXJoIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDA4NzA0NDIsImV4cCI6MjA1NjQ0NjQ0Mn0.5cL_VZXH-dL1_4FD8aLslwdyRLgG7TVEB0gSvC6JhRc"
        ) {
            install(Postgrest)
            install(Realtime)
            //install other modules
        }

    open fun getSupabaseClient(): SupabaseClient {

        return this.supabaseClient

    }

}