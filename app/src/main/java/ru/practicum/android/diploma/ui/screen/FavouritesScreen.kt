package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesInfo
import ru.practicum.android.diploma.presentation.vacancies.models.FavoritesScreenState
import ru.practicum.android.diploma.presentation.vacancies.viewmodel.FavoritesViewModel
import ru.practicum.android.diploma.ui.components.Placeholder
import ru.practicum.android.diploma.ui.components.VacancyItem
import ru.practicum.android.diploma.ui.components.topbars.CommonTopBar
import ru.practicum.android.diploma.ui.theme.paddingBase

@Composable
fun FavouritesScreen(
    modifier: Modifier,
    viewModel: FavoritesViewModel = koinViewModel<FavoritesViewModel>()
) {
    LaunchedEffect(Unit) { viewModel.getFavorites() }

    Scaffold(
        modifier = modifier,
        topBar = {
            CommonTopBar(stringResource(R.string.top_bar_label_favourites))
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .padding(horizontal = paddingBase),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainContent(viewModel)
        }
    }
}

@Composable
fun MainContent(viewModel: FavoritesViewModel) {
    when (val state = viewModel.screenState.collectAsState().value) {
        is FavoritesScreenState.Default -> Placeholder(
            R.drawable.empty_placeholder,
            stringResource(R.string.empty_favorites)
        )

        is FavoritesScreenState.Error -> Placeholder(
            R.drawable.no_vacancy_placeholder,
            stringResource(R.string.bad_request)
        )

        is FavoritesScreenState.Content -> FavoritesList(state.data, onItemClick = {})

    }
}

@Composable
fun FavoritesList(
    vacancyList: List<VacanciesInfo>,
    onItemClick: (VacanciesInfo) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        items(vacancyList) { vacancy ->
            VacancyItem(
                vacancy = vacancy,
                onClick = { onItemClick }
            )
        }
    }
}
