package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EstadoTicket(
    @SerialName("id_estado_ticket")
    val id: Int = 0,
    @SerialName("tipo_estado_ticket")
    val tipoEstado: String = " "
)
