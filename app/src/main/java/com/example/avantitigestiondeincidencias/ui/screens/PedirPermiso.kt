package com.example.avantitigestiondeincidencias.ui.screens

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.content.ContextCompat
import com.example.avantitigestiondeincidencias.Notification.Notification
import com.example.avantitigestiondeincidencias.ui.screens.componentes.AlertDialogPersonalizado
import kotlin.system.exitProcess

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PedirPermiso(
    context: Context,
    permiso: String) {

    var mostrarAlertDialog = remember {mutableStateOf(false)}

    //UNA SOLA NOTIFICACION
    val grantedNotification = ContextCompat.checkSelfPermission(context, permiso)

    // Se comprueban que los permisos hayan sido aceptados
    if( grantedNotification == PackageManager.PERMISSION_GRANTED)
    {
        Log.d("PEDIR PERMISOS","Se concedió el permiso")
    }
    // Si no es asi, se piden los permisos
    else
    {

        // Camera permission state
        val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()){ granted ->

            if (granted)
            {
                Log.d("PEDIR PERMISOS","Se concedió el permiso")
                Notification().createChannel(context)
            }
            // Si los permisos son rechazados, se cierra la aplicacion, y cada vez que se abra, se pediran los permisos
            else
            {
                Log.d("PEDIR PERMISOS", "No se concedió el permiso")
                mostrarAlertDialog.value = true
            }

        }

        // Se ejecuta la aplicacion
        LaunchedEffect(Unit) {
            permissionLauncher.launch(permiso)
        }
    }

    if (mostrarAlertDialog.value)
    {

        AlertDialogPersonalizado(
            titulo = "AVISO",
            contenido = "No se concedió el permiso necesario. Se cierra la aplicación.\n" +
                    "\n" +
                    "Por favor, conceda el permiso de notificación.",
            onDismissRequest = { mostrarAlertDialog.value },
            aceptarAccion = {

                mostrarAlertDialog.value = false
                exitProcess(0)


            },
            cancelarAccion = {}
        )


    }

}