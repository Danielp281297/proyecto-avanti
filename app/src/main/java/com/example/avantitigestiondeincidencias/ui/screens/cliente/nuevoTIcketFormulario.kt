package com.example.avantitigestiondeincidencias.ui.screens.cliente

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.espacioSpacer
import com.example.avantitigestiondeincidencias.ui.screens.componentes.BotonPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.OutlinedTextFieldPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.Spinner
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import com.example.avantitigestiondeincidencias.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun nuevoTicketFormulario(
    idClienteInterno: Int,
    containerColor: Color = Color.Transparent,
    lambda: () -> Unit)
{

    val context = LocalContext.current


    val tipoEvento = listOf("Incidencia", "Solicitud", "Mantenimiento", "Control de cambio")

    var descripcionState = remember{
        mutableStateOf("")
    }



    var tipoTicketState = remember{
        mutableStateOf(1)
    }

    var insertarTicketState = remember{
        mutableStateOf(false)
    }

    var ticket = Ticket()

    Box(modifier = Modifier.wrapContentHeight().padding(0.dp).background(containerColor), contentAlignment = Alignment.Center)
    {
            Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(15.dp))
            {

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
                {
                    Icon(
                        painter = painterResource(R.drawable.nuevo_ticket_icon),
                        contentDescription = "",
                        modifier = Modifier.size(50.dp)
                    )
                }

                Text(text = "Nuevo Ticket", modifier = Modifier.padding(0.dp, 10.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp)

                HorizontalDivider()

                Spacer(modifier = espacioSpacer)
                Text("Ingrese los campos correspondientes:",
                    modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

                Spacer(modifier = espacioSpacer)

                Column(modifier = Modifier)
                {

                    Column(modifier = Modifier)
                    {
                        Text("Tipo de ticket")
                        Spinner(modifier = Modifier.fillMaxWidth(),
                            itemList = tipoEvento,
                            posicionInicial = 0,
                            onItemSelected = { option ->
                                tipoTicketState.value = tipoEvento.indexOf(option) + 1
                            })
                    }

                    Spacer(modifier = espacioSpacer)

                }

                Spacer(modifier = espacioSpacer)
                Text(text = " Descripción:")
                OutlinedTextFieldPersonalizado(
                    modifier = Modifier.fillMaxWidth(),
                    value = descripcionState.value,
                    onValueChange = {newText ->
                        // Si el texto es menor a 50 caracteres, se almacena en newText
                        if (newText.length <= 100)
                            descripcionState.value = newText
                    },
                    label = { Text("Indique aquí", fontSize = 13.sp) },
                    supportingText = true,
                    imeActionNext = false,
                    maximoCaracteres = 100,
                )

                Spacer(modifier = espacioSpacer)
                BotonPersonalizado(onClick = {
                    insertarTicketState.value = true
                })
                {
                    Text(text = "ABRIR TICKET", color = Color.White, fontFamily = montserratFamily)
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
                    descripcionState.value
                )
            ) {

                withContext(Dispatchers.IO) {

                    // Se envia la peticion


                    ticket = Ticket(
                        hora = LocalTime.now().toString(),
                        fecha = LocalDate.now().toString(),
                        descripcion = descripcionState.value,
                        observaciones = "Sin observaciones",
                        idPrioridadTicket = 1,
                        idTipoTicket = tipoTicketState.value,
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

}

fun validarContenidoTicket(context: Context, tipoEvento: Int, descripcion: String): Boolean
{

    var bandera = false

    if(tipoEvento < 1 && tipoEvento > 4)
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

        nuevoTicketFormulario(0){}

    }
}
