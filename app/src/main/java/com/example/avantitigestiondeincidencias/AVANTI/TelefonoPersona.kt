package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TelefonoPersona(
    @SerialName("id_teléfono_persona")
    val id: Int = 0,
    @SerialName("extensión_teléfono")
    val extension: String = " ",
    @SerialName("código_operadora_teléfono")
    val codigoOperadoraTelefono: CodigoOperadoraTelefono = CodigoOperadoraTelefono()
)
