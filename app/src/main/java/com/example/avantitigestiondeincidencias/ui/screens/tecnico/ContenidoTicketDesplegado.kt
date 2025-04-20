package com.example.avantitigestiondeincidencias.ui.screens.tecnico

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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.screens.componentes.AlertDialogPersonalizado
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.launch

val fuenteLetraTicketDesplegado = 15.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContenidoTicketDesplegado(navController: NavController, ticket: Ticket, function: @Composable () -> Unit)
{


    val iconos = listOf<Int>(R.drawable.incidencia_icon, R.drawable.clipboard_question_solid, R.drawable.screwdriver_wrench_solid, R.drawable.gears_solid)
    val scrollState = rememberScrollState()
    var borrarTicketState = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    Color.White
                ),
                title = {
                    androidx.compose.material3.Text(
                        "Ticket",
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(modifier = Modifier, onClick = {
                        navController.popBackStack()
                    }
                    ) {
                        androidx.compose.material3.Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Volver")
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
                androidx.compose.material3.Icon(
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

            function()

            Spacer(modifier = Modifier.padding(50.dp))

        }
    }

    if (borrarTicketState.value)
    {

        val scope = rememberCoroutineScope()
        var borrarTicketBandera = remember { mutableStateOf(false) }
        AlertDialogPersonalizado(
            titulo = "Borrar Ticket",
            contenido = "¿Deseas borrar el ticket?",
            onDismissRequest = {
                borrarTicketState.value = false
            },
            aceptarAccion = {
                borrarTicketBandera.value = true
            },
            cancelarAccion = {
                borrarTicketState.value = false
            }
        )

        if (borrarTicketBandera.value) {

            LaunchedEffect(Unit) {

                scope.launch {
                    TicketRequests().borrarTicket(ticket)

                }
            }
            Toast.makeText(context, "Ticket borrado con éxito.", Toast.LENGTH_SHORT).show()
            borrarTicketBandera.value = false
            borrarTicketState.value = false
            navController.popBackStack()
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
