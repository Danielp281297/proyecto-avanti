package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.Supabase.EmpleadoRequest
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import io.ktor.http.ContentType.Application.Json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import org.json.JSONObject


@OptIn(ExperimentalSerializationApi::class)
@Composable
fun PantallaPruebas(navController: NavController)
{


    var tecnico = remember {
        mutableStateOf<Tecnico>(Tecnico())
    }


    var clienteInterno = remember {
        mutableStateOf(ClienteInterno())
    }

    var administrador = remember {
        mutableStateOf(Tecnico())
    }

    var jsonState = remember {
        mutableStateOf("")
    }


    var empleado = remember {
        mutableStateOf<Empleado>(Empleado())
    }

    var pantallaCargaState = remember {
        mutableStateOf(false)
    }


    LaunchedEffect(Unit)
    {
        withContext(Dispatchers.IO)
        {
            tecnico.value = EmpleadoRequest().seleccionarEmpleadoTecnicoById(2)
            clienteInterno.value = EmpleadoRequest().seleccionarEmpleadoClienteById(3)
            administrador.value = EmpleadoRequest().seleccionarEmpleadoTecnicoById(1)
        }
    }


    Box(modifier = Modifier.fillMaxSize())
    {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        )
        {


                Button(onClick = {
                    empleado.value.id = 1
                    jsonState.value = Json { ignoreUnknownKeys = true }.encodeToString(administrador.value)
                    //Log.d("ADMINISTRADOR", json.value)
                    navController.navigate("principalAdministrador" + "/${jsonState.value}")
                }) {

                    Text(text = "ADMINISTRADOR")

                }

                Button(onClick = {
                    empleado.value.id = 3
                    jsonState.value = Json { ignoreUnknownKeys = true }.encodeToString(clienteInterno.value)
                    navController.navigate("principalCliente" + "/${jsonState.value}")
                }) {

                    Text(text = "CLIENTE (3)")

                }

                Button(onClick = {
                    //empleado.value.id = 8
                    jsonState.value = Json { ignoreUnknownKeys = true }.encodeToString(tecnico.value)
                    navController.navigate("principalTécnico" + "/${jsonState.value}")

                }) {

                    Text(text = "TECNICO (2)")

                }


            }

        }


}


@Preview(showBackground = true)
@Composable
fun PantallaPruebasPreview() {

    val navController = rememberNavController()
    AVANTITIGestionDeIncidenciasTheme {

        PantallaPruebas(navController)

    }
}
