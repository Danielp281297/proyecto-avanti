package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    @SerialName("id_usuario")
    val id: Int = -1,
    @SerialName("nombre_usuario")
    val nombre: String = " ",
    @SerialName("contraseña_usuario")
    var password: String = " ",
    @SerialName("id_tipo_usuario")
    val idTipoUsuario: Int = 0,
    @SerialName("usuario_habilitado")
    val habilitado: Boolean = true,
    @SerialName("tipo_usuario")
    val tipoUsuario: TipoUsuario = TipoUsuario()
)
