package com.example.avantitigestiondeincidencias.ui.screens.componentes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.ClienteInterno
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.R
import com.example.avantitigestiondeincidencias.modeloButton
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import com.example.avantitigestiondeincidencias.ui.theme.montserratFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldConMenuLateral(
    titulo: String,
    containerColor: Color,
    mostrarLupa: Boolean = false,
    busquedaLupa: () -> Unit = {},
    contenidoMenu: @Composable () -> Unit,
    fondoPantalla: @Composable () -> Unit,
    )
{

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scopeState = rememberCoroutineScope()


    // Se maneja cuando el usuario pulsa el boton atras cuando el menu lateral esta abierto
    BackHandler(drawerState.isOpen) {
        if(drawerState.isOpen)
        {
            scopeState.launch { drawerState.close() }
            drawerState.isClosed
        }
    }

    //UI
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // El contenido del menu lateral
            ModalDrawerSheet(modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth(), drawerContainerColor = containerColor)
            {
                contenidoMenu()
            }
        }
    )
    {

        Scaffold(
            containerColor = containerColor,
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
                    title = {
                        Text(
                            titulo,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp),
                            fontWeight = FontWeight.Bold, fontFamily = montserratFamily
                        )
                    },
                    actions = {
                        if (mostrarLupa) {
                                IconButton(onClick = busquedaLupa)
                                {
                                    Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar tickets")
                                }
                            }
                    },
                    navigationIcon = {
                        IconButton(modifier = Modifier, onClick = { scopeState.launch { drawerState.open() } }
                        ) {
                            androidx.compose.material3.Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                        }
                    }
                )
            }
        ) {

            fondoPantalla()

        }
    }

}

@Preview(showBackground = true)
@Composable
fun ScaffoldConMenuLateralPreview() {

    val navController = rememberNavController()
    AVANTITIGestionDeIncidenciasTheme {

        //InicioCliente(Empleado(), navController)\

    }
}

