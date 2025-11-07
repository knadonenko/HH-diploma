package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.workplaces.models.WorkPlacesScreenState
import ru.practicum.android.diploma.presentation.workplaces.viewmodel.WorkPlacesViewModel
import ru.practicum.android.diploma.ui.components.topbars.FilterTopBar
import ru.practicum.android.diploma.ui.theme.paddingBase

@Composable
fun FilterCountryScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    viewModel: WorkPlacesViewModel = koinViewModel<WorkPlacesViewModel>()
) {
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = viewModel.screenState.collectAsState().value) {
                is WorkPlacesScreenState.Found -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top
                    ) {
                        items(state.data) { area ->
                            Text(text = area.name ?: "Daleko")
                        }
                    }
                }

                is WorkPlacesScreenState.Default -> {}
                is WorkPlacesScreenState.InternalServerError -> {}
                is WorkPlacesScreenState.Loading -> {}
                is WorkPlacesScreenState.NoInternetConnection -> {}
                is WorkPlacesScreenState.NotFound -> {}
            }
            Button(onBackClick) {
                Text(stringResource(R.string.filter_choose_label))
            }
        }
    }
}
