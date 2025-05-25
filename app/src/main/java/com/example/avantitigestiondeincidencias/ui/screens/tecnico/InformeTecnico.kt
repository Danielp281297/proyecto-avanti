package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.annotation.SuppressLint
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.avantitigestiondeincidencias.AVANTI.Accion
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.Notification.Notification
import com.example.avantitigestiondeincidencias.Supabase.AccionRequest
import com.example.avantitigestiondeincidencias.ViewModel.InformeTecnicoViewModel
import com.example.avantitigestiondeincidencias.ui.screens.componentes.BotonPersonalizado
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldSimplePersonalizado
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.LocalDate


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformeTecnico(
    tecnico: Tecnico,
    viewModel: InformeTecnicoViewModel = viewModel(),
    containerColor: Color = Color.Transparent
)
{

    val context = LocalContext.current
    
    val fechaFinal = viewModel.fechaFinal.collectAsState()
    val fechaInicial = viewModel.fechaInicial.collectAsState()
    val listaAcciones = viewModel.listaAcciones.collectAsState()

    var showDatePickerState = remember {
        mutableStateOf(false)
    }

    var banderaState = remember {
        mutableStateOf(0)
    }

    var habilitarCargarDatosState = remember{
        mutableStateOf(false)
    }

    var buscarAccionesByIdState = remember {
        mutableStateOf(false)
    }

    var  habilitarBotonGuardarInformeState = remember{
        mutableStateOf(false)
    }

    val horizontalScrollState = rememberScrollState()
    val verticalScrollState = rememberScrollState()


    ScaffoldSimplePersonalizado(
        tituloPantalla = "Informe",
        containerColor = containerColor
    ) {

        Column(modifier = Modifier
            .padding(25.dp)
            .fillMaxSize())
        {

            Spacer(modifier = Modifier.padding(40.dp))

            Column(modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(state = verticalScrollState, enabled = true), verticalArrangement = Arrangement.SpaceEvenly)
            {

                Text(text = "Ingrese los campos correspondientes para imprimir el informe.", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                Row(modifier = Modifier.fillMaxWidth())
                {

                    Column (modifier = Modifier.weight(1F))
                    {

                        Text("Fecha Inicial:")
                        Column (modifier = Modifier.clickable {
                            showDatePickerState.value = true
                            banderaState.value = 1
                        })
                        {

                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(
                                text = fechaInicial.value,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp
                            )

                            HorizontalDivider(thickness = 1.dp)
                        }

                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Column (modifier = Modifier.weight(1F))
                    {
                        Text("Fecha Final:")
                        Column (modifier = Modifier.clickable {
                            showDatePickerState.value = true
                            banderaState.value = 2
                        })
                        {
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(text = fechaFinal.value, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 18.sp)
                            HorizontalDivider(thickness = 1.dp)

                        }
                    }
                }

                BotonPersonalizado(onClick = {
                    //Se buscan los datos de los tickets cerrados, en base a las acciones ejecutadas y el id del
                    // tecnico, y se obtienen estos datos
                    buscarAccionesByIdState.value = true

                },
                    enabled = habilitarCargarDatosState.value)
                {
                    Text(text = "CARGAR DATOS", fontFamily = montserratFamily)
                }

                Column(
                    modifier = Modifier.fillMaxWidth().
                    height(240.dp).
                    border(1.dp, Color.Black, RectangleShape)
                ) {

                    LazyColumn(modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(state = horizontalScrollState, enabled = true))
                    {

                        items(listaAcciones.value.count()) { index ->

                            accionFila(listaAcciones.value[index])

                        }

                    }

                }

                BotonPersonalizado(
                    onClick = {
                        viewModel.generarInforme(context)
                    },
                    enabled = habilitarBotonGuardarInformeState.value,
                ) {
                    Text(text = "GUARDAR INFORME", fontFamily = montserratFamily)
                }

                Spacer(modifier = Modifier.padding(40.dp))

            }

        }

    }

    if (viewModel.validarFechas())
    {

        habilitarCargarDatosState.value = true

    }
    else
        habilitarCargarDatosState.value = false

    if(buscarAccionesByIdState.value)
    {

        ObtenerAcciones(viewModel, tecnico.id) {
            buscarAccionesByIdState.value = false
        }

    }

    if (listaAcciones.value.count() > 0)
    {
        habilitarBotonGuardarInformeState.value = true
    }
    else
        habilitarBotonGuardarInformeState.value = false

    if (showDatePickerState.value)
    {
        var datePickerInput = ""

        com.example.avantitigestiondeincidencias.ui.screens.componentes.DatePicker(
            showDialog = showDatePickerState.value,
            containerColor = containerColor,
            ondismiss = { showDatePickerState.value = false },
            fecha = {
                datePickerInput = it

                if (banderaState.value == 1) {
                    viewModel.setFechaInicial(datePickerInput)
                } else if (banderaState.value == 2) {
                    viewModel.setFechaFinal(datePickerInput)
                }

                showDatePickerState.value = false
            })

    }

}

@Composable
fun ObtenerAcciones(
    viewModel: InformeTecnicoViewModel,
    idTecnico: Int,
    acciones: () -> Unit)
{

    LaunchedEffect(Unit)
    {

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.obtenerTicketsResueltos(idTecnico)
            acciones()
        }

    }

}



@Composable
fun accionFila(accion: Accion)
{
    Row(horizontalArrangement = Arrangement.SpaceBetween) {

        Text(accion.ticket.id.toString())
        Text("${accion.ticket.clienteInterno.empleado.primerNombre} ${accion.ticket.clienteInterno.empleado.primerApellido}")
        Text(accion.ticket.clienteInterno.empleado.departamento.sede.nombre)
        Text(accion.ticket.clienteInterno.empleado.departamento.piso.toString())
        Text(accion.ticket.tipo.tipoTicket)
        Text(accion.ticket.descripcion)
        Text(accion.ticket.fecha)
        Text(accion.ticket.hora)
        Text(accion.descripcionAccion.descripcion)
        Text(accion.fecha)
        Text(accion.hora)
        Text(accion.ticket.estado.tipoEstado)
        Text(accion.ticket.tecnico.grupoAtencion.grupoAtencion)
        Text("${accion.ticket.tecnico.empleado.primerNombre} ${accion.ticket.tecnico.empleado.primerApellido}")
        Text(accion.ticket.observaciones)
    }
}

@Preview(showBackground = true)
@Composable
fun InformeTecnicoPreview() {

    AVANTITIGestionDeIncidenciasTheme {

        InformeTecnico(Tecnico())

    }
}
