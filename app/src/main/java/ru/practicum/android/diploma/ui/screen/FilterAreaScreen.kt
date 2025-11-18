package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filters.models.WorkPlacesScreenState
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterWorkPlaceViewModel
import ru.practicum.android.diploma.ui.components.LoadingComponent
import ru.practicum.android.diploma.ui.components.Placeholder
import ru.practicum.android.diploma.ui.components.SearchField
import ru.practicum.android.diploma.ui.components.topbars.FilterTopBar

@Composable
fun FilterAreaScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    viewModel: FilterWorkPlaceViewModel
) {
    LaunchedEffect(Unit) { viewModel.loadAreasAndCountries() }

    Scaffold(
        modifier = modifier,
        topBar = {
            FilterTopBar(
                stringResource(id = R.string.top_bar_label_filter_area),
                onBackClick
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val state = viewModel.screenState.collectAsState().value
            val searchQuery = viewModel.currentSearchText.collectAsStateWithLifecycle().value
            val items = viewModel.filteredItems
            SearchField(
                searchQuery = searchQuery,
                onQueryChange = {
                    viewModel.onSearchTextChange(it)
                },
                placeHolder = stringResource(R.string.filter_area_search),
                onSearchClear = {
                    viewModel.onClearSearchText()
                }
            )
            when (state) {
                is WorkPlacesScreenState.InternalServerError -> Placeholder(
                    R.drawable.server_error_placeholder,
                    stringResource(R.string.server_error)
                )

                is WorkPlacesScreenState.Loading -> LoadingComponent()
                is WorkPlacesScreenState.Content -> {
                    RegionsList(
                        list = items,
                        onClick = { area ->
                            viewModel.chooseArea(area)
                            onBackClick()
                        }
                    )
                }

                is WorkPlacesScreenState.NoInternetConnection -> Placeholder(
                    R.drawable.error_placeholder,
                    stringResource(R.string.no_internet)
                )

                is WorkPlacesScreenState.NotFound -> Placeholder(
                    R.drawable.location_error_placeholder,
                    stringResource(R.string.no_regions_error)
                )

                else -> {}
            }
        }
    }
}
