package com.example.avantitigestiondeincidencias.ui.screens.administrador

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Network.Network
import com.example.avantitigestiondeincidencias.Supabase.TecnicoRequest
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.screens.componentes.AlertDialogPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.Spinner
import com.example.avantitigestiondeincidencias.ui.screens.ticket.ContenidoTicketDesplegado
import com.example.avantitigestiondeincidencias.ui.screens.PantallaCarga
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketDesplegadoAdministrador(navController: NavController, context: Context, ticket: Ticket)
{

    val fuenteLetraTicketDesplegado = 15.sp

    val prioridadTicket = listOf<String>("Seleccione --", "BAJA", "MEDIA", "ALTA", "URGENTE")

    var prioridadState = remember {
        mutableStateOf(ticket.idPrioridadTicket)
    }

    var tecnicosListState = remember {
        mutableStateOf(ticket.idTecnico)
    }

    var tecnicosList = remember {
        mutableListOf<String>("-- Seleccione")
    }

    var actualizarTicketState = remember{
        mutableStateOf(false)
    }

    var mostrarSpinnerState = remember{
        mutableStateOf(false)
    }

    var borrarTicketState = remember {
        mutableStateOf(false)
    }

    var cargandoContenidoSpinners = remember{
        mutableStateOf(true)
    }

    Network.networkCallback(navController, context)

    LaunchedEffect(Unit)
    {
        withContext(Dispatchers.IO)
        {
            TecnicoRequest().seleccionarTecnicos { tecnicos ->

                tecnicos.forEach { item ->

                    tecnicosList.add("${item.empleado.primerNombre} ${item.empleado.primerApellido} - ${item.grupoAtencion.grupoAtencion}")
                    mostrarSpinnerState.value = true
                    cargandoContenidoSpinners.value = false

                }

            }
        }
    }

    if(cargandoContenidoSpinners.value)
    {
        PantallaCarga()
    }
    else
        ContenidoTicketDesplegado(navController, context, ticket) {

            if (ticket.idEstadoTicket < 4) {
                Column(modifier = Modifier)
                {
                    Text("PRIORIDAD")
                    Spinner(
                        modifier = Modifier.fillMaxWidth(),
                        itemList = prioridadTicket,
                        posicionInicial = prioridadState.value,
                        onItemSelected = { option ->
                            prioridadState.value = prioridadTicket.indexOf(option) + 1
                        })
                }
                Spacer(modifier = Modifier.padding(5.dp))

                Text("TÉCNICO : ", fontSize = fuenteLetraTicketDesplegado)
                Spinner(
                    modifier = Modifier.fillMaxWidth(),
                    itemList = tecnicosList,
                    posicionInicial = tecnicosListState.value,
                    onItemSelected = { option ->

                        tecnicosListState.value = tecnicosList.indexOf(option) + 1

                    })

                Spacer(modifier = Modifier.padding(15.dp))
                Button(
                    modifier = modeloButton,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RectangleShape,
                    onClick = {

                        // Si tecnicoListState es mayor a 1, se crea la consulta
                        if ((tecnicosListState.value > 1) && (prioridadState.value > 0 && prioridadState.value < 6)) {
                            Log.d("RESULTADO", "ADMITIDO")

                            //Se regresa a la pantalla anterior
                            actualizarTicketState.value = true
                            Toast.makeText(context, "Técnico encargado con éxito.", Toast.LENGTH_SHORT).show()

                        } else {
                            Log.e("RESULTADO", "NO ADMITIDO")
                            Toast.makeText(
                                context,
                                "Por favor, complete los campos correspondientes.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                    }
                )
                {
                    androidx.compose.material3.Text(text = "ASIGNAR TICKET", color = Color.White)
                }
                Spacer(modifier = Modifier.padding(5.dp))


                if (ticket.idEstadoTicket < 4) {
                    Button(
                        modifier = modeloButton,

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black
                        ),
                        shape = RectangleShape,
                        onClick = {

                            borrarTicketState.value = true

                        }
                    )
                    {
                        androidx.compose.material3.Text(text = "CANCELAR TICKET", color = Color.White)
                    }
                }

            }

        }

    if (actualizarTicketState.value == true)
    {


        LaunchedEffect(Unit) {

            CoroutineScope(Dispatchers.IO).launch{

                TicketRequests().updateTicketEnProcesoTecnicoEstadoById(ticket.id, prioridadState.value, tecnicosListState.value)

            }

            //Se devuelve a la pantalla anterior
            navController.popBackStack()
            actualizarTicketState.value = false
        }

    }


    if (borrarTicketState.value)
    {

        val scope = rememberCoroutineScope()
        var borrarTicketBandera = remember { mutableStateOf(false) }
        AlertDialogPersonalizado(
            titulo = "Cancelar Ticket",
            contenido = "¿Deseas cancelar el ticket?",
            onDismissRequest = { borrarTicketState.value = false },
            aceptarAccion = {
                borrarTicketBandera.value = true
            },
            cancelarAccion = {

                Text("CANCELAR", color = Color.Black, modifier = Modifier.clickable {

                    borrarTicketState.value = false

                })

            }
        )

        if (borrarTicketBandera.value) {

            LaunchedEffect(Unit) {

                scope.launch {
                    TicketRequests().cancelarTicket(ticket){

                        if(it != null)
                        {
                            Toast.makeText(context, "Ticket cancelado con éxito.", Toast.LENGTH_SHORT).show()
                        }
                        else
                            Toast.makeText(context, "Error al cancelar el ticket. Intente de nuevo", Toast.LENGTH_SHORT).show()

                    }

                }
            }
            borrarTicketBandera.value = false
            borrarTicketState.value = false
            navController.popBackStack()
        }

    }

}

@Preview(showBackground = true)
@Composable
fun TicketDesplegadoAdministradorPreview() {

    val navController = rememberNavController()
    val context = LocalContext.current

    AVANTITIGestionDeIncidenciasTheme {

        TicketDesplegadoAdministrador(navController, context, Ticket())

    }
}
