package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.content.Context
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
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
fun nuevoTicketFormulario(function: () -> Unit)
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

    val focusRequester = remember{
        FocusRequester()
    }

    Box(modifier = Modifier.wrapContentHeight().padding(0.dp).background(Color.White), contentAlignment = Alignment.Center)
    {
            Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(15.dp))
            {

                Text(text = "Nuevo Ticket", modifier = Modifier.padding(0.dp, 10.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp)

                Text("Ingrese los campos correspondientes:",
                    modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

                Spacer(modifier = espacioSpacer)

                Row(modifier = Modifier)
                {

                    Column(modifier = Modifier.weight(1f))
                    {
                        Text("Tipo de evento")
                        Spinner(modifier = Modifier,
                            itemList = tipoEvento,
                            onItemSelected = { option ->
                                tipoEventoState.value = option
                            })
                    }

                    Spacer(modifier = espacioSpacer)

                    Column(modifier = Modifier.weight(0.9f))
                    {
                        Text("Prioridad")
                        Spinner(modifier = Modifier,
                            itemList = prioridadTicket,
                            onItemSelected = { option ->
                                prioridadState.value = option
                            })
                    }

                }

                Spacer(modifier = espacioSpacer)
                Text(text = " Descripción:")

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                    value = descripcionState.value,
                    onValueChange = { newText ->
                        // Si el texto es menor a 50 caracteres, se almacena en newText
                        if (newText.length <= 100)
                                descripcionState.value = newText
                                    },
                    label = { Text("Indique aqui", fontSize = 13.sp) },
                    placeholder = {  },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedLabelColor = Color.Black,
                        focusedBorderColor = Color.Black),
                    supportingText = {

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Spacer(modifier = Modifier.padding(0.dp))

                            Text(text = "${descripcionState.value.length}/100",
                                color = if (descripcionState.value.length < 95) Color.LightGray else Color.Red,
                                modifier = Modifier)
                        }


                    }
                )

                Spacer(modifier = espacioSpacer)

                Button(modifier = modeloButton,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RectangleShape,
                    onClick = {

                        // Se envia la peticion
                        if(validarPeticionTicket(context, tipoEventoState.value, prioridadState.value, descripcionState.value)) {
                            crearTicket(
                                peticionTicket(
                                    idUsuario,
                                    tipoEventoState.value,
                                    prioridadState.value,
                                    descripcionState.value
                                )
                            )
                            function()
                        }
                    }
                )
                {
                    Text(text = "ABRIR TICKET", color = Color.White)
                }

        }

        Spacer(modifier = espacioSpacer)

    }

    val view = LocalView.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        view.bringToFront()
    }

}

fun validarPeticionTicket(context: Context, tipoEventoState: String, prioridadState: String, descripcionState: String): Boolean
{

    var bandera = false

    if(tipoEventoState.isBlank() || prioridadState.isBlank())
    {

        Toast.makeText(context, "Los campos no pueden quedar vacios.", Toast.LENGTH_SHORT).show()

    }else
    if(descripcionState.isBlank()) // Se comprueba que la descripcion no este vacia, ni conformada por espacios
    {

        Toast.makeText(context, "Descripcion invalida. Intente de nuevo.", Toast.LENGTH_SHORT).show()

    }
    else bandera = true

    return bandera

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

        nuevoTicketFormulario({})

    }
}
