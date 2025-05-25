package com.example.avantitigestiondeincidencias.ui.screens.ticket

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avantitigestiondeincidencias.AVANTI.Accion
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Network.Network
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.Supabase.AccionRequest
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.ui.screens.componentes.AlertDialogPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.PantallaCarga
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val fuenteLetraTicketDesplegado = 15.sp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContenidoTicketDesplegado(
    navController: NavController,
    context: Context,
    ticket: Ticket,
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919),
    function: @Composable () -> Unit)
{
    
    val iconos = listOf(R.drawable.incidencia_icon, R.drawable.clipboard_question_solid, R.drawable.screwdriver_wrench_solid, R.drawable.gears_solid)
    val scrollState = rememberScrollState()
    var borrarTicketState = remember {
        mutableStateOf(false)
    }

    var accion = remember {
        mutableStateOf<Accion?>(null)
    }

    var cargarPantalla = remember {
        mutableStateOf(true)
    }

    Network.networkCallback(navController, context)


    obtenerAccion(ticket.id) {
        accion.value = it
        cargarPantalla.value = false
    }

    if (cargarPantalla.value)
    {
        PantallaCarga()
    }
    else
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor
                ),
                title = {
                    androidx.compose.material3.Text(
                        "Ticket",
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        fontFamily = montserratFamily
                    )
                },
                navigationIcon = {
                    IconButton(modifier = Modifier, onClick = {
                        navController.popBackStack()
                    }
                    ) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Volver")
                    }
                }
            )
        },
        //Color de fondo
        containerColor = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919)
    )
    {

        Column(
            modifier = Modifier.fillMaxSize().padding(15.dp).verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center
        )
        {
            Spacer(modifier = Modifier.padding(50.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
            {
                Icon(
                    painter = painterResource(iconos[ticket.idTipoTicket - 1]),
                    contentDescription = "",
                    modifier = Modifier.size(50.dp)
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                "Ticket - " + ticket.tipo.tipoTicket,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = fuenteLetraTicketDesplegado
            )
            Spacer(modifier = Modifier.padding(5.dp))
            HorizontalDivider(modifier = Modifier.height(5.dp))
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "TICKET: ", fontSize = fuenteLetraTicketDesplegado)
            Text(text = "${ticket.id} \n", fontWeight = FontWeight.Bold, fontSize = fuenteLetraTicketDesplegado)

            Text(text = "FECHA Y HORA: ", fontSize = fuenteLetraTicketDesplegado)
            Text(
                text = "${ticket.fecha} ${ticket.hora} \n",
                fontWeight = FontWeight.Bold,
                fontSize = fuenteLetraTicketDesplegado
            )

            Text(text = "DESCRIPCIÓN: ", fontSize = fuenteLetraTicketDesplegado)
            Text(
                text = "${ticket.descripcion} \n",
                fontWeight = FontWeight.Bold,
                fontSize = fuenteLetraTicketDesplegado
            )

            Text(text = "PRIORIDAD: ", fontSize = fuenteLetraTicketDesplegado)
            Text(
                text = "${ticket.prioridad.nivel} \n",
                fontWeight = FontWeight.Bold,
                fontSize = fuenteLetraTicketDesplegado
            )

            Text(text = "CLIENTE INTERNO: ", fontSize = fuenteLetraTicketDesplegado)
            Text(
                text = "${ticket.clienteInterno.empleado.primerNombre} ${ticket.clienteInterno.empleado.segundoNombre} ${ticket.clienteInterno.empleado.primerApellido} ${ticket.clienteInterno.empleado.segundoApellido} \n",
                fontWeight = FontWeight.Bold,
                fontSize = fuenteLetraTicketDesplegado
            )

            Text(text = "TELÉFONO: ", fontSize = fuenteLetraTicketDesplegado)
            Text(
                text = "${ticket.clienteInterno.empleado.telefonoEmpleado.codigoOperadoraTelefono.operadora}-${ticket.clienteInterno.empleado.telefonoEmpleado.extension} \n",
                fontWeight = FontWeight.Bold,
                fontSize = fuenteLetraTicketDesplegado
            )

            Text(text = "SEDE: ", fontSize = fuenteLetraTicketDesplegado)
            Text(
                text = "${ticket.clienteInterno.empleado.departamento.sede.nombre} \n",
                fontWeight = FontWeight.Bold,
                fontSize = fuenteLetraTicketDesplegado
            )

            Text(text = "DEPARTAMENTO: ", fontSize = fuenteLetraTicketDesplegado)
            Text(
                text = "${ticket.clienteInterno.empleado.departamento.piso} - ${ticket.clienteInterno.empleado.departamento.nombre}",
                fontWeight = FontWeight.Bold,
                fontSize = fuenteLetraTicketDesplegado
            )
            Spacer(modifier = Modifier.padding(15.dp))

            // Si el ticket ya fue asignado, pero no cancelado, se muestra la fecha y hora de su asignacion
            if (ticket.idEstadoTicket > 1 && ticket.idEstadoTicket != 6)
            {
                Text(text = "FECHA Y HORA DE LA ASIGNACIÓN DEL TICKET: ", fontSize = fuenteLetraTicketDesplegado)
                Text(
                    text = "${ticket.fechaAsignacion} - ${ticket.horaAsignacion}  \n",
                    fontWeight = FontWeight.Bold,
                    fontSize = fuenteLetraTicketDesplegado
                )

                Text(text = "TÉCNICO ENCARGADO: ", fontSize = fuenteLetraTicketDesplegado)
                Text(
                    text = "${ticket.tecnico.empleado.primerNombre} ${ticket.tecnico.empleado.segundoNombre} ${ticket.tecnico.empleado.primerApellido} ${ticket.tecnico.empleado.segundoApellido}\n",
                    fontWeight = FontWeight.Bold,
                    fontSize = fuenteLetraTicketDesplegado
                )

                Text(text = "GRUPO DE ATENCIÓN : ", fontSize = fuenteLetraTicketDesplegado)
                Text(
                    text = "${ticket.tecnico.grupoAtencion.grupoAtencion} \n",
                    fontWeight = FontWeight.Bold,
                    fontSize = fuenteLetraTicketDesplegado
                )
            }

            //Si el ticket esta cerrado, se muestran los datos de la accion
            if (accion.value != null)
            {

                Text(text = "ACCIÓN EJECUTADA: ", fontSize = fuenteLetraTicketDesplegado)
                Text(
                    text = "${accion.value!!.descripcionAccion.descripcion} \n",
                    fontWeight = FontWeight.Bold,
                    fontSize = fuenteLetraTicketDesplegado
                )

                Text(text = "FECHA Y HORA DE LA ACCIÓN: ", fontSize = fuenteLetraTicketDesplegado)
                Text(
                    text = "${accion.value!!.fecha} - ${accion.value!!.hora}  \n",
                    fontWeight = FontWeight.Bold,
                    fontSize = fuenteLetraTicketDesplegado
                )

                Text(text = "OBSERVACIONES: ", fontSize = fuenteLetraTicketDesplegado)
                Text(
                    text = "${ticket.observaciones} \n",
                    fontWeight = FontWeight.Bold,
                    fontSize = fuenteLetraTicketDesplegado
                )

            }

            function()

            Spacer(modifier = Modifier.padding(50.dp))

        }
    }

    var alertdialogRealTime = remember{
        mutableStateOf(false)
    }


    realtimeTicketCambiosCancelado(ticket) {

        if (it == true)
        {
            alertdialogRealTime.value = true
            //navController.popBackStack()
        }
    }

    if (alertdialogRealTime.value)
    {

        AlertDialogPersonalizado(
            titulo = "Atención.",
            contenido = "El ticket que ha desplegado ha sido cancelado. Pulse Aceptar para volver.",
            onDismissRequest = {false},
            aceptarAccion = {
                    navController.popBackStack()
                    alertdialogRealTime.value = false},
            cancelarAccion = { }
        )

    }

    if (borrarTicketState.value)
    {

        val scope = rememberCoroutineScope()
        var borrarTicketBandera = remember { mutableStateOf(false) }
        AlertDialogPersonalizado(
            titulo = "Cancelar Ticket",
            contenido = "¿Deseas cancelar el ticket?",
            onDismissRequest = {
                borrarTicketState.value = false
            },
            aceptarAccion = {
                borrarTicketBandera.value = true
            },
            cancelarAccion = {

                Text("Cancelar", modifier = Modifier.clickable{
                    borrarTicketState.value = false
                })

            }
        )

        if (borrarTicketBandera.value) {

            LaunchedEffect(Unit) {

                scope.launch {
                    TicketRequests().cancelarTicket(ticket){}

                }
            }
            Toast.makeText(context, "Ticket cancelado con éxito.", Toast.LENGTH_SHORT).show()
            borrarTicketBandera.value = false
            borrarTicketState.value = false
            navController.popBackStack()
        }

    }

}

@Composable
fun obtenerAccion(idTicket: Int, resultado: (Accion?) -> Unit){

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            AccionRequest().seleccionarAccionbyTicketId(idTicket){
                    resultado(it)
            }

        }
    }

}

// Una funcion especial, que devuelve a la pantalla anterior cuando se que el ticket se ha cancelado en la base de datos
@Composable
fun realtimeTicketCambiosCancelado(ticket: Ticket, resultados: (Boolean) -> Unit)
{

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit)
    {

        delay(1000)
        scope.launch {

            CoroutineScope(Dispatchers.IO).launch {

                TicketRequests().realtimeTicketCanceladoRequest(
                    scope = scope,
                    ticket = ticket
                ) {
                    resultados(it)
                }

            }

        }


    }
}

@Preview(showBackground = true)
@Composable
fun TicketDessplegadoPreview() {
    AVANTITIGestionDeIncidenciasTheme {

        //ContenidoTicketDesplegado(Ticket(), {})

    }
}
