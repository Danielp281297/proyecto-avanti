package com.example.avantitigestiondeincidencias.Request

import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServices {

    @GET("?seleccionarTickets")
    fun getTickets(): Call<List<Ticket>>

    @GET("?")
    fun getTicketsById(@Query("selecionarTicketId", encoded = true) idUsuario: Int): Call<List<Ticket>>

}