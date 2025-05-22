package com.example.avantitigestiondeincidencias.ui.screens.usuario

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.avantitigestiondeincidencias.AVANTI.Usuario
import com.example.avantitigestiondeincidencias.Supabase.UsuarioRequest
import com.example.avantitigestiondeincidencias.ui.screens.componentes.AlertDialogPersonalizado
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun usuarioDeshabilitado(navController: NavController, usuario: Usuario)
{

    var usuarioInhabilitado = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            if(!UsuarioRequest().comprobarUsuarioInhabilitado(usuario))
                usuarioInhabilitado.value = true

        }
    }

    if (usuarioInhabilitado.value)
    {
        mensajeDeshabilitacion(navController)
    }

}

@Composable
fun validarUsuarioInhabilitado(navController: NavController, usuario: Usuario)
{

    var usuarioInhabilitado = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            UsuarioRequest().realTimeUsuarioInhabiitado(this, usuario){

                usuarioInhabilitado.value = it

            }

        }
    }

    if (usuarioInhabilitado.value)
    {
        mensajeDeshabilitacion(navController)

        BackHandler(enabled = false) {}
    }

}

@Composable
fun mensajeDeshabilitacion(navController: NavController)
{

    AlertDialogPersonalizado(
        titulo = "ATENCIÓN",
        contenido = "Se deshabilitó el usuario. Se cierra la sesión. \n Para más información, comuníquese con el administrador.",
        onDismissRequest = {  },
        aceptarAccion = {

            navController.navigate("Login"){ popUpTo(navController.graph.id) }
        },
        cancelarAccion = {

        }
    )

}
