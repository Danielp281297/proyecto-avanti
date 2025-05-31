package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Network.Network
import com.example.avantitigestiondeincidencias.ViewModel.TicketDesplegadoTecnicoViewModel
import com.example.avantitigestiondeincidencias.ui.screens.componentes.AutocompleteTextField
import com.example.avantitigestiondeincidencias.ui.screens.componentes.BotonCargaPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.OutlinedTextFieldPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldSimplePersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.ticket.ContenidoTicketDesplegado
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketDesplegadoTecnico(
    navController: NavController,
    context: Context,
    ticket: Ticket,
    viewModel: TicketDesplegadoTecnicoViewModel = viewModel(),
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919)) {


    val focusRequester = remember {
        FocusRequester()
    }

    val fuenteLetraTicketDesplegado = 15.sp

    val descripcionAccion = viewModel.descripcionAccion.collectAsState()
    val listaDescripcionAcciones = viewModel.listaDescripcionAcciones.collectAsState()
    val observaciones = viewModel.observaciones.collectAsState()

    val ingresarbuttonState = remember {
        mutableStateOf(false)
    }

    var ticketResuelto = remember { mutableStateOf(false) }

    Network.networkCallback(navController, context)

    ObtenerAcciones(viewModel)

    ScaffoldSimplePersonalizado(
        tituloPantalla = "Ticket",
        containerColor = containerColor
    )
    {
        ContenidoTicketDesplegado(
            navController = navController,
            context = context,
            ticket = ticket,

            ) {

            Text("ACCIÓN EJECUTADA: ", fontSize = fuenteLetraTicketDesplegado)
            AutocompleteTextField(
                initialText = descripcionAccion.value.descripcion,
                label = "Indique aqui",
                suggestions = listaDescripcionAcciones.value.map { it.descripcion },
                onClearResults = {},
                modifier = Modifier.focusRequester(focusRequester).fillMaxWidth().clickable {
                    focusRequester.requestFocus()
                },
            ) { viewModel.setDescripcionAccion(it.text) }

            Spacer(modifier = Modifier.padding(15.dp))
            Text("OBSERVACIONES: ", fontSize = fuenteLetraTicketDesplegado)
            OutlinedTextFieldPersonalizado(
                modifier = Modifier.fillMaxWidth(),
                value = observaciones.value,
                onValueChange = { newText ->
                    // Si el texto es menor a 50 caracteres, se almacena en newText
                    if (newText.length <= 100)
                        viewModel.setObservaciones(newText)
                },
                label = { androidx.compose.material3.Text("Indique aquí", fontSize = 13.sp) },
                supportingText = true,
                imeActionNext = false,
                maximoCaracteres = 100,
            )

            Spacer(modifier = Modifier.padding(15.dp))
            BotonCargaPersonalizado(
                onClick = {
                    ingresarbuttonState.value = true

                    // Si la accion ejecutada o la descripcion no estan vacios, se crea la accion y se actualiza el ticket en la base de datos
                    if (viewModel.validarDatosTicketAccion()) {

                        // Se actualiza el ticket y se regresa a la pantalla anterior
                        Log.d("RESULTADO", "ADMITIDO")
                        ticketResuelto.value = true


                    } else {
                        Log.e("RESULTADO", "NO ADMITIDO")
                        Toast.makeText(context, "Por favor, llene los campos correspondientes.", Toast.LENGTH_SHORT)
                            .show()
                        ingresarbuttonState.value = false
                    }
                },
                enabled = !ingresarbuttonState.value,
                isLoading = ingresarbuttonState.value,
                CuerpoBoton = {
                    Text(text = "INCIDENCIA RESUELTA", color = Color.White, fontFamily = montserratFamily)
                }
            )

        }
        Spacer(modifier = Modifier.padding(30.dp))

    }

    if (ticketResuelto.value)
    {

        ResolverTicket(
            context,
            viewModel,
            ticket
        ) {

            ingresarbuttonState.value = false
            ticketResuelto.value = false
            navController.popBackStack()

        }

    }

}

@Composable
fun ResolverTicket(
    context: Context,
    viewModel: TicketDesplegadoTecnicoViewModel,
    ticket: Ticket,
    resultados: () -> Unit)
{

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {

            //Se crea la fila de la accion, y se actualiza el estado del ticket
            viewModel.IncidenciaResuelta(ticket)
            Toast.makeText(context, "Ticket cerrado con éxito.", Toast.LENGTH_SHORT).show()
            resultados()

        }

    }
}

@Composable
fun ObtenerAcciones(viewModel: TicketDesplegadoTecnicoViewModel){

    LaunchedEffect(Unit)
    {
        withContext(Dispatchers.IO)
        {
            viewModel.setListaDescripcionAcciones()

        }
    }

}

@Preview(showBackground = true)
@Composable
fun TicketDesplegadoTecnicoPreview() {

    val navController = rememberNavController()
    val context = LocalContext.current
    AVANTITIGestionDeIncidenciasTheme {

        TicketDesplegadoTecnico(navController, context, Ticket())
    }
}