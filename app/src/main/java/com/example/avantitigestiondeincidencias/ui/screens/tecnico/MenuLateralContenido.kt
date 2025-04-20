package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.modeloButton

@Composable
fun MenuLateralContenido(navController: NavController, empleado: Empleado, perfil: () -> Unit, manualUsuarioEvento: () -> Unit)
{

    var salirUsuarioState = remember{
        mutableStateOf(false)
    }

    if (salirUsuarioState.value)
    {

        AlertDialog(
            shape = RectangleShape,
            containerColor = Color.White,
            onDismissRequest = {
                salirUsuarioState.value = false
            },
            confirmButton = {

                Text("ACEPTAR", color = Color.Black, modifier = Modifier.clickable {

                    // Se borra las pantalla de la pila y se dirige al login
                    navController.navigate("Login"){ popUpTo(navController.graph.id) }

                })

            },
            dismissButton = {

                Text("CANCELAR", color = Color.Black, modifier = Modifier.clickable {

                    salirUsuarioState.value = false

                })

            },
            title = {
                Text("Cerrar Sesión")
            },
            text = {
                Text("¿Deseas salir de la Sesión?")
            }
        )
    }

    Column(modifier = Modifier.fillMaxHeight().width(240.dp).padding(15.dp), verticalArrangement = Arrangement.SpaceBetween)
    {

        Column(modifier = Modifier.fillMaxWidth()) {

            Spacer(modifier = Modifier.padding(25.dp))
            Text(empleado.usuario.nombre)
            Text("${empleado.primerNombre} ${empleado.segundoNombre} ${empleado.primerApellido} ${empleado.segundoApellido}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp)
            HorizontalDivider()

            Spacer(modifier = Modifier.padding(10.dp))

            Row(modifier = modeloButton.clickable {
                perfil()
            }, verticalAlignment = Alignment.CenterVertically)
            {
                Icon(modifier = Modifier.size(25.dp), imageVector = Icons.Default.Person, contentDescription = "Perfil")
                Text("Perfil", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 25.dp))
            }

            Row(modifier = modeloButton.clickable {
                manualUsuarioEvento()
            }, verticalAlignment = Alignment.CenterVertically)
            {
                Icon(modifier = Modifier.size(25.dp), painter = painterResource(R.drawable.book_open_solid), contentDescription = "Manual de usuario")
                Text("Manual de usuario", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 25.dp))
            }

        }

        Row(modifier = modeloButton.clickable {
            salirUsuarioState.value = true

        }, verticalAlignment = Alignment.CenterVertically)
        {
            Icon(modifier = Modifier.size(25.dp), painter = painterResource(R.drawable.logout_icon), contentDescription = "Cierre de Sesión")
            Text("Cerrar Sesión", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 25.dp))

        }

    }

}
