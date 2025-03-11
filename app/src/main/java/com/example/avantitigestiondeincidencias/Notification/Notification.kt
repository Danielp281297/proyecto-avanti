package com.example.avantitigestiondeincidencias.Notification

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.avantitigestiondeincidencias.Notification.Notification.Companion.CHANNEL_ID
import com.example.avantitigestiondeincidencias.R

class Notification() {

    companion object
    {

        val CHANNEL_ID = "myChannel"

    }


    fun mostrarNotificacion(context: Context, titulo: String, contenido: String) {


        val builder = NotificationCompat.Builder(context, CHANNEL_ID).also {

            it.setSmallIcon(R.drawable.logo) // HAY QUE CAMBIAR ESTE ICONO
            it.setContentTitle(titulo)
            it.setContentText(contenido)
            it.priority = NotificationManager.IMPORTANCE_HIGH
        }


            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

            }
            NotificationManagerCompat.from(context).notify(1, builder.build())

            //Se crea el canal
        createChannel(context)


    }



    @SuppressLint("ObsoleteSdkInt")
    private fun createChannel(context: Context)
    {

        if (Build.VERSION.SDK_INT >= VERSION_CODES.O)
        {
            val notificationManager = getSystemService<NotificationManager>(context, NotificationManager::class.java)

            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "MyChannel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Descripcion"
            }

            //notificationManager.createNotificationChannel(notificationChannel)
            notificationManager!!.createNotificationChannel(notificationChannel)

        }
        else Toast.makeText(context, "No se creo el canal", Toast.LENGTH_SHORT).show()

    }

}