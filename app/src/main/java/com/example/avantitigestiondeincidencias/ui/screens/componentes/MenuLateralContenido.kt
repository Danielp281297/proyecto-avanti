package com.example.avantitigestiondeincidencias.ui.screens.componentes

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.modeloButton

@Composable
fun MenuLateralContenido(empleado: Empleado, perfil: () -> Unit, manualUsuarioEvento: () -> Unit, cerrarSesionEvento: () -> Unit)
{

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
                androidx.compose.material3.Icon(modifier = Modifier.size(25.dp), imageVector = Icons.Default.Person, contentDescription = "Manual de usuario")
                Text("Perfil", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 25.dp))
            }

            Row(modifier = modeloButton.clickable {
                manualUsuarioEvento()
            }, verticalAlignment = Alignment.CenterVertically)
            {
                androidx.compose.material3.Icon(modifier = Modifier.size(25.dp), painter = painterResource(R.drawable.book_open_solid), contentDescription = "Manual de usuario")
                Text("Manual de usuario", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 25.dp))
            }

        }

        Row(modifier = modeloButton.clickable {
            cerrarSesionEvento()
        }, verticalAlignment = Alignment.CenterVertically)
        {
            androidx.compose.material3.Icon(modifier = Modifier.size(25.dp), painter = painterResource(R.drawable.logout_icon), contentDescription = "Cierre de Sesión")
            Text("Cerrar Sesión", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 25.dp))

        }

    }

}
