package com.example.avantitigestiondeincidencias.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme

@Composable
fun PantallaCarga(
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919)
)
{

    Column(modifier = Modifier.fillMaxSize().background(containerColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {

        Icon(imageVector = ImageVector.Companion.vectorResource(R.drawable.logo),
            contentDescription = "Logo de Avanti",
            modifier = Modifier.size(90.dp))

        CircularProgressIndicator(modifier = Modifier.size(35.dp), color = if(!isSystemInDarkTheme()) Color.Black else Color.LightGray)

        Text("CARGANDO", fontWeight = FontWeight.Bold)
    }

}

@Preview(showBackground = true)
@Composable
fun CrearUsuarioPreview() {

    AVANTITIGestionDeIncidenciasTheme {
        PantallaCarga()
    }
}
