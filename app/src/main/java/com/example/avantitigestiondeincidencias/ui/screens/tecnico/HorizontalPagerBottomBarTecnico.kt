package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.Supabase.EmpleadoRequest
import com.example.avantitigestiondeincidencias.Supabase.TecnicoRequest
import com.example.avantitigestiondeincidencias.ui.screens.componentes.MenuLateralContenido
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldConMenuLateral
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalPagerBottomBarTecnico(tecnico: Tecnico, navController: NavController)
{


    val corroutineScope = rememberCoroutineScope()

    val navItemList = listOf(
        NavItem("Inicio", Icons.Default.Home),
        NavItem("Busqueda", Icons.Default.Search),
        NavItem("Informe", Icons.Default.Menu)
    )

    val numPantalla = rememberSaveable {
        mutableStateOf(navItemList.count())
    }

    val state = rememberPagerState(initialPage = 0, pageCount = { numPantalla.value })

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

    ScaffoldConMenuLateral("", {})
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

                NavigationBar(containerColor = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919)) {

                    navItemList.forEachIndexed { index, item ->

                        NavigationBarItem(
                            selected = true,
                            onClick = {

                                corroutineScope.launch {

                                    state.animateScrollToPage(index)

                                }

                            },
                            colors = NavigationBarItemColors(
                                selectedIconColor = Color.Black,
                                selectedTextColor = Color.Black,
                                selectedIndicatorColor = Color.Transparent,
                                unselectedIconColor = Color.White,
                                unselectedTextColor = Color.White,
                                disabledIconColor = Color.White,
                                disabledTextColor = Color.White
                            ),
                            icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                            label = { Text(text = item.label) }
                        )

                    }

                }
            },

            //Color de fondo
            containerColor = if (!isSystemInDarkTheme()) Color.White else Color(0xFF191919)
        )
        {

            HorizontalPager(state = state, modifier = Modifier.background(Color.Blue))
            { page ->

                when (page) {
                    0 -> InicioTecnico(tecnico.empleado, navController)
                    1 -> BusquedaTecnico(navController)
                    2 -> InformeTecnico(tecnico)
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
