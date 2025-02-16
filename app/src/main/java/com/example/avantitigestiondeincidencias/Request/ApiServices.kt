package com.example.avantitigestiondeincidencias.Request

import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {

    @GET("?seleccionarTickets")
    fun getTickets(): Call<List<Ticket>>

    //@Headers("Content-Type:application/json; charset: UTF-8")
    @POST("crearTicket")
    fun crearTicketUserID(@Body json: JSONObject): Call<Ticket>

}