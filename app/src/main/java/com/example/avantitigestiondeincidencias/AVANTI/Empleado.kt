package com.example.avantitigestiondeincidencias.AVANTI

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Empleado(
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
    @SerialName("id_usuario")
    val idUsuario: Int = 0,

    @SerialName("teléfono_empleado")
    val telefonoPersona: TelefonoEmpleado = TelefonoEmpleado(),
    @SerialName("departamento")
    val departamento: Departamento = Departamento(),
    @SerialName("cargo_empleado")
    val cargoEmpleado: CargoEmpleado = CargoEmpleado(),
    @SerialName("usuario")
    var usuario: Usuario = Usuario()
)
