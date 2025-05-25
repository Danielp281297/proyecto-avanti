package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DescripcionAccion(
    @SerialName("id_descripción_acción")
    val id: Int = 0,
    @SerialName("descripción_acción_ejecutada")
    var descripcion: String = " "
)
