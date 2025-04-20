package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
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
import com.example.avantitigestiondeincidencias.AVANTI.Accion
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.Notification.Notification
import com.example.avantitigestiondeincidencias.Supabase.AccionRequest
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.time.LocalDate
import java.util.Date

val weightColumnas = 1F

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformeTecnico(tecnico: Tecnico)
{

    val context = LocalContext.current

    var showDatePickerState = remember {
        mutableStateOf(false)
    }

    var fechaInicioState = remember {
        mutableStateOf<String>("")
    }

    var fechaFinalState = remember {
        mutableStateOf<String>("")
    }

    var banderaState = remember {
        mutableStateOf(0)
    }

    var habilitarCargarDatosState = remember{
        mutableStateOf(false)
    }

    var accionesList = remember {
        mutableStateListOf<Accion>()
    }

    var buscarAccionesByIdState = remember {
        mutableStateOf(false)
    }

    var  habilitarBotonGuardarInformeState = remember{
        mutableStateOf(false)
    }

    var generarExcelState = remember{
        mutableStateOf(false)
    }

    val horizontalScrollState = rememberScrollState()

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.White),
                title = {
                    Text("Informe", modifier = Modifier.fillMaxWidth().padding(0.dp), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
                }
            )
        }
    ) {

        val verticalScrollState = rememberScrollState()

        Column(modifier = Modifier.padding(25.dp).fillMaxSize())
        {



            Spacer(modifier = Modifier.padding(40.dp))

            Column(modifier = Modifier.fillMaxHeight().verticalScroll(state = verticalScrollState, enabled = true), verticalArrangement = Arrangement.SpaceEvenly)
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
                                text = fechaInicioState.value,
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
                            Text(text = fechaFinalState.value, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 18.sp)
                            HorizontalDivider(thickness = 1.dp)

                        }

                    }
                }

                Button(enabled = habilitarCargarDatosState.value,
                    modifier = modeloButton,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RectangleShape,
                    onClick = {

                        //Se buscan los datos de los tickets cerrados, en base a las acciones ejecutadas y el id del
                        // tecnico, y se obtienen estos datos
                        buscarAccionesByIdState.value = true

                    })
                {
                    Text(text = "CARGAR DATOS")
                }

                //Column() {
                    /*
                    Row(modifier = Modifier.wrapContentWidth()){
                        Text("Ticket", modifier = Modifier)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text("Usuario", modifier = Modifier)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text("Sede", modifier = Modifier)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text("Piso", modifier = Modifier)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text("Tipo", modifier = Modifier)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text("Descripción", modifier = Modifier)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text("Fecha ticket", modifier = Modifier)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text("Hora ticket", modifier = Modifier)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text("Acción Ejecutada", modifier = Modifier)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text("Fecha Accion", modifier = Modifier)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text("Hora Accion", modifier = Modifier)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text("Estado", modifier = Modifier)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text("Grupo de Atención", modifier = Modifier)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text("Técnico", modifier = Modifier)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text("Observaciones", modifier = Modifier)
                        Spacer(modifier = Modifier.padding(5.dp))

                        Text("Id_ticket", modifier = Modifier)
                    }
                    */
                    LazyColumn(modifier = Modifier.fillMaxWidth().height(240.dp).background(Color.LightGray).horizontalScroll(state = horizontalScrollState, enabled = true))
                    {

                        items(accionesList.count()){ index ->

                            accionFila(accionesList[index])

                        }

                    }
               // }

                Button(enabled = habilitarBotonGuardarInformeState.value,
                    modifier = modeloButton,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RectangleShape,
                    onClick = {
                        generarExcelState.value = true
                    })
                {
                    Text(text = "GUARDAR INFORME")
                }

                Spacer(modifier = Modifier.padding(40.dp))

            }

        }


    }

    if(generarExcelState.value)
    {

        if (accionesList.isNotEmpty()) {
            generalInformeExcel(accionesList, fechaInicioState.value, fechaFinalState.value)
            // Se avisa la creacion del informe\
            Notification().mostrarNotificacion(
                context,
                "AVANTI - Gestión de Incidencias: Informe",
                "Se guardó el informe en la carpeta de Documentos."
            )
        }
        else
            Log.d("AVISO", "LISTA VACIA, NO PROCEDE")


        generarExcelState.value = false
    }

    if(buscarAccionesByIdState.value)
    {

        actualizarAcciones(tecnico.id, fechaInicioState.value, fechaFinalState.value) { acciones ->
            accionesList.addAll(acciones)
        }

            buscarAccionesByIdState.value = false

    }


    if (fechaInicioState.value.isNotBlank() && fechaFinalState.value.isNotBlank())
    {

        habilitarCargarDatosState.value = true

    }
    else
        habilitarCargarDatosState.value = false


    if (showDatePickerState.value)
    {
        var datePickerInput = ""


        DatePicker(showDatePickerState.value, {
            showDatePickerState.value = false
        }, {
            datePickerInput = it

            if(banderaState.value == 1)
            {
                fechaInicioState.value = datePickerInput
            }
            else if (banderaState.value == 2)
            {
                fechaFinalState.value = datePickerInput
            }

            showDatePickerState.value = false
        })

    }

    if (accionesList.count() > 0)
    {
        habilitarBotonGuardarInformeState.value = true
    }
    else
        habilitarBotonGuardarInformeState.value = false

}

