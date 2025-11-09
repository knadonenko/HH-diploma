package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesInfo
import ru.practicum.android.diploma.presentation.vacancies.models.VacanciesScreenState
import ru.practicum.android.diploma.presentation.vacancies.viewmodel.VacanciesViewModel
import ru.practicum.android.diploma.ui.components.Chip
import ru.practicum.android.diploma.ui.components.LoadingComponent
import ru.practicum.android.diploma.ui.components.Placeholder
import ru.practicum.android.diploma.ui.components.SearchField
import ru.practicum.android.diploma.ui.components.VacancyItem
import ru.practicum.android.diploma.ui.components.VacancyLoadingItem
import ru.practicum.android.diploma.ui.components.topbars.MainTopBar
import ru.practicum.android.diploma.ui.navigation.FILTER_APPLY
import ru.practicum.android.diploma.ui.theme.floatingChipContentPadding
import ru.practicum.android.diploma.ui.theme.floatingChipPadding
import ru.practicum.android.diploma.ui.theme.loaderItemPadding
import ru.practicum.android.diploma.ui.theme.padding12
import ru.practicum.android.diploma.ui.theme.paddingBase

@Composable
fun MainScreen(
    navController: NavController,
    modifier: Modifier,
    onFilterClick: () -> Unit,
    onDetailsClick: (String) -> Unit,
    viewModel: VacanciesViewModel = koinViewModel<VacanciesViewModel>()
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    LaunchedEffect(backStackEntry.value) {
        viewModel.loadFilterSettings()

        backStackEntry.value?.savedStateHandle?.get<Boolean>(FILTER_APPLY)?.let { isApply ->
            if (isApply) {
                viewModel.searchWithNewSettings()
                backStackEntry.value?.savedStateHandle?.remove<Boolean>(FILTER_APPLY)
            }
        }
    }

    val query = viewModel.currentSearchText.collectAsStateWithLifecycle().value

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
            MainContent(viewModel, onDetailsClick)
        }
    }
}

@Composable
fun MainContent(viewModel: VacanciesViewModel, onDetailsClick: (String) -> Unit) {
    val state = viewModel.screenState.collectAsState().value

    when (state) {
        is VacanciesScreenState.Default -> Placeholder(R.drawable.main_placeholder)
        is VacanciesScreenState.Loading -> LoadingComponent()

        is VacanciesScreenState.NoInternetConnection -> Placeholder(
            R.drawable.error_placeholder,
            stringResource(R.string.no_internet)
        )

        is VacanciesScreenState.NotFound -> {
            Spacer(modifier = Modifier.padding(top = padding12))
            Chip(stringResource(R.string.no_vacancies))
            Placeholder(
                R.drawable.no_vacancy_placeholder,
                stringResource(R.string.bad_request)
            )
        }

        is VacanciesScreenState.Found -> MainScreenContent(viewModel, state, onDetailsClick)

        is VacanciesScreenState.InternalServerError -> {}
    }
}

@Composable
fun MainScreenContent(
    viewModel: VacanciesViewModel,
    state: VacanciesScreenState.Found,
    onDetailsClick: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        VacanciesList(
            state.data,
            onItemClick = onDetailsClick,
            onLoadNextPage = { viewModel.loadNextPage() },
            isNextPageLoading = state.isNextPageLoading
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            contentAlignment = Alignment.Center
        ) {
            Spacer(modifier = Modifier.padding(top = floatingChipPadding))
            Chip(
                text = pluralStringResource(
                    R.plurals.vacancy_plurals,
                    state.totalCount,
                    state.totalCount
                )
            )
        }
    }
}

@Composable
fun VacanciesList(
    vacancyList: List<VacanciesInfo>,
    onItemClick: (String) -> Unit,
    onLoadNextPage: () -> Unit,
    isNextPageLoading: Boolean
) {
    val listState = rememberLazyListState()
    val shouldLoadNext = remember {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val totalItemsCount = listState.layoutInfo.totalItemsCount

            lastVisibleItemIndex != null && lastVisibleItemIndex >= totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadNext.value) {
        if (shouldLoadNext.value) {
            onLoadNextPage()
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        state = listState,
        contentPadding = PaddingValues(bottom = if (isNextPageLoading) loaderItemPadding else 0.dp)
    ) {
        item {
            Spacer(modifier = Modifier.padding(top = floatingChipContentPadding))
        }
        items(vacancyList) { vacancy ->
            VacancyItem(
                vacancy = vacancy,
                onClick = onItemClick
            )
        }
        if (isNextPageLoading) {
            item {
                VacancyLoadingItem()
            }
        }
    }
}
