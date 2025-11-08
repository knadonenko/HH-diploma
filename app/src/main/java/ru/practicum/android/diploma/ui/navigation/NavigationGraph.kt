package ru.practicum.android.diploma.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.presentation.vacancies.models.VacancySource
import ru.practicum.android.diploma.presentation.vacancydetails.viewmodel.VacancyDetailsViewModel
import ru.practicum.android.diploma.presentation.workplaces.viewmodel.WorkPlacesViewModel
import ru.practicum.android.diploma.ui.screen.FavouritesScreen
import ru.practicum.android.diploma.ui.screen.FilterAreaScreen
import ru.practicum.android.diploma.ui.screen.FilterCountryScreen
import ru.practicum.android.diploma.ui.screen.FilterIndustryScreen
import ru.practicum.android.diploma.ui.screen.FilterSettingsScreen
import ru.practicum.android.diploma.ui.screen.FilterWorkPlaceScreen
import ru.practicum.android.diploma.ui.screen.MainScreen
import ru.practicum.android.diploma.ui.screen.TeamScreen
import ru.practicum.android.diploma.ui.screen.VacancyScreen

@Composable
fun NavigationGraph(
    modifier: Modifier,
    navController: NavHostController,
    sharedWorkPlacesViewModel: WorkPlacesViewModel = koinViewModel<WorkPlacesViewModel>()
) {
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = Routes.MAIN
    ) {
        composable(Routes.MAIN) {
            MainScreen(
                modifier,
                onFilterClick = {
                    navController.navigate(Routes.FILTER_SETTINGS)
                },
                onDetailsClick = { vacancyId ->
                    navController.navigate("${Routes.VACANCY}/${VacancySource.SEARCH}/$vacancyId")
                }
            )
        }

        composable(Routes.FAVOURITES) {
            FavouritesScreen(
                modifier,
                onDetailsClick = { vacancyId ->
                    navController.navigate("${Routes.VACANCY}/${VacancySource.FAVOURITE}/$vacancyId")
                }
            )
        }

        composable(
            "${Routes.VACANCY}/{$VACANCY_SOURCE}/{$VACANCY_ID}",
            arguments = listOf(
                navArgument(VACANCY_ID) {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument(VACANCY_SOURCE) {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val vacancyId = backStackEntry.arguments?.getString(VACANCY_ID)!!
            val vacancySource = backStackEntry.arguments?.getString(VACANCY_SOURCE)!!

            VacancyScreen(
                modifier,
                onBackClick = {
                    navController.popBackStack()
                },
                viewModel = koinViewModel<VacancyDetailsViewModel>(parameters = {
                    parametersOf(vacancyId, vacancySource)
                })
            )
        }

        composable(Routes.TEAM) {
            TeamScreen(modifier)
        }

        composable(Routes.FILTER_SETTINGS) {
            FilterSettingsScreen(
                modifier,
                onBackClick = {
                    navController.popBackStack()
                },
                toFilterWorkPlace = {
                    navController.navigate(Routes.FILTER_WORK_PLACE)
                },
                toFilterIndustry = {
                    navController.navigate(Routes.FILTER_INDUSTRY)
                }
            )
        }

        composable(Routes.FILTER_WORK_PLACE) {
            FilterWorkPlaceScreen(
                modifier,
                onBackClick = {
                    navController.popBackStack()
                },
                onSubmit = { area ->
                    navController.popBackStack()
                },
                toFilterCountry = {
                    navController.navigate(Routes.FILTER_COUNTRY)
                },
                toFilterRegion = {
                    navController.navigate(Routes.FILTER_AREA)
                },
                viewModel = sharedWorkPlacesViewModel
            )
        }

        composable(Routes.FILTER_COUNTRY) {
            FilterCountryScreen(
                modifier,
                onBackClick = {
                    navController.popBackStack()
                },
                viewModel = sharedWorkPlacesViewModel
            )
        }

        composable(Routes.FILTER_AREA) {
            FilterAreaScreen(
                modifier,
                onBackClick = {
                    navController.popBackStack()
                },
                viewModel = sharedWorkPlacesViewModel
            )
        }

        composable(Routes.FILTER_INDUSTRY) {
            FilterIndustryScreen(
                modifier,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

private const val COUNTRY_ID = "countryId"
private const val VACANCY_ID = "vacancyId"
private const val VACANCY_SOURCE = "vacancySource"
