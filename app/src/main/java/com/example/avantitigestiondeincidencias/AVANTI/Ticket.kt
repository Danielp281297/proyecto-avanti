package com.example.avantitigestiondeincidencias.AVANTI

import java.sql.Time
import java.sql.Date

data class Ticket(
    val id: Int = 0,
    val fecha: String = "",
    val hora: String = "",
    val descripcion: String = "",
    val observaciones: String = "",
    val tipo: String = "",
    val prioridad: String = "",
    val estado: String = "",

    val cedulaEmpleado: Int = 0,
    val nombreEmpleado: String = "",
    val apellidoEmpleado: String = "",
    val departamento: String = "",
    val sede: String = "",
    val piso: Int = 0,

    val cedulaTecnico: Int = 0,
    val nombreTecnico: String = "",
    val apellidoTecnico: String = ""
)