package ru.practicum.android.diploma.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesInfo
import ru.practicum.android.diploma.presentation.vacancies.models.VacanciesScreenState
import ru.practicum.android.diploma.presentation.vacancies.viewmodel.VacanciesViewModel
import ru.practicum.android.diploma.ui.components.LoadingComponent
import ru.practicum.android.diploma.ui.components.SearchField
import ru.practicum.android.diploma.ui.components.topbars.MainTopBar
import ru.practicum.android.diploma.ui.theme.LocalTypography
import ru.practicum.android.diploma.ui.theme.blue
import ru.practicum.android.diploma.ui.theme.chipCornerRadius
import ru.practicum.android.diploma.ui.theme.chipHeight
import ru.practicum.android.diploma.ui.theme.padding12
import ru.practicum.android.diploma.ui.theme.padding4
import ru.practicum.android.diploma.ui.theme.padding64
import ru.practicum.android.diploma.ui.theme.paddingBase
import ru.practicum.android.diploma.ui.theme.placeholderHeight
import ru.practicum.android.diploma.ui.theme.placeholderWidth
import ru.practicum.android.diploma.ui.theme.white

@Composable
fun MainScreen(
    modifier: Modifier, onFilterClick: () -> Unit,
    viewModel: VacanciesViewModel = koinViewModel<VacanciesViewModel>()
) {
    var query = viewModel.currentSearchText.collectAsStateWithLifecycle().value
    Scaffold(
        modifier = Modifier,
        topBar = {
            MainTopBar(onFilterClick)
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .padding(horizontal = paddingBase)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchField(
                searchQuery = query,
                onQueryChange = {
                    viewModel.onSearchTextChange(it)
                },
                placeHolder = stringResource(R.string.enter_request),
                onSearchClear = { viewModel.onClearSearchText() }
            )
            MainContent(viewModel)
        }
    }
}

@Composable
fun MainContent(viewModel: VacanciesViewModel) {
    var state = viewModel.screenState.collectAsState().value

    when (state) {
        is VacanciesScreenState.Default -> Placeholder(R.drawable.main_placeholder)
        is VacanciesScreenState.Loading -> LoadingComponent()

        is VacanciesScreenState.NoInternetConnection -> Placeholder(
            R.drawable.error_placeholder,
            stringResource(R.string.no_internet)
        )

        is VacanciesScreenState.NotFound -> {
            Chip(stringResource(R.string.no_vacancies))
            Placeholder(
                R.drawable.no_vacancy_placeholder,
                stringResource(R.string.bad_request)
            )
        }

        is VacanciesScreenState.Found -> {
            Chip(pluralStringResource(R.plurals.vacancy_plurals, 100, 100))
            VacanciesList(state.data)
        }

        is VacanciesScreenState.InternalServerError -> {}
    }

}

@Composable
fun Chip(text: String) {
    Spacer(modifier = Modifier.height(padding12))
    Box(
        modifier = Modifier
            .height(chipHeight)
            .background(blue, RoundedCornerShape(chipCornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(vertical = padding4, horizontal = padding12),
            text = text,
            color = white,
            style = LocalTypography.current.body16Regular
        )
    }
}

@Composable
fun Placeholder(@DrawableRes imageResId: Int, text: String? = null) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier.size(width = placeholderWidth, height = placeholderHeight),
                painter = painterResource(imageResId),
                contentDescription = text
            )
            if (!text.isNullOrEmpty()) {
                Text(
                    modifier = Modifier.padding(horizontal = padding64, vertical = paddingBase),
                    textAlign = TextAlign.Center,
                    text = text,
                    style = LocalTypography.current.body22Medium
                )
            }
        }
    }
}

@Composable
fun VacanciesList(vacancyList: List<VacanciesInfo>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {

    }
}


