package com.example.avantitigestiondeincidencias.ui.screens.manuales

import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun ManualClienteInterno()
{

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 20 })
    val scope = rememberCoroutineScope()

    VerticalPager(state = pagerState) {page ->

        when(page)
        {
            0 -> Presentacion()
            // Indice
            // Introduccion
            // Descripcion de las pantallas
                // Pantalla Login
                // Pantalla Inicio del cliente
                // Pantalla de nuevo Ticket
                // Pantalla de perfil
                // Pantalla de editar perfil
                // Pantalla de cambiar contrasena
            // Operativa de creacion de ticket
            // Operativa de editar perfil
                // Operativa de cambiar contrasena
            // Botones
                // Botones de inicio de sesion
            else -> {}
        }

    }
}