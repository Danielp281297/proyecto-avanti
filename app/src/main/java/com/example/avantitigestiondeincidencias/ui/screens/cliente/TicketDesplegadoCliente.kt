package com.example.avantitigestiondeincidencias.ui.screens.cliente

import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import android.media.VolumeShaper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.ViewModel.TicketDesplegadoClienteViewModel
import com.example.avantitigestiondeincidencias.ui.screens.componentes.AlertDialogPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.BotonPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.SliderPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.ticket.ContenidoTicketDesplegado
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import io.ktor.util.converters.DelegatingConversionService
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketDesplegadoCliente(
    navController: NavController,
    ticket:Ticket,
    viewModel: TicketDesplegadoClienteViewModel = viewModel()
)
{

    val context = LocalContext.current

    var cancelarTicketState = remember {
        mutableStateOf(false)
    }

    var cerrarTicketState = remember{
        mutableStateOf(false)
    }

    ContenidoTicketDesplegado(navController, context, ticket) {

        if (ticket.idEstadoTicket >= 4) {

            if (ticket.idEstadoTicket == 4) {

                BotonPersonalizado(
                    onClick = {

                        cerrarTicketState.value = true

                    }
                )
                {
                    Text(text = "CERRAR TICKET", color = Color.White)
                }
            }

        } else if (ticket.idEstadoTicket < 3) {
            BotonPersonalizado(
                onClick = {

                    cancelarTicketState.value = true

                }
            )
            {
                Text(text = "CANCELAR TICKET", color = Color.White)
            }
        }

    }

    if (cerrarTicketState.value){

        CalificarTicketDialog(
            context = context,
            viewModel = viewModel,
            navController = navController,
            ticket = ticket,
            dismissButtonAction = {
                cerrarTicketState.value = false
            }
        )

    }

    if (cancelarTicketState.value)
    {

        var cancelarTicketBandera = remember { mutableStateOf(false) }

        AlertDialogPersonalizado(
            titulo = "Cancelar Ticket",
            contenido = "¿Deseas cancelar el ticket?",
            onDismissRequest = {
                cancelarTicketState.value = false
            },
            aceptarAccion = { cancelarTicketBandera.value = true },
            cancelarAccion = {

                Text("CANCELAR", color = if (isSystemInDarkTheme()) Color.LightGray else Color.Black, modifier = Modifier.clickable {

                    cancelarTicketState.value = false

                })

            },
        )

        if (cancelarTicketBandera.value) {

            CancelarTicket(
                context,
                viewModel,
                ticket
            ) {

                cancelarTicketBandera.value = false
                cancelarTicketState.value = false
                navController.popBackStack()

            }

        }

    }

}

@Composable
fun CancelarTicket(
    context: Context,
    viewModel: TicketDesplegadoClienteViewModel,
    ticket: Ticket,
    resultado: () -> Unit)
{

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {

        scope.launch {
            viewModel.cancelarTicket(ticket)
            Toast.makeText(context, "Ticket cancelado con éxito.", Toast.LENGTH_SHORT).show()
            resultado()
        }
    }

}

@Composable
fun CalificarTicketDialog(
    context: Context,
    viewModel: TicketDesplegadoClienteViewModel,
    navController: NavController,
    ticket: Ticket,
    dismissButtonAction: () -> Unit,
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919),
    textColorContainer: Color =  if (isSystemInDarkTheme()) Color.LightGray else Color.Black
)
{

    val scope = rememberCoroutineScope()

    var calificarState = remember{
        mutableStateOf(false)
    }

    val calificacion = viewModel.calificacion.collectAsState()

    // Se obtiene la calificación de la gestión
    AlertDialog(
        shape = RectangleShape,
        containerColor = containerColor,
        onDismissRequest = {

        },
        confirmButton = {

            Text("CALIFICAR", color = textColorContainer, modifier = Modifier.clickable {

                calificarState.value = true

            })

        },
        dismissButton = {

            Text("CANCELAR", color = textColorContainer, modifier = Modifier.clickable {

                dismissButtonAction()

            })

        },
        title = {
            Text("Cerrar Ticket", textAlign = TextAlign.Center)
        },
        text = {

            var iconosEstrellas = remember {
                mutableStateListOf(R.drawable.una_estrella, R.drawable.dos_estrellas, R.drawable.tres_estrellas, R.drawable.cuatro_estrellas, R.drawable.cinco_estrellas)
            }

            Column (verticalArrangement = Arrangement.SpaceEvenly){

                Text("Por favor, califique la gestión de la incidencia", textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.padding(5.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    iconosEstrellas.forEach {

                        Column(modifier = Modifier.wrapContentWidth()) {
                            Icon(
                                painter = painterResource(it),
                                contentDescription = "Estrella",
                                modifier = Modifier.size(40.dp),
                            )
                            Text("    ${iconosEstrellas.indexOf(it) + 1}",textAlign = TextAlign.Center)
                        }

                    }

                }

                SliderPersonalizado(
                    value = calificacion.value.toFloat(),
                    onValueChange = {

                        viewModel.setCalificacion(it.toInt())

                        Log.d("CALIFICACION", (calificacion.value).toString())
                    },
                    punteros = 3,
                    rango = 1f..5f
                )

            }
        }
    )

    if(calificarState.value)
    {

        LaunchedEffect(Unit) {

            scope.launch {

                viewModel.cerrarTicket(ticket){

                    Toast.makeText(context, "Ticket cerrado con éxito.", Toast.LENGTH_SHORT).show()

                    navController.popBackStack()
                }
            }
        }
        calificarState.value = false

    }
}

@Preview
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun preview(){

    val context = LocalContext.current
    val navController = rememberNavController()
    val ticketDesplegadoClienteViewModel = viewModel<TicketDesplegadoClienteViewModel>()
    AVANTITIGestionDeIncidenciasTheme {
        CalificarTicketDialog(
            context = context,
            viewModel = ticketDesplegadoClienteViewModel,
            navController = navController,
            ticket = Ticket(),
            dismissButtonAction = {},
        )
    }

}
