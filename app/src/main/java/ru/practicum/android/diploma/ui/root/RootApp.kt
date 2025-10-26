package ru.practicum.android.diploma.ui.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.practicum.android.diploma.ui.navigation.BottomNavigationBar
import ru.practicum.android.diploma.ui.navigation.NavigationGraph
import ru.practicum.android.diploma.ui.navigation.Routes

@Composable
fun RootApp(modifier: Modifier) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    val isBottomBarVisible = currentDestination?.route in setOf(
        Routes.MAIN,
        Routes.FAVOURITES,
        Routes.TEAM
    )

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (isBottomBarVisible) {
                BottomNavigationBar(
                    modifier = Modifier,
                    navController = navController,
                    currentDestination = currentDestination
                )
            }
        }
    ) { padding ->
        NavigationGraph(
            navController = navController,
            modifier = modifier
                .fillMaxSize()
                .padding()
        )
    }
}
