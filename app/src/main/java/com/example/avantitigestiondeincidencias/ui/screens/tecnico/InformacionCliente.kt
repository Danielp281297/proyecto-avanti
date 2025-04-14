package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.modeloButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformacionClienteInterno(navController: NavController, clienteInterno: ClienteInterno)
{

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    Color.White
                ),
                title = {
                    Text("Usuario", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            )
        },
        //Color de fondo
        containerColor = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919),
    )
    {

        Column(modifier = Modifier.padding(15.dp).fillMaxSize().verticalScroll(scrollState, true))
        {
            EncabezadoInformacionUsuario(com.example.avantitigestiondeincidencias.R.drawable.user_icon, "Cliente Interno")

            Text(text = "CLIENTE INTERNO: ", fontSize = fuenteLetraTicketDesplegado)
            Text(
                text = "${clienteInterno.id}",
                fontWeight = FontWeight.Bold,
                fontSize = fuenteLetraTicketDesplegado
            )
            Spacer(modifier = Modifier.padding(5.dp))

            informacionPersonalEmpleado(clienteInterno.empleado)

            //Spacer(modifier = Modifier.padding(5.dp))

            InformacionLaboralEmpleado(clienteInterno.empleado)

            Spacer(modifier = Modifier.padding(10.dp))
            //Boton para cambiar los datos del usuario
            Button(modifier = modeloButton,

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                shape = RectangleShape,
                onClick = {
                }
            )
            {
                Text(text = "MODIFICAR USUARIO", color = Color.White)
            }
            Spacer(modifier = Modifier.padding(15.dp))
            //Boton para borra el usuario de la base de datos
            Button(modifier = modeloButton,

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                shape = RectangleShape,
                onClick = {
                }
            )
            {
                Text(text = "BORRAR USUARIO", color = Color.White)
            }

            Spacer(modifier = Modifier.padding(50.dp))
        }
    }
}