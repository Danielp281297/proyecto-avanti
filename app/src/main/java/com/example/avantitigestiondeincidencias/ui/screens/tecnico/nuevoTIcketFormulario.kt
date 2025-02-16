package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.MainActivity
import com.example.avantitigestiondeincidencias.Request.ApiServices
import com.example.avantitigestiondeincidencias.Request.OkHttpRequest
import com.example.avantitigestiondeincidencias.Request.Retrofit
import com.example.avantitigestiondeincidencias.espacioSpacer
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.screens.componentes.Spinner
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.MainScope
import okhttp3.FormBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class peticionTicket(val idUsusario: Int, val tipo: String, val prioridad: String, val descripcion: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun nuevoTicketFormulario()
{

    val context = LocalContext.current

    var idUsuario = 1
    val prioridadTicket = listOf<String>("BAJA", "MEDIA", "ALTA", "URGENTE")
    val tipoEvento = listOf("Incidencia", "Solicitud", "Mantenimiento", "Control de cambio")

    var descripcionState = remember{
        mutableStateOf("")
    }

    var prioridadState = remember {
        mutableStateOf("")
    }

    var tipoEventoState = remember{
        mutableStateOf("")
    }

    Box(modifier = Modifier.fillMaxSize().padding(0.dp).background(Color.White), contentAlignment = Alignment.Center)
    {

        Box(modifier = Modifier.background(Color.Black))
        Scaffold(
            modifier = Modifier.wrapContentHeight(),
            containerColor = Color.White,
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(Color.White),
                    title = {
                        Text("Nuevo Ticket", modifier = Modifier.fillMaxWidth().padding(0.dp), textAlign = TextAlign.Center)
                    }
                )
            }
        )
        {
            Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(25.dp))
            {

                Spacer(modifier = Modifier.padding(50.dp))

                Text("Ingrese los campos correspondientes:",
                    modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.padding(15.dp))


                Row(modifier = Modifier)
                {

                    Column(modifier = Modifier.weight(1f))
                    {
                        Text("Tipo de evento")
                        Spacer(modifier = espacioSpacer)
                        Spinner(modifier = Modifier,
                            itemList = tipoEvento,
                            onItemSelected = { option ->
                                tipoEventoState.value = option
                            })
                    }

                    Spacer(modifier = espacioSpacer)

                    Column(modifier = Modifier.weight(1f))
                    {
                        Text("Prioridad")
                        Spacer(modifier = espacioSpacer)
                        Spinner(modifier = Modifier,
                            itemList = prioridadTicket,
                            onItemSelected = { option ->
                                prioridadState.value = option
                            })
                    }

                }

                Spacer(modifier = Modifier.padding(15.dp))

                Text(text = " Descripción:")

                Spacer(modifier = espacioSpacer)

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = descripcionState.value,
                    onValueChange = { newText ->
                        // Si el texto es menor a 50 caracteres, se almacena en newText
                        if (newText.length <= 100)
                                descripcionState.value = newText
                                    },
                    label = { Text("Indique aqui", fontSize = 13.sp) },
                    placeholder = {  },
                    supportingText = {

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Spacer(modifier = Modifier.padding(0.dp))

                            Text(text = "${descripcionState.value.length}/100",
                                color = if (descripcionState.value.length < 95) Color.LightGray else Color.Red,
                                modifier = Modifier)
                        }


                    }
                )

                Spacer(modifier = Modifier.padding(15.dp))

                Button(modifier = modeloButton,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RectangleShape,
                    onClick = {


                        if(tipoEventoState.value.isBlank() || prioridadState.value.isBlank())
                        {

                            Toast.makeText(context, "Los campos no pueden quedar vacios.", Toast.LENGTH_SHORT).show()

                        }else
                        if(descripcionState.value.isBlank()) // Se comprueba que la descripcion no este vacia, ni conformada por espacios
                        {

                            Toast.makeText(context, "Descripcion invalida. Intente de nuevo.", Toast.LENGTH_SHORT).show()

                        }else
                        // Se envia la peticion
                        crearTicket(peticionTicket(idUsuario, tipoEventoState.value, prioridadState.value, descripcionState.value))

                    }
                )
                {
                    Text(text = "ABRIR TICKET", color = Color.White)
                }


            }

        }

    }
}

fun crearTicket(peticion: peticionTicket)
{

    val requestBody = FormBody.Builder()
        .add("idUsuario", peticion.idUsusario.toString()) // Aquí defines el nombre y valor
        .add("tipoEvento", peticion.tipo)
        .add("prioridad", peticion.prioridad)
        .add("descripcion", peticion.descripcion)
        .build()

    OkHttpRequest().enviarPost("http://192.168.0.104/Daniel/IncidenciasAvanti/PHP/crear_ticket.php/", requestBody)

}

@Preview(showBackground = true)
@Composable
fun nuevoTicketFormularioPreview() {
    AVANTITIGestionDeIncidenciasTheme {

        nuevoTicketFormulario()

    }
}
