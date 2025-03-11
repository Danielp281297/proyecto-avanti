package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GrupoAtencion(
    @SerialName("id_grupo_atención")
    val id: Int = 0,
    @SerialName("nombre_grupo_atención")
    val grupoAtencion: String = " "
)
