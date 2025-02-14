package com.example.avantitigestiondeincidencias.Request

import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("?seleccionarTickets")
    fun getTickets(): Call<List<Ticket>>

}