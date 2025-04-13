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
import com.example.avantitigestiondeincidencias.AVANTI.EstadoTicket
import com.example.avantitigestiondeincidencias.AVANTI.PrioridadTicket
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.AVANTI.TipoTicket
import com.example.avantitigestiondeincidencias.MainActivity
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.espacioSpacer
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.screens.componentes.Spinner
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.withContext
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun nuevoTicketFormulario(idClienteInterno: Int, lambda: () -> Unit)
{

    val context = LocalContext.current

    val prioridadTicket = listOf<String>("BAJA", "MEDIA", "ALTA", "URGENTE")
    val tipoEvento = listOf("Incidencia", "Solicitud", "Mantenimiento", "Control de cambio")

    var descripcionState = remember{
        mutableStateOf("")
    }

    var prioridadState = remember {
        mutableStateOf(1)
    }

    var tipoTicketState = remember{
        mutableStateOf(1)
    }

    val focusRequester = remember{
        FocusRequester()
    }

    var insertarTicketState = remember{
        mutableStateOf(false)
    }

    var ticket = Ticket()

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

                Column(modifier = Modifier)
                {

                    Column(modifier = Modifier)
                    {
                        Text("Tipo de ticket")
                        Spinner(modifier = Modifier,
                            itemList = tipoEvento,
                            onItemSelected = { option ->
                                tipoTicketState.value = tipoEvento.indexOf(option) + 1
                            })
                    }

                    Spacer(modifier = espacioSpacer)

                    Column(modifier = Modifier)
                    {
                        Text("Prioridad")
                        Spinner(modifier = Modifier,
                            itemList = prioridadTicket,
                            onItemSelected = { option ->
                                prioridadState.value = prioridadTicket.indexOf(option) + 1
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

                        insertarTicketState.value = true

                    }
                )
                {
                    Text(text = "ABRIR TICKET", color = Color.White)
                }

        }

        Spacer(modifier = espacioSpacer)

    }

    // Si se oprime el boton ABRIR TICKET, se guarda la informacion de la base de datos
    if (insertarTicketState.value) {

        //Se envia la consulta en una corrutina
        LaunchedEffect(Unit)
        {

            if (validarContenidoTicket(
                    context,
                    tipoTicketState.value,
                    prioridadState.value,
                    descripcionState.value
                )
            ) {

                withContext(Dispatchers.IO) {

                    // Se envia la peticion


                    ticket = Ticket(
                        hora = LocalTime.now().toString(),
                        fecha = java.time.LocalDate.now().toString(),
                        descripcion = descripcionState.value,
                        observaciones = "Sin observaciones",
                        idTipoTicket = tipoTicketState.value,
                        idPrioridadTicket = prioridadState.value,
                        idEstadoTicket = 1, // abierto
                        idClienteInterno = idClienteInterno,
                        idTecnico = 1

                    )

                    TicketRequests().insertarTicketByClienteInternoId(ticket/*, { tickets ->

                        lambda(tickets)

                    }*/)

                    lambda()

                    insertarTicketState.value = false


                }

            }



        }
    }

    val view = LocalView.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        view.bringToFront()
    }

}

fun validarContenidoTicket(context: Context, tipoEvento: Int, prioridad: Int, descripcion: String): Boolean
{

    var bandera = false

    if((tipoEvento < 1 && tipoEvento > 4) || (prioridad < 0 && prioridad > 4))
    {

        Toast.makeText(context, "Los campos no pueden quedar vacios.", Toast.LENGTH_SHORT).show()

    }else
    if(descripcion.isBlank()) // Se comprueba que la descripcion no este vacia, ni conformada por espacios
    {

        Toast.makeText(context, "Descripcion invalida. Intente de nuevo.", Toast.LENGTH_SHORT).show()

    }
    else bandera = true

    return bandera

}

@Preview(showBackground = true)
@Composable
fun nuevoTicketFormularioPreview() {
    AVANTITIGestionDeIncidenciasTheme {

        nuevoTicketFormulario(0, {})

    }
}
