package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TipoTicket(
    @SerialName("id_tipo_ticket")
    var id: Int = 0,
    @SerialName("nombre_tipo_ticket")
    val tipoTicket: String = " "
)
