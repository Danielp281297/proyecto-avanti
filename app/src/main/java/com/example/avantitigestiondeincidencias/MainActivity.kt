package com.example.avantitigestiondeincidencias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.InicioCliente
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.nuevoTicketFormulario
import com.example.avantitigestiondeincidencias.ui.screens.tecnico.pantallaPrueba
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme

val paddingPantallas = Modifier.fillMaxSize().padding(15.dp)
val espacioSpacer = Modifier.padding(7.5.dp)
val modeloButton = Modifier.fillMaxWidth().height(50.dp)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AVANTITIGestionDeIncidenciasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //InicioCliente()
                    nuevoTicketFormulario()
                }
            }
        }
    }
}



@Composable
fun screen()
{
    Box(modifier = Modifier.fillMaxSize())
    {
        Box(modifier = Modifier.size(250.dp).background(Color.Red).align(Alignment.Center))
        {
            
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AVANTITIGestionDeIncidenciasTheme {
        pantallaPrueba()
    }
}