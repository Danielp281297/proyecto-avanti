package com.example.avantitigestiondeincidencias.ui.screens.componentes

import java.security.MessageDigest

fun SHA512(input: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-512")
    val hashedBytes = messageDigest.digest(input.toByteArray())

    // Convertir los bytes a una representación hexadecimal
    val hexString = StringBuilder()
    for (byte in hashedBytes) {
        val hex = Integer.toHexString(0xff and byte.toInt())
        if (hex.length == 1) hexString.append('0')
        hexString.append(hex)
    }

    return hexString.toString()
}