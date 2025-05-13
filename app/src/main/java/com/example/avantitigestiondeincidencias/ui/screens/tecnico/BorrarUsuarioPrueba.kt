package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.Supabase.EmpleadoRequest
import com.example.avantitigestiondeincidencias.Supabase.UsuarioRequest
import com.example.avantitigestiondeincidencias.ui.screens.componentes.BotonPersonalizado
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun BorrarUsuarioPrueba()
{

    var borrarUsuarioState = remember {
        mutableStateOf(false)
    }
/*
    var clienteInterno = remember {
        mutableStateOf<ClienteInterno>(ClienteInterno())
    }
    */

    var tecnico = remember {
        mutableStateOf<Tecnico>(Tecnico())
    }

    //Se obtienen los datos del usuario
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            tecnico.value = EmpleadoRequest().seleccionarTecnicoById(6)
        }
    }



    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly)
    {
        BotonPersonalizado(
            onClick = {
                borrarUsuarioState.value = true
            }
        ) {
            Text("BORRAR USUARIO", fontFamily = montserratFamily)
        }

    }

    if (borrarUsuarioState.value)
    {
        Log.d("USUARIO", tecnico.value.toString())

        LaunchedEffect(Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                //UsuarioRequest().borrarUsuarioClienteInterno(clienteInterno.value)
                UsuarioRequest().borrarUsuarioTecnico(tecnico.value)
            }
        }

        borrarUsuarioState.value = false
    }

}