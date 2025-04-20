package com.example.avantitigestiondeincidencias.FormatoHoraFecha

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class FormatoHoraFecha() {

    companion object{

        fun formatoHora(hora: String): String {

            val localTime = LocalTime.parse(hora.takeWhile { it != '.' && it != ',' }, DateTimeFormatter.ISO_LOCAL_TIME)
            return localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))!!
        }

        fun formatoFecha(fecha: String): String? {
            val formatoEntrada = DateTimeFormatter.ofPattern("yyyy-M-dd")
            val formatoSalida = DateTimeFormatter.ofPattern("dd-M-yyyy")
            val fechaFormateada = LocalDate.parse(fecha, formatoEntrada).format(formatoSalida)
            return fechaFormateada
        }

    }

}