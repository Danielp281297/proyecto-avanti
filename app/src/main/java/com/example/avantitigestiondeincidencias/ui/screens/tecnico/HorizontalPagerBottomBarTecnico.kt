package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.Network.Network
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldConMenuLateral
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalPagerBottomBarTecnico(
    navController: NavController,
    context: Context,
    tecnico: Tecnico,
    containerColor: Color = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919))
{

    val corroutineScope = rememberCoroutineScope()

    val navItemList = listOf(
        NavItem("Inicio", Icons.Default.Home),
        NavItem("Buscar\nTicket", Icons.Default.Search),
        NavItem("Informe", Icons.Default.Menu)
    )

    val numPantalla = rememberSaveable {
        mutableStateOf(navItemList.count())
    }

    val state = rememberPagerState(initialPage = 0
        , pageCount = { numPantalla.value })

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scopeState = rememberCoroutineScope()

    Network.networkCallback(navController, context)

    // Se maneja cuando el usuario pulsa el boton atras cuando el menu lateral esta abierto
    ScaffoldConMenuLateral(
        titulo = "",
        containerColor = containerColor,
        contenidoMenu = {
        MenuLateralContenido(navController, context, tecnico.empleado, perfil = {

            // Se covierte el objeto en json para enviarlo a la pantalla
            val json = Json { ignoreUnknownKeys = true }.encodeToString(tecnico)

            navController.navigate("informacionPerfilTecnico" + "/${json}")

        }, manualUsuarioEvento =  {})
    })
    {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
                    title = {
                        Text(
                            "",
                            modifier = Modifier.fillMaxWidth().padding(0.dp),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(modifier = Modifier, onClick = { scopeState.launch { drawerState.open() } }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                        }
                    }
                )
            },
            bottomBar = {

                NavigationBar(containerColor = containerColor) {

                    navItemList.forEachIndexed { index, item ->

                        NavigationBarItem(
                            selected = true,
                            onClick = {

                                corroutineScope.launch {

                                    state.animateScrollToPage(index)

                                }

                            },
                            colors = NavigationBarItemColors(
                                selectedIconColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black,
                                selectedTextColor = if (isSystemInDarkTheme()) Color.LightGray else Color.Black,
                                selectedIndicatorColor = Color.Transparent,
                                unselectedIconColor = Color.White,
                                unselectedTextColor = Color.White,
                                disabledIconColor = Color.White,
                                disabledTextColor = Color.White
                            ),
                            icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                            label = { Text(text = item.label, textAlign = TextAlign.Center) }
                        )

                    }

                }
            },

            //Color de fondo
            containerColor = containerColor
        )
        {
            HorizontalPager(state = state, modifier = Modifier.background(Color.Blue))
            { page ->

                when (page) {
                    0 -> InicioTecnico(tecnico, navController, containerColor)
                    1 -> BusquedaTicket(navController, context, tecnico, { json ->
                        navController.navigate("ticketDesplegadoBusqueda" + "/${json}")
                    }, containerColor)
                    2 -> InformeTecnico(tecnico, containerColor)
                    else -> {}
                }

            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun HorizontalPagerBottomBarTecnicoPreview() {

    val navController = rememberNavController()

    AVANTITIGestionDeIncidenciasTheme {


    }
}
