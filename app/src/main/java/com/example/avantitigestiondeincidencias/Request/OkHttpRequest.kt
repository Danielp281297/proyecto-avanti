package com.example.avantitigestiondeincidencias.Request

import android.util.Log
import android.widget.Toast
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import kotlin.jvm.Throws


class OkHttpRequest() {

    val client = OkHttpClient()

    val sendRequest = object: Callback {

        //Metodo que ejecuta las sentencia al momento de  que hallan fallos en la
        // conexion o en el Thread
        override fun onFailure(call: Call, e: IOException) {
            Log.d("PRUEBA ERROR", " ")
            Log.e("Error: ", e.message!!)
        }

        //Se crea un metodo que ejecuta la peticion de forma asincrona
        @Throws(IOException::class)
        override fun onResponse(call: Call, response: Response) {
            Log.d("PRUEBA", " ")
            
            try {

                // Si el objeto response se ha cargado, se guarda el contenido en el Thread
                if (response.isSuccessful)
                {
                    //Se envia el contenido del response a al MainActivity, por medio
                    // de la interfaz de Prueba
                    var resultado = response.body?.string()
                        // Como el resultado json llegua en formato d arreglo, este llegua con los corchetes incluidos
                        // Por ende, se quitan estos, para poder crear el objeto json sin inconvenientes
                        //.replaceFirst("[", "", false).replaceAfterLast("}", "")

                    Log.d("PRUEBA", resultado.toString())

                }

            }catch (e: IOException)
            {


            }
        }

    }

    fun userPostRequest(url: String, json:String) {

        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = RequestBody.create(mediaType, json)
        val request = Request.Builder().
                                post(body).
                                url(url).
                                build()

        // Se envia la peticion por medio del Callback de okHttp3
        client.newCall(request = request).enqueue(sendRequest)

    }

    fun peticionGET(url: String)
    {

        var peticion = Request.Builder().url(url).build()

        // Se envia la peticion por medio del Callback de okHttp3
        client.newCall(request = peticion).enqueue(sendRequest)

    }

}