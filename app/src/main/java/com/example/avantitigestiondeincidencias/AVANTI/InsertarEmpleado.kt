package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InsertarEmpleado(
    @SerialName("id_empleado")
    var id: Int = 0,
    @SerialName("cédula_empleado")
    var cedula: Int = 0,
    @SerialName("primer_nombre_empleado")
    var primerNombre: String = " ",
    @SerialName("segundo_nombre_empleado")
    var segundoNombre: String = " ",
    @SerialName("primer_apellido_empleado")
    val primerApellido: String = " ",
    @SerialName("segundo_apellido_empleado")
    val segundoApellido: String = " ",
    @SerialName("correo_electrónico_empleado")
    val correoElectronico: String = " ",
    @SerialName("id_teléfono_empleado")
    var idTeléfonoEmpleado: Int = 0,
    @SerialName("id_departamento")
    val idDepartamento: Int = 0,
    @SerialName("id_cargo_empleado")
    val idCargoEmpleado: Int = 0,
    @SerialName("id_usuario")
    var idUsuario: Int = 0,
)