@Composable
fun actualizarAcciones(idTecnico: Int, fechaInicio: String, fechaFinal: String, acciones: (List<Accion>) -> Unit)
{

    // Se convierten las fechas en formato YYYY-MM-DDDD
    val formatoEntrada = DateTimeFormatter.ofPattern("dd-M-yyyy") // Corregido para aceptar mes de un dígito

    val fechaInicio = LocalDate.parse(fechaInicio, formatoEntrada)
    val fechaFin = LocalDate.parse(fechaFinal, formatoEntrada)

    LaunchedEffect(Unit)
    {

        CoroutineScope(Dispatchers.IO).launch {

            AccionRequest().buscarAccionesByIdConRangoFechas(
                idTecnico,
                fechaInicio.toString(),
                fechaFin.toString()
            ) { accion ->

                acciones(accion)

            }

        }
        delay(1000)
    }

}

fun generalInformeExcel(
    accions: List<Accion>,
    fechaInicio: String,
    fechaFin: String
) {

    val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)

    val fileName = "AVANTI_informe${LocalDateTime.now().nano}.xlsx"

    val workbook = XSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")

    // Se crea el encabezado de las columnas
    val headerRow = sheet.createRow(0)
    var headerCell = headerRow.createCell(0).setCellValue("Ticket")
    headerCell = headerRow.createCell(1).setCellValue("Usuario")
    headerCell = headerRow.createCell(2).setCellValue("Sede")
    headerCell = headerRow.createCell(3).setCellValue("Piso")
    headerCell = headerRow.createCell(4).setCellValue("Departamento")
    headerCell = headerRow.createCell(5).setCellValue("Tipo")
    headerCell = headerRow.createCell(6).setCellValue("Descripción")
    headerCell = headerRow.createCell(7).setCellValue("fecha del ticket")
    headerCell = headerRow.createCell(8).setCellValue("hora del ticket")
    headerCell = headerRow.createCell(9).setCellValue("Acción Ejecutada")
    headerCell = headerRow.createCell(10).setCellValue("Fecha Acción")
    headerCell = headerRow.createCell(11).setCellValue("Hora Acción")
    headerCell = headerRow.createCell(12).setCellValue("Estado")
    headerCell = headerRow.createCell(13).setCellValue("Grupo de Atención")
    headerCell = headerRow.createCell(14).setCellValue("Caso atendido por:")
    headerCell = headerRow.createCell(15).setCellValue("Observaciones")

    // Se insertan los datos de cada uno de las acciones
    for (i in 0..accions.count() - 1)
    {

        var row = sheet.createRow(i + 1)
        var rowcell = row.createCell(0).setCellValue(accions[i].ticket.id.toString())
            rowcell = row.createCell(1).setCellValue("${accions[i].ticket.clienteInterno.empleado.primerNombre} ${accions[i].ticket.clienteInterno.empleado.segundoNombre} ${accions[i].ticket.clienteInterno.empleado.primerApellido} ${accions[i].ticket.clienteInterno.empleado.segundoApellido}")
            rowcell = row.createCell(2).setCellValue(accions[i].ticket.clienteInterno.empleado.departamento.sede.nombre)
            rowcell = row.createCell(3).setCellValue(accions[i].ticket.clienteInterno.empleado.departamento.piso.toString())
            rowcell = row.createCell(4).setCellValue(accions[i].ticket.clienteInterno.empleado.departamento.nombre)
            rowcell = row.createCell(5).setCellValue(accions[i].ticket.tipo.tipoTicket)
            rowcell = row.createCell(6).setCellValue(accions[i].ticket.descripcion)
            rowcell = row.createCell(7).setCellValue(accions[i].ticket.fecha)
            rowcell = row.createCell(8).setCellValue(accions[i].ticket.hora)
            rowcell = row.createCell(9).setCellValue(accions[i].descripcionAccion.descripcion)
            rowcell = row.createCell(10).setCellValue(accions[i].fecha)
            rowcell = row.createCell(11).setCellValue(accions[i].hora)
            rowcell = row.createCell(12).setCellValue(accions[i].ticket.estado.tipoEstado)
            rowcell = row.createCell(13).setCellValue(accions[i].ticket.tecnico.grupoAtencion.grupoAtencion)
            rowcell = row.createCell(14).setCellValue("${accions[i].ticket.tecnico.empleado.primerNombre} ${accions[i].ticket.tecnico.empleado.segundoNombre} ${accions[i].ticket.tecnico.empleado.primerApellido} ${accions[i].ticket.tecnico.empleado.segundoApellido}")
            rowcell = row.createCell(15).setCellValue(accions[i].ticket.observaciones)

    }

    // Se crea el archivo
    val outputStream  = FileOutputStream(
        File(path, fileName)
    )
    workbook.write(outputStream)

    // Se cierra el stream para crear el archivo
    outputStream.close()
    workbook.close()

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
