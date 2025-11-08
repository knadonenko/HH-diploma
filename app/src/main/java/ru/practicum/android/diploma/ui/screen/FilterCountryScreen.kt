package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.vacancydetails.models.FilterArea
import ru.practicum.android.diploma.presentation.workplaces.models.WorkPlacesScreenState
import ru.practicum.android.diploma.presentation.workplaces.viewmodel.WorkPlacesViewModel
import ru.practicum.android.diploma.ui.components.LoadingComponent
import ru.practicum.android.diploma.ui.components.Placeholder
import ru.practicum.android.diploma.ui.components.topbars.FilterTopBar
import ru.practicum.android.diploma.ui.theme.paddingBase

@Composable
fun FilterCountryScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    viewModel: WorkPlacesViewModel = koinViewModel<WorkPlacesViewModel>()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.getAreas() }

    Scaffold(
        modifier = modifier,
        topBar = {
            FilterTopBar(
                stringResource(id = R.string.top_bar_label_filter_country),
                onBackClick
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .padding(horizontal = paddingBase),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val state = viewModel.screenState.collectAsState().value
            when (state) {
                is WorkPlacesScreenState.Content -> CountryList(state.data)
                is WorkPlacesScreenState.Default -> {}
                is WorkPlacesScreenState.InternalServerError -> Placeholder(
                    R.drawable.server_error_placeholder,
                    stringResource(R.string.server_error)
                )

                is WorkPlacesScreenState.Loading -> LoadingComponent()
                is WorkPlacesScreenState.NoInternetConnection -> Placeholder(
                    R.drawable.error_placeholder,
                    stringResource(R.string.no_internet)
                )

                is WorkPlacesScreenState.NotFound -> Placeholder(
                    R.drawable.location_error_placeholder,
                    stringResource(R.string.no_regions_error)
                )
            }
        }
    }
}

@Composable
fun CountryList(
    list: List<FilterArea>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        items(list) { area ->
            Text(text = area.name ?: "Daleko")

            area.areas?.forEach { region ->
                Text(
                    text = region.name ?: "Tuta",
                    modifier = Modifier.padding(start = paddingBase)
                )
            }
        }
    }
}
