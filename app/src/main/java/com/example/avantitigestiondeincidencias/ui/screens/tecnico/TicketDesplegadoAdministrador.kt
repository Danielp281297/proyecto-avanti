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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
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
import com.example.avantitigestiondeincidencias.AVANTI.Ticket
import com.example.avantitigestiondeincidencias.R
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

        ContenidoTicketDesplegado(navController, ticket) {

            //if(mostrarSpinnerState.value) {
            Text("TÉCNICO : ", fontSize = fuenteLetraTicketDesplegado)
            Spinner(
                modifier = Modifier,
                itemList = tecnicosList,
                onItemSelected = { option ->

                    tecnicosListState.value = tecnicosList.indexOf(option) + 1

                })
            //}

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
                androidx.compose.material3.Text(text = "ASIGNAR TICKET", color = Color.White)
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
