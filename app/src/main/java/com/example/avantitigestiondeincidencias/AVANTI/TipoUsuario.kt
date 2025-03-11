package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TipoUsuario(
    @SerialName("id_tipo_usuario")
    val id: Int = 0,
    @SerialName("tipo_usuario")
    val tipoUsuario: String = " "
)
