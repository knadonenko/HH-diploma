package ru.practicum.android.diploma.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.practicum.android.diploma.ui.screen.FavouritesScreen
import ru.practicum.android.diploma.ui.screen.FilterSettingsScreen
import ru.practicum.android.diploma.ui.screen.MainScreen
import ru.practicum.android.diploma.ui.screen.TeamScreen

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Routes.MAIN
    ) {
        composable(Routes.MAIN) {
            MainScreen(
                onFilterClick = {
                    navController.navigate(Routes.FILTER_SETTINGS)
                }
            )
        }

        composable(Routes.FAVOURITES) {
            FavouritesScreen()
        }

        composable(Routes.TEAM) {
            TeamScreen()
        }

        composable(Routes.FILTER_SETTINGS) {
            FilterSettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
