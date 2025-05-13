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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.avantitigestiondeincidencias.AVANTI.Accion
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.Notification.Notification
import com.example.avantitigestiondeincidencias.Supabase.AccionRequest
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

val weightColumnas = 1F

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformeTecnico(
    tecnico: Tecnico,
    containerColor: Color = Color.Transparent
)
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

                BotonPersonalizado(onClick = {
                    //Se buscan los datos de los tickets cerrados, en base a las acciones ejecutadas y el id del
                    // tecnico, y se obtienen estos datos
                    buscarAccionesByIdState.value = true
                },
                    enabled = habilitarCargarDatosState.value)
                {
                    Text(text = "CARGAR DATOS", fontFamily = montserratFamily)
                }

                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .horizontalScroll(state = horizontalScrollState, enabled = true)
                        .border(1.dp, Color.Black, RectangleShape))
                    {

                        items(accionesList.count()) { index ->

                            accionFila(accionesList[index])

                        }

                    }

                BotonPersonalizado(
                    onClick = {
                        generarExcelState.value = true
                    },
                    enabled = habilitarBotonGuardarInformeState.value,
                ) {
                    Text(text = "GUARDAR INFORME", fontFamily = montserratFamily)
                }

                Spacer(modifier = Modifier.padding(40.dp))

            }

        }


    }

    if(generarExcelState.value)
    {

        if (accionesList.isNotEmpty()) {
            generalInformeExcel(accionesList, fechaInicioState.value, fechaFinalState.value, ){_,_->}
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
            buscarAccionesByIdState.value = false
        }

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


        com.example.avantitigestiondeincidencias.ui.screens.componentes.DatePicker(
            showDialog = showDatePickerState.value,
            containerColor = containerColor,
            ondismiss = { showDatePickerState.value = false },
            fecha = {
                datePickerInput = it

                if (banderaState.value == 1) {
                    fechaInicioState.value = datePickerInput
                } else if (banderaState.value == 2) {
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
    acciones: List<Accion>,
    fechaInicio: String,
    fechaFin: String,
    adicional: (sheet: XSSFSheet?, indice: Int) -> Unit
) {

    val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)

    val fileName = "AVANTI_informe${fechaInicio} ${fechaFin} ${LocalDateTime.now().nano}.xlsx"

    val workbook = XSSFWorkbook()
    val sheet = workbook.createSheet("Sheet1")

    // Fuente de letra para los encabezados

    val fuenteLetraEncabezado = workbook.createFont()
    fuenteLetraEncabezado.apply {
        color = IndexedColors.WHITE.index
        bold = true
    }

    // Estilo para el encabezado
    var encabezadoCellStyle = workbook.createCellStyle()
    encabezadoCellStyle.apply {
        fillForegroundColor = IndexedColors.DARK_BLUE.index       // Fondo de la casilla
        fillPattern = FillPatternType.SOLID_FOREGROUND

        // Bordes de la casilla delgadas y negras
        borderBottom = BorderStyle.THIN
        bottomBorderColor = IndexedColors.BLACK.index

        borderTop = BorderStyle.THIN
        topBorderColor = IndexedColors.BLACK.index

        borderLeft = BorderStyle.THIN
        leftBorderColor = IndexedColors.BLACK.index

        borderRight = BorderStyle.THIN
        rightBorderColor = IndexedColors.BLACK.index

        setFont(fuenteLetraEncabezado)                      // Fuente de letra
        alignment = HorizontalAlignment.CENTER              // Alineacion horizontal
        verticalAlignment = VerticalAlignment.CENTER        // Alineacion vertical
        wrapText = true                                     // Ajustar texto en la casilla
    }


    // Se crea el encabezado de las columnas
    val camposEncabezado = listOf("Ticket", "Usuario", "Sede", "Piso", "Departamento", "Tipo", "Descripción", "fecha del ticket", "hora del ticket", "Acción Ejecutada", "Fecha Acción", "Hora Acción", "Estado", "Grupo de Atención", "Caso atendido por:", "Observaciones")

    val headerRow = sheet.createRow(0)
    var headerCell = headerRow
    for (i in 0..camposEncabezado.count() - 1)
    {
        headerCell.createCell(i).apply {
            cellStyle = encabezadoCellStyle
            setCellValue(camposEncabezado[i])
        }
    }

    // Se insertan los datos de cada uno de las acciones
    var rowCellStyle = workbook.createCellStyle()
    rowCellStyle.apply {
        alignment = HorizontalAlignment.CENTER              // Alineacion horizontal
        verticalAlignment = VerticalAlignment.CENTER        // Alineacion vertical

        // Bordes de la casilla delgadas y negras
        borderBottom = BorderStyle.THIN
        bottomBorderColor = IndexedColors.BLACK.index

        borderTop = BorderStyle.THIN
        topBorderColor = IndexedColors.BLACK.index

        borderLeft = BorderStyle.THIN
        leftBorderColor = IndexedColors.BLACK.index

        borderRight = BorderStyle.THIN
        rightBorderColor = IndexedColors.BLACK.index

        wrapText = true                                     // Ajustar texto en la casilla
    }

    for (i in 0..acciones.count() - 1)
    {

        var row = sheet.createRow(i + 1)
        var rowcell =
                      row.createCell(0).apply{
                          setCellValue(acciones[i].ticket.id.toString())
                          cellStyle = rowCellStyle
                      }
            rowcell = row.createCell(1).apply{
                setCellValue("${acciones[i].ticket.clienteInterno.empleado.primerNombre} ${acciones[i].ticket.clienteInterno.empleado.segundoNombre} ${acciones[i].ticket.clienteInterno.empleado.primerApellido} ${acciones[i].ticket.clienteInterno.empleado.segundoApellido}")
                cellStyle = rowCellStyle
            }

            rowcell = row.createCell(2).apply {setCellValue(acciones[i].ticket.clienteInterno.empleado.departamento.sede.nombre)
                        cellStyle = rowCellStyle
            }
            rowcell = row.createCell(3).apply {setCellValue(acciones[i].ticket.clienteInterno.empleado.departamento.piso.toString())
                        cellStyle = rowCellStyle
            }
            rowcell = row.createCell(4).apply {setCellValue(acciones[i].ticket.clienteInterno.empleado.departamento.nombre)
                        cellStyle = rowCellStyle
            }
            rowcell = row.createCell(5).apply {setCellValue(acciones[i].ticket.tipo.tipoTicket)
                        cellStyle = rowCellStyle
            }
            rowcell = row.createCell(6).apply {setCellValue(acciones[i].ticket.descripcion)
                        cellStyle = rowCellStyle
            }
            rowcell = row.createCell(7).apply {setCellValue(acciones[i].ticket.fecha)
                        cellStyle = rowCellStyle
            }
            rowcell = row.createCell(8).apply {setCellValue(acciones[i].ticket.hora)
                        cellStyle = rowCellStyle
            }
            rowcell = row.createCell(9).apply {setCellValue(acciones[i].descripcionAccion.descripcion)
                        cellStyle = rowCellStyle
            }
            rowcell = row.createCell(10).apply {setCellValue(acciones[i].fecha)
                        cellStyle = rowCellStyle
            }
            rowcell = row.createCell(11).apply {setCellValue(acciones[i].hora)
                        cellStyle = rowCellStyle
            }
            rowcell = row.createCell(12).apply {setCellValue(acciones[i].ticket.estado.tipoEstado)
                        cellStyle = rowCellStyle
            }
            rowcell = row.createCell(13).apply {setCellValue(acciones[i].ticket.tecnico.grupoAtencion.grupoAtencion)
                        cellStyle = rowCellStyle
            }
            rowcell = row.createCell(14).apply {setCellValue("${acciones[i].ticket.tecnico.empleado.primerNombre} ${acciones[i].ticket.tecnico.empleado.segundoNombre} ${acciones[i].ticket.tecnico.empleado.primerApellido} ${acciones[i].ticket.tecnico.empleado.segundoApellido}")
                        cellStyle = rowCellStyle
            }
            rowcell = row.createCell(15).apply {setCellValue(acciones[i].ticket.observaciones)
                        cellStyle = rowCellStyle
            }

    }

    //Se ajusta de forma automatica el ancho de las columnas
    for (i in 0..camposEncabezado.count() - 1) // El contenido mas el encabezado
    {
        sheet.setColumnWidth(i, 5000)
    }

    //AQUI ES DONDE ES DONDE SE IMPRIME EL GRAFICO DE TORTA
    adicional(sheet, acciones.count())

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
