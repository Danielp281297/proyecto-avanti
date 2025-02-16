package com.example.avantitigestiondeincidencias.Request

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class Retrofit {

    companion object
    {

        @OptIn(DelicateCoroutinesApi::class)
        fun seleccionarTickets(url: String,
                               functionLambda: (retrofit: Retrofit?) -> Unit)
        {

            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            GlobalScope.launch {

                    functionLambda(retrofit)

            }

        }

        @OptIn(DelicateCoroutinesApi::class)
        fun crearTicket(url: String,
                               functionLambda: (retrofit: Retrofit?) -> Unit)
        {

            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            GlobalScope.launch {

                functionLambda(retrofit)

            }

        }

    }

}