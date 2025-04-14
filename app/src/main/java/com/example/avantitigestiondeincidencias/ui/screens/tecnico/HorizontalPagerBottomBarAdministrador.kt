package com.example.avantitigestiondeincidencias.ui.screens.tecnico

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avantitigestiondeincidencias.AVANTI.Empleado
import com.example.avantitigestiondeincidencias.AVANTI.Tecnico
import com.example.avantitigestiondeincidencias.ui.screens.componentes.MenuLateralContenido
import com.example.avantitigestiondeincidencias.ui.screens.componentes.ScaffoldConMenuLateral
import com.example.avantitigestiondeincidencias.ui.theme.AVANTITIGestionDeIncidenciasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


data class NavItem(val label: String, val icon: ImageVector)

@Composable
fun HorizontalPagerBottomBarAdministrador(administrador: Tecnico, navController: NavController)
{

    val corroutineScope = rememberCoroutineScope()

    val navItemList = listOf(
        NavItem("Inicio", Icons.Default.List),
        NavItem("Busqueda", Icons.Default.Search),
        NavItem("Usuarios", Icons.Default.Person)

    )

    val numPantalla = rememberSaveable {
        mutableStateOf(navItemList.count())
    }

    val state = rememberPagerState(initialPage = 0, pageCount = { numPantalla.value })

    ScaffoldConMenuLateral("", {
        MenuLateralContenido(navController, administrador.empleado, {}, {})
    })
    {
        Scaffold(
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
                        0 -> InicioAdministrador(navController)
                        1 -> BusquedaAdministrador(navController)
                        2 -> BusquedaUsuarios(navController)
                    }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun HorizontalPagerBottomBarPreview() {

    val navController = rememberNavController()

    AVANTITIGestionDeIncidenciasTheme {

        HorizontalPagerBottomBarAdministrador(Tecnico(), navController)

    }
}
