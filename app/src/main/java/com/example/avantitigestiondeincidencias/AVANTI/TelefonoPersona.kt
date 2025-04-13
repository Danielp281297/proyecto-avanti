package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TelefonoEmpleado(
    @SerialName("id_teléfono_empleado")
    val id: Int = 0,
    @SerialName("id_código_operadora_teléfono")
    val idCodigoExtensionTelefono: Int = 0,
    @SerialName("extensión_teléfono")
    val extension: String = " ",


    @SerialName("código_operadora_teléfono")
    val codigoOperadoraTelefono: CodigoOperadoraTelefono = CodigoOperadoraTelefono()
)
