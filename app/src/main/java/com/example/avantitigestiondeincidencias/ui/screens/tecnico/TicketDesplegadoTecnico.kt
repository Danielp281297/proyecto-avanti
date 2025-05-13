package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.DescripcionAccion
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Network.Network
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.Supabase.AccionRequest
import com.example.avantitigestiondeincidencias.Supabase.TecnicoRequest
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.screens.componentes.AutocompleteTextField
import com.example.avantitigestiondeincidencias.ui.screens.componentes.BotonCargaPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.OutlinedTextFieldPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldSimplePersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.Spinner
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketDesplegadoTecnico(
    navController: NavController,
    context: Context,
    ticket: Ticket,
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919))
{

    val image = R.drawable.ticket_solid

    val scrollState = rememberScrollState()

    val focusRequester = remember{
        FocusRequester()
    }

    val fuenteLetraTicketDesplegado = 15.sp

    var descripcionAccionState = remember { mutableStateOf("") }

    var descripcionAccionList = remember {
        mutableListOf<DescripcionAccion>()
    }

    var observacionesState = remember { mutableStateOf("SIN OBSERVACIONES") }

    val mensajeAccionExistosaState = remember {
        mutableStateOf(false)
    }

    val ingresarbuttonState = remember{
        mutableStateOf(false)
    }

    var cancelarTicketState = remember{ mutableStateOf(false) }

    var cargandoContenidoSpinners = remember{
        mutableStateOf(true)
    }

    Network.networkCallback(navController, context)

    if (cargandoContenidoSpinners.value)
    {
        PantallaCarga()
    }

    LaunchedEffect(Unit)
    {
        withContext(Dispatchers.IO)
        {
            descripcionAccionList.addAll(AccionRequest().buscarDescripcionAccion())
            cargandoContenidoSpinners.value = false
        }
    }

    if(cargandoContenidoSpinners.value)
    {
        PantallaCarga()
    }
    else
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
                    initialText = descripcionAccionState.value,
                    label = "Indique aqui",
                    suggestions = descripcionAccionList.map { it.descripcion },
                    onClearResults = {},
                    modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                ) { descripcionAccionState.value = it.text }

                Spacer(modifier = Modifier.padding(15.dp))
                Text("OBSERVACIONES: ", fontSize = fuenteLetraTicketDesplegado)
                OutlinedTextFieldPersonalizado(
                    modifier = Modifier.fillMaxWidth(),
                    value = observacionesState.value,
                    onValueChange = {newText ->
                        // Si el texto es menor a 50 caracteres, se almacena en newText
                        if (newText.length <= 100)
                            observacionesState.value = newText
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
                        if (descripcionAccionState.value.isNotEmpty() && observacionesState.value.isNotEmpty())
                        {
                            Log.d("RESULTADO", "ADMITIDO")

                            //Se regresa a la pantalla anterior
                            cancelarTicketState.value = true


                        }
                        else {
                            Log.e("RESULTADO", "NO ADMITIDO")
                            Toast.makeText(context, "Por favor, llene los campos correspondientes.", Toast.LENGTH_SHORT).show()
                            ingresarbuttonState.value = false
                        }
                    },
                    isLoading = ingresarbuttonState.value,
                    CuerpoBoton = {
                        Text(text = "INCIDENCIA RESUELTA", color = Color.White, fontFamily = montserratFamily)
                    }
                )

            }
            Spacer(modifier = Modifier.padding(30.dp))

        val view = LocalView.current

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            view.bringToFront()
        }

    }

    if (cancelarTicketState.value == true)
    {

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {

                //Se crea la fila de la accion, y se actualiza el estado del ticket
                TicketRequests().resolverTicket(ticket, observacionesState.value)

                AccionRequest()
                    .insertarAccion(ticket, descripcionAccionState.value)
                mensajeAccionExistosaState.value = true

            }

            ingresarbuttonState.value = false
            cancelarTicketState.value = false
            navController.popBackStack()

        }

    }

    if (mensajeAccionExistosaState.value)
    {
        Toast.makeText(context, "Ticket cerrado con éxito.", Toast.LENGTH_SHORT).show()
        mensajeAccionExistosaState.value = false
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