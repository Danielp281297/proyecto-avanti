package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CargoEmpleado(
    @SerialName("id_cargo_empleado")
    val id: Int = 0,
    @SerialName("tipo_cargo_empleado")
    val tipoCargo: String = " "
)
