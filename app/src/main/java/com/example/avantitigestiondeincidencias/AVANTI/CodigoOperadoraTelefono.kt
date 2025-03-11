package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CodigoOperadoraTelefono(
    @SerialName("id_código_operadora_teléfono")
    val id: Int = 0,
    @SerialName("operadora_teléfono")
    val operadora: String = " "
)
