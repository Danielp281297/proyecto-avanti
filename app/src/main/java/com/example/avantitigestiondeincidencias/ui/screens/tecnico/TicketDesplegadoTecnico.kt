package com.example.avantitigestiondeincidencias.ui.screens.tecnico

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
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
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.Supabase.AccionRequest
import com.example.avantitigestiondeincidencias.Supabase.TecnicoRequest
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.screens.componentes.AutocompleteTextField
import com.example.avantitigestiondeincidencias.ui.screens.componentes.Spinner
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketDesplegadoTecnico(navController: NavController, ticket: Ticket)
{

    val image = R.drawable.ticket_solid

    val context = LocalContext.current

    val scrollState = rememberScrollState()

    val fuenteLetraTicketDesplegado = 15.sp

    var descripcionAccionState = remember { mutableStateOf("") }

    var descripcionAccionList = remember {
        mutableListOf<DescripcionAccion>()
    }

    var observacionesState = remember { mutableStateOf("SIN OBSERVACIONES") }

    var mostrarTextfieldState = remember{
        mutableStateOf(false)
    }

    var cerrarTicketState = remember{ mutableStateOf(false) }

    LaunchedEffect(Unit)
    {
        withContext(Dispatchers.IO)
        {
            descripcionAccionList.addAll(AccionRequest().buscarDescripcionAccion())
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    Color.White
                ),
                title = {
                    androidx.compose.material3.Text(
                        "Ticket - " + ticket.tipo.tipoTicket,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            )
        },
        //Color de fondo
        containerColor = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919)
    )
    {

        Column(modifier = Modifier.fillMaxSize().padding(25.dp).verticalScroll(state = scrollState, enabled = true), verticalArrangement = Arrangement.Center)
        {
            Spacer(modifier = Modifier.padding(50.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
            {
                androidx.compose.material3.Icon(
                    painter = painterResource(image),
                    contentDescription = "",
                    modifier = Modifier.size(50.dp).rotate(135F)
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Text("Ticket - " + ticket.tipo.tipoTicket,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = fuenteLetraTicketDesplegado
            )
            Spacer(modifier = Modifier.padding(5.dp))
            HorizontalDivider(modifier = Modifier.height(5.dp))

            Spacer(modifier = Modifier.padding(30.dp))
            Text(text = "TICKET: ", fontSize = fuenteLetraTicketDesplegado)
            Text(text = "${ticket.id} \n", fontWeight = FontWeight.Bold, fontSize = fuenteLetraTicketDesplegado)

            Text(text = "FECHA Y HORA: ", fontSize = fuenteLetraTicketDesplegado)
            Text(text = "${ticket.fecha} ${ticket.hora} \n", fontWeight = FontWeight.Bold, fontSize = fuenteLetraTicketDesplegado)

            Text(text = "DESCRIPCIÓN: ", fontSize = fuenteLetraTicketDesplegado)
            Text(text = "${ticket.descripcion} \n", fontWeight = FontWeight.Bold, fontSize = fuenteLetraTicketDesplegado)

            Text(text = "PRIORIDAD: ", fontSize = fuenteLetraTicketDesplegado)
            Text(text = "${ticket.prioridad.nivel} \n", fontWeight = FontWeight.Bold, fontSize = fuenteLetraTicketDesplegado)

            Text(text = "CLIENTE INTERNO: ", fontSize = fuenteLetraTicketDesplegado)
            Text(text = "${ticket.clienteInterno.empleado.primerNombre} ${ticket.clienteInterno.empleado.segundoNombre} ${ticket.clienteInterno.empleado.primerApellido} ${ticket.clienteInterno.empleado.segundoApellido} \n", fontWeight = FontWeight.Bold, fontSize = fuenteLetraTicketDesplegado)

            Text(text = "TELÉFONO: ", fontSize = fuenteLetraTicketDesplegado)
            Text(text = "${ticket.clienteInterno.empleado.telefonoPersona.codigoOperadoraTelefono.operadora}-${ticket.clienteInterno.empleado.telefonoPersona.extension} \n", fontWeight = FontWeight.Bold, fontSize = fuenteLetraTicketDesplegado)

            Text(text = "SEDE: ", fontSize = fuenteLetraTicketDesplegado)
            Text(text = "${ticket.clienteInterno.empleado.departamento.sede.nombre} \n", fontWeight = FontWeight.Bold, fontSize = fuenteLetraTicketDesplegado)

            Text(text = "DEPARTAMENTO: ", fontSize = fuenteLetraTicketDesplegado)
            Text(text = "${ticket.clienteInterno.empleado.departamento.piso} - ${ticket.clienteInterno.empleado.departamento.nombre}", fontWeight = FontWeight.Bold, fontSize = fuenteLetraTicketDesplegado)
            Spacer(modifier = Modifier.padding(15.dp))

            Text("ACCIÓN EJECUTADA: ", fontSize = fuenteLetraTicketDesplegado)
            AutocompleteTextField(
                initialText = descripcionAccionState.value,
                label = "Indique aqui",
                suggestions = descripcionAccionList.map { it.descripcion },
                onClearResults = {},
                modifier = Modifier.fillMaxWidth(),
            ) { descripcionAccionState.value = it.text }

            Spacer(modifier = Modifier.padding(15.dp))
            Text("OBSERVACIONES: ", fontSize = fuenteLetraTicketDesplegado)

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().focusRequester(focusRequester = FocusRequester.Default),
                value = observacionesState.value,
                onValueChange = { newText ->
                    // Si el texto es menor a 50 caracteres, se almacena en newText
                    if (newText.length <= 100)
                        observacionesState.value = newText
                },
                label = { androidx.compose.material3.Text("Indique aqui", fontSize = 13.sp) },
                placeholder = {  },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Black,
                    focusedBorderColor = Color.Black),
                supportingText = {

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Spacer(modifier = Modifier.padding(0.dp))

                        androidx.compose.material3.Text(
                            text = "${observacionesState.value.length}/100",
                            color = if (observacionesState.value.length < 95) Color.LightGray else Color.Red,
                            modifier = Modifier
                        )
                    }


                }
            )

            Spacer(modifier = Modifier.padding(15.dp))
            Button(modifier = modeloButton,

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                shape = RectangleShape,
                onClick = {

                    // Si la accion ejecutada o la descripcion no estan vacios, se crea la accion y se actualiza el ticket en la base de datos
                    if (descripcionAccionState.value.isNotEmpty() && observacionesState.value.isNotEmpty())
                    {
                        Log.d("RESULTADO", "ADMITIDO")

                        //Se regresa a la pantalla anterior
                        cerrarTicketState.value = true
                        Toast.makeText(context, "Ticket cerrado con éxito.", Toast.LENGTH_SHORT).show()

                    }
                    else {
                        Log.e("RESULTADO", "NO ADMITIDO")
                        Toast.makeText(context, "Por favor, llene los campos correspondientes.", Toast.LENGTH_SHORT).show()
                    }
                    //Se cambia el estado de Abierto a En proceso
                    //Se cambia el nombre del tecnico


                }
            )
            {
                androidx.compose.material3.Text(text = "ACTUALIZAR TICKET", color = Color.White)
            }

            Spacer(modifier = Modifier.padding(30.dp))
        }

    }


    if (cerrarTicketState.value == true)
    {

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {

                //Se crea la fila de la accion, y se actualiza el estado del ticket
                TicketRequests().cerrarTicket(ticket, observacionesState.value)

                AccionRequest()
                    .cerrarTicket(ticket, descripcionAccionState.value)
                Log.d("Indice", "${AccionRequest().seleccionarAccion(descripcionAccionState.value)}")

            }

            cerrarTicketState.value == false
            navController.popBackStack()

        }


    }

}

@Preview(showBackground = true)
@Composable
fun TicketDesplegadoTecnicoPreview() {

    val navController = rememberNavController()

    AVANTITIGestionDeIncidenciasTheme {

        TicketDesplegadoTecnico(navController, Ticket())
    }
}