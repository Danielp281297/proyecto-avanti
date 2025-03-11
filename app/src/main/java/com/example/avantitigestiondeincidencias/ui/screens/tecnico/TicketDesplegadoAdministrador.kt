package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.Supabase.TecnicoRequest
import com.example.avantitigestiondeincidencias.Supabase.TicketRequests
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.screens.componentes.Spinner
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketDesplegadoAdministrador(navController: NavController, ticket: Ticket)
{

    val context = LocalContext.current

    val fuenteLetraTicketDesplegado = 15.sp

    var tecnicosListState = remember {
        mutableStateOf(0)
    }

    var tecnicosList = remember {
        mutableListOf<String>("-- Seleccione")
    }

    var actualizarTicetState = remember{
        mutableStateOf(false)
    }

    var mostrarSpinnerState = remember{
        mutableStateOf(false)
    }

    LaunchedEffect(Unit)
    {
        withContext(Dispatchers.IO)
        {
            TecnicoRequest().seleccionarTecnicos { tecnicos ->

                tecnicos.forEach { item ->

                    tecnicosList.add("${item.empleado.primerNombre} ${item.empleado.primerApellido} - ${item.grupoAtencion.grupoAtencion}")
                    mostrarSpinnerState.value = true

                }

            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    Color.Transparent
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

        Column(modifier = Modifier.fillMaxSize().padding(25.dp), verticalArrangement = Arrangement.Center)
        { 
            
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
            Column(modifier = Modifier)
            {
                Text("TÉCNICO : ", fontSize = fuenteLetraTicketDesplegado)

                if(mostrarSpinnerState.value) {
                    Spinner(
                        modifier = Modifier,
                        itemList = tecnicosList,
                        onItemSelected = { option ->

                            tecnicosListState.value = tecnicosList.indexOf(option) + 1

                        })
                }

            }
            Spacer(modifier = Modifier.padding(15.dp))
            Button(modifier = modeloButton,

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                shape = RectangleShape,
                onClick = {

                    // Si tecnicoListState es mayor a 1, se crea la consulta
                    if (tecnicosListState.value > 1)
                    {
                        Log.d("RESULTADO", "ADMITIDO")

                        //Se regresa a la pantalla anterior
                        actualizarTicetState.value = true
                        Toast.makeText(context, "Técnico encargado con éxito.", Toast.LENGTH_SHORT).show()

                    }
                    else {
                        Log.e("RESULTADO", "NO ADMITIDO")
                        Toast.makeText(context, "Por favor, indique el técnico encargado.", Toast.LENGTH_SHORT).show()
                    }

                }
            )
            {
                androidx.compose.material3.Text(text = "ENCARGAR TICKET", color = Color.White)
            }

        }

    }

    if (actualizarTicetState.value == true)
    {

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {

                TicketRequests().updateTicketEnProcesoTecnicoEstadoById(ticket.id, tecnicosListState.value)

            }

            //Se devuelve a la pantalla anterior
            navController.popBackStack()
            actualizarTicetState.value = false
        }

    }

}

@Preview(showBackground = true)
@Composable
fun TicketDesplegadoAdministradorPreview() {

    val navController = rememberNavController()

    AVANTITIGestionDeIncidenciasTheme {

        TicketDesplegadoAdministrador(navController, Ticket())

    }
}
