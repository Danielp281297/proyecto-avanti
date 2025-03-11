package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PrioridadTicket(
    @SerialName("id_prioridad_ticket")
    val id: Int = 0,
    @SerialName("nivel_prioridad_ticket")
    val nivel: String = " "
)
