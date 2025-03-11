package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tecnico(
    @SerialName("id_técnico")
    var id: Int = 0, // Cada vez que se crea un ticket, este se va al administrador
    @SerialName("id_grupo_atención")
    val idGrupoAtencion: Int = 0,
    @SerialName("id_empleado")
    val idEmpleado: Int = 0,
    @SerialName("grupo_atención")
    val grupoAtencion: GrupoAtencion = GrupoAtencion(),
    @SerialName("empleado")
    val empleado: Empleado = Empleado()
)
