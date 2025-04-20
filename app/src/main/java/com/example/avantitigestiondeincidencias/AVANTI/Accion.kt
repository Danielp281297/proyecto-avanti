package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Accion(
    @SerialName("id_acción")
    val id: Int = 0,
    @SerialName("fecha_acción")
    var fecha: String = " ",
    @SerialName("hora_acción")
    var hora: String = " ",
    @SerialName("id_ticket")
    val idTicket: Int = 0,
    @SerialName("id_descripción_acción")
    val idDescripcionAccion: Int = 0,

    @SerialName("descripción_acción")
    val descripcionAccion: DescripcionAccion = DescripcionAccion(),
    @SerialName("ticket")
    val ticket: Ticket = Ticket()
)
