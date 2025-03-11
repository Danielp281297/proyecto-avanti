package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClienteInterno(
    @SerialName("id_cliente_interno")
    var id: Int = 0,
    @SerialName("empleado")
    val empleado: Empleado = Empleado()
)
