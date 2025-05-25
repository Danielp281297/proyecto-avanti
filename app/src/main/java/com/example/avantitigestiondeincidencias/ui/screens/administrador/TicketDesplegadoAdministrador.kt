package com.example.avantitigestiondeincidencias.ui.screens.administrador

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Network.Network
import com.example.avantitigestiondeincidencias.Supabase.TecnicoRequest
import com.example.avantitigestiondeincidencias.ViewModel.TicketDesplegadoAdministrador
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
import kotlin.collections.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketDesplegadoAdministrador(
    navController: NavController,
    context: Context,
    ticket: Ticket,
    viewModel: TicketDesplegadoAdministrador = viewModel(),
    labelSpinnerColor: Color = if (isSystemInDarkTheme()) Color.LightGray else Color.Black
)
{

    val fuenteLetraTicketDesplegado = 15.sp

    val prioridadTicket = listOf<String>("Seleccione --", "BAJA", "MEDIA", "ALTA", "URGENTE")

    val idPrioridadTicket = viewModel.prioridadTicket.collectAsState()
    val idTecnico = viewModel.idTecnico.collectAsState()
    val listaTecnicos = viewModel.listaTecnicos.collectAsState()

    var actualizarTicketState = remember{
        mutableStateOf(false)
    }

    var mostrarSpinnerState = remember{
        mutableStateOf(false)
    }

    var cancelarTicketState = remember {
        mutableStateOf(false)
    }

    var cargandoContenidoSpinners = remember{
        mutableStateOf(true)
    }

    Network.networkCallback(navController, context)

    // Se cargan los datos de los tecnicos
    ObtenerNombresAreaTecnicos {

        viewModel.setListaTecnicos(it)
        mostrarSpinnerState.value = true
        cargandoContenidoSpinners.value = false

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
                    Text("PRIORIDAD:", color = labelSpinnerColor)
                    Spinner(
                        modifier = Modifier.fillMaxWidth(),
                        itemList = prioridadTicket,
                        posicionInicial = idPrioridadTicket.value,
                        onItemSelected = { option ->
                            viewModel.setPrioridadTicket(prioridadTicket.indexOf(option) + 1)
                        })
                }
                Spacer(modifier = Modifier.padding(5.dp))
                    Text("TÉCNICO: ", color = labelSpinnerColor, fontSize = fuenteLetraTicketDesplegado)
                    Spinner(
                        modifier = Modifier.fillMaxWidth(),
                        itemList = listaTecnicos.value,
                        posicionInicial = 0,
                        onItemSelected = { option ->

                            // Se obtiene el valor numerico dentro de la cadena de caracteres, que es el unico numero en la cadena
                            // En caso contrario, pasara el numero 0
                            viewModel.setIdTecnico(option)

                        })
                    Spacer(modifier = Modifier.padding(15.dp))


                Button(
                    modifier = modeloButton,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RectangleShape,
                    onClick = {

                        if(viewModel.validarDatosTicket())
                        {
                            Log.e("ASIGNAR TICKET", "PERMITIDO")
                            actualizarTicketState.value = true
                            Toast.makeText(context, "Técnico encargado con éxito.", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Log.e("ASIGNAR TICKET", "NO PERMITIDO")
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

                            cancelarTicketState.value = true

                        }
                    )
                    {
                        androidx.compose.material3.Text(text = "CANCELAR TICKET", color = Color.White)
                    }
                }

            }

        }

    if (actualizarTicketState.value)
    {

        LaunchedEffect(Unit) {

            CoroutineScope(Dispatchers.IO).launch{

                viewModel.asignarTicket(ticket.id)

            }

            //Se devuelve a la pantalla anterior
            navController.popBackStack()
            actualizarTicketState.value = false
        }

    }


    if (cancelarTicketState.value)
    {

        val scope = rememberCoroutineScope()
        var cancelarTicketBandera = remember { mutableStateOf(false) }
        AlertDialogPersonalizado(
            titulo = "Cancelar Ticket",
            contenido = "¿Deseas cancelar el ticket?",
            onDismissRequest = { cancelarTicketState.value = false },
            aceptarAccion = {
                cancelarTicketBandera.value = true
            },
            cancelarAccion = {

                Text("CANCELAR", color = Color.Black, modifier = Modifier.clickable {

                    cancelarTicketState.value = false

                })

            }
        )

        if (cancelarTicketBandera.value) {

            LaunchedEffect(Unit) {

                scope.launch {

                    viewModel.cancelarTicket(ticket){

                        if(it != null)
                        {
                            Toast.makeText(context, "Ticket cancelado con éxito.", Toast.LENGTH_SHORT).show()
                        }
                        else
                            Toast.makeText(context, "Error al cancelar el ticket. Intente de nuevo", Toast.LENGTH_SHORT).show()

                    }

                }
            }
            cancelarTicketBandera.value = false
            cancelarTicketState.value = false
            navController.popBackStack()
        }

    }

}

@Composable
fun ObtenerNombresAreaTecnicos(resultados: (List<String>) -> Unit){

    LaunchedEffect(Unit)
    {
        withContext(Dispatchers.IO)
        {

            TecnicoRequest().seleccionarTecnicos { tecnicos ->

                resultados(tecnicos.map{ "${it.id} ${it.empleado.primerNombre} ${it.empleado.segundoNombre} - ${it.grupoAtencion.grupoAtencion}" })

            }

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
