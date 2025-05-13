package com.example.avantitigestiondeincidencias.Network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.example.avantitigestiondeincidencias.DataStore.DataStore
import com.example.avantitigestiondeincidencias.ui.screens.componentes.AlertDialogPersonalizado
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Network {

    companion object{

        fun isConnect(context: Context): Boolean
        {

            //Se crea el objeto que permitira comprobar la conexion
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            var network = connectivityManager.activeNetworkInfo

            //Se retorna el estado de la conexion
            return network != null && network.isConnected

        }

        @Composable
        fun AvisoSinConexion(navController: NavController, context: Context)
        {

            val mostrarAlertDialog = remember{
                mutableStateOf(false)
            }

            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {

                    do {
                        delay(500)
                        if (!isConnect(context)) {

                            DataStore(context).setData(
                                tipo = -1,
                                json = " ",
                                sesionAbierta = false
                            )

                            mostrarAlertDialog.value = true

                        }
                    }while (true)

                }
            }

            if(mostrarAlertDialog.value)
            {

                AlertDialogPersonalizado(
                    titulo = "AVISO",
                    contenido = " Sin conexion a internet. Se cierra la sesion.",
                    onDismissRequest = {  },
                    aceptarAccion = {

                        navController.navigate("Login"){ popUpTo(navController.graph.id) }
                    },
                    cancelarAccion = {

                    }
                )

                BackHandler(enabled = false) {}

            }

        }

        @Composable
        fun networkCallback(navController: NavController, context: Context)
        {

            //Se crea el objeto que permitira comprobar la conexion
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val scope = rememberCoroutineScope()

            var sinConexion = remember { mutableStateOf(false) }

            val networkCallback = object : ConnectivityManager.NetworkCallback() {

                override fun onLost(network: Network) {
                    super.onLost(network)

                    sinConexion.value = true

                }

            }

            DisposableEffect(Unit) {

                connectivityManager.registerDefaultNetworkCallback(networkCallback)
                onDispose {
                    connectivityManager.unregisterNetworkCallback(networkCallback)
                }
            }

            if (sinConexion.value)
            {

                AlertDialogPersonalizado(
                    titulo = "AVISO",
                    contenido = " Sin conexion a internet. Se cierra la sesion.",
                    onDismissRequest = {  },
                    aceptarAccion = {
                        scope.launch {
                            DataStore(context).setData(
                                tipo = -1,
                                json = " ",
                                sesionAbierta = false
                            )

                            navController.navigate("Login"){ popUpTo(navController.graph.id) }
                        }

                    },
                    cancelarAccion = {

                    }
                )

                BackHandler(enabled = false) {}
            }

        }



    }

}