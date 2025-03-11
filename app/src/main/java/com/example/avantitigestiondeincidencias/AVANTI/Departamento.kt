package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Departamento(
    @SerialName("id_departamento")
    val id: Int = 0,
    @SerialName("nombre_departamento")
    val nombre: String = " ",
    @SerialName("piso_departamento")
    val piso: Int = 0,
    @SerialName("sede")
    val sede: Sede = Sede()
)
