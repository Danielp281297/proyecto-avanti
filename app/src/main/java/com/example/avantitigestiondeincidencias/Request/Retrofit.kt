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
                               functionLambda: (List<Ticket>?) -> Unit)
        {

            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            GlobalScope.launch {

                    val service = retrofit.create(ApiServices::class.java).getTickets()

                    service.enqueue(object : Callback<List<Ticket>> {
                        override fun onResponse(
                            p0: Call<List<Ticket>?>,
                            response: Response<List<Ticket>?>
                        ) {
                            if (response.isSuccessful) {

                                val apiResponse = response.body()
                                functionLambda(apiResponse)
                                //Log.d("ApiService: ", apiResponse.toString())
                            }
                        }

                        override fun onFailure(
                            p0: Call<List<Ticket>?>,
                            p1: Throwable
                        ) {
                            Log.e("Error: ", p1.message.toString())
                        }

                    })

            }

        }

    }

}