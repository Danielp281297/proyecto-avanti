package com.example.avantitigestiondeincidencias.FormatoHoraFecha

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Conversor {

    companion object{

        data class Fecha(val fecha: String, val hora: String)
        data class RangoFecha(val fechaInicio: Fecha, val fechaFin: Fecha)

        fun duracionFechasMilisegundos(rangoFecha: RangoFecha): Duration? {

            val formato = "dd-MM-yyyy HH:mm:ss"
            val formateador = DateTimeFormatter.ofPattern(formato)

            val dateTime1 = LocalDateTime.parse("${rangoFecha.fechaInicio.fecha} ${rangoFecha.fechaInicio.hora}", formateador)
            val dateTime2 = LocalDateTime.parse("${rangoFecha.fechaFin.fecha} ${rangoFecha.fechaFin.hora}", formateador)

            val duracion = if (dateTime2.isAfter(dateTime1)) {
                Duration.between(dateTime1, dateTime2)
            } else {
                Duration.between(dateTime2, dateTime1)
            }

            return duracion
        }

    }

}