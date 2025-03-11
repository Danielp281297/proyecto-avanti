package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sede(
    @SerialName("id_name")
    val id: Int = 0,
    @SerialName("nombre_sede")
    val nombre: String = " "
)
