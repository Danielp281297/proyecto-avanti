package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    @SerialName("id_ticket")
    val id: Int = 0,
    @SerialName("fecha_ticket")
    var fecha: String = "",
    @SerialName("hora_ticket")
    var hora: String = "",

    @SerialName("fecha_asignación_ticket")
    var fechaAsignacion: String = "",
    @SerialName("hora_asignación_ticket")
    var horaAsignacion: String = "",

    @SerialName("descripción_ticket")
    val descripcion: String = "",
    @SerialName("observaciones_ticket")
    val observaciones: String = " ",
    @SerialName("calificación_gestión_ticket")
    val calificacionGestionTicket: Int = 0,

    @SerialName("id_tipo_ticket")
    val idTipoTicket: Int = 0,
    @SerialName("id_prioridad_ticket")
    val idPrioridadTicket: Int = 0,
    @SerialName("id_estado_ticket")
    val idEstadoTicket: Int = 0,
    @SerialName("id_cliente_interno")
    var idClienteInterno: Int = 0,
    @SerialName("id_técnico")
    var idTecnico: Int = 0,




    @SerialName("tipo_ticket")
    val tipo: TipoTicket = TipoTicket(),
    @SerialName("prioridad_ticket")
    val prioridad: PrioridadTicket = PrioridadTicket(),
    @SerialName("estado_ticket")
    val estado: EstadoTicket = EstadoTicket(),
    @SerialName("cliente_interno")
    val clienteInterno: ClienteInterno = ClienteInterno(),
    @SerialName("técnico")
    val tecnico: Tecnico = Tecnico()

)