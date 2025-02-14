package com.example.avantitigestiondeincidencias.AVANTI

import java.sql.Time
import java.sql.Date

data class Ticket(
    val id: Int,
    val fecha: String,
    val hora: String,
    val descripcion: String,
    val observaciones: String,
    val tipo: String,
    val prioridad: String,
    val estado: String,

    val cedulaEmpleado: Int,
    val nombreEmpleado: String,
    val apellidoEmpleado: String,
    val departamento: String,
    val sede: String,
    val piso: Int,

    val cedulaTecnico: Int,
    val nombreTecnico: String,
    val apellidoTecnico: String
)