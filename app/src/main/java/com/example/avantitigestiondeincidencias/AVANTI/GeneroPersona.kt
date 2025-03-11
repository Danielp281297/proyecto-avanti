package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeneroPersona(
    @SerialName("id_genero_persona")
    val id: Int = 0,
    @SerialName("tipo_genero_persona")
    val tipo: String = " "
)
