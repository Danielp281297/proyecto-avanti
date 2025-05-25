package com.example.avantitigestiondeincidencias.POI

import android.os.Environment
import com.example.avantitigestiondeincidencias.AVANTI.Accion
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.xddf.usermodel.chart.ChartTypes
import org.apache.poi.xddf.usermodel.chart.LegendPosition
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory
import org.apache.poi.xssf.usermodel.XSSFDrawing
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime

class POI {

    companion object{

        fun generarPieChart(
            sheet: XSSFSheet?,
            filaComienzo: Int,
            columnaComienzo: Int,
            filaFin: Int,
            columnaFin: Int,
            tituloPieChart: String,
            categorias: Array<String>,
            valores: Array<Int>,

            ){
            val drawing: XSSFDrawing = sheet!!.createDrawingPatriarch()
            val anchor = drawing.
            createAnchor(0,
                0,
                0,
                0,
                columnaComienzo,  // Columna donde comienza el grafico
                filaComienzo, // Fila donde comienza el grafico
                columnaFin,   // Cantidad de columnas que conforma el grafico
                filaFin  // Cantidad de filas que conforma el grafico
            )

            val chart = drawing.createChart(anchor)
            chart.setTitleText(tituloPieChart)
            chart.titleOverlay = false

            val legend = chart.orAddLegend
            legend.position = LegendPosition.TOP_RIGHT


            val categorias = XDDFDataSourcesFactory.
            fromArray(categorias)

            val valores = XDDFDataSourcesFactory.
            fromArray(valores)

            val data = chart.createData(ChartTypes.PIE, null, null)
            data.setVaryColors(true)

            val serie = data.addSeries(categorias, valores)
            serie.setTitle(null, null)

            chart.plot(data)
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

    }

}