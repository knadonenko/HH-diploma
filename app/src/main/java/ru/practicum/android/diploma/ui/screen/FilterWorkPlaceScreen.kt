package ru.practicum.android.diploma.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filters.models.WorkPlacesScreenState
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterWorkPlaceViewModel
import ru.practicum.android.diploma.ui.components.LoadingComponent
import ru.practicum.android.diploma.ui.components.topbars.FilterTopBar
import ru.practicum.android.diploma.ui.theme.paddingBase

@Composable
fun FilterWorkPlaceScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    toFilterCountry: () -> Unit,
    toFilterRegion: () -> Unit,
    viewModel: FilterWorkPlaceViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.loadAreas() }

    Scaffold(
        modifier = modifier,
        topBar = {
            FilterTopBar(
                title = stringResource(id = R.string.top_bar_label_filter_work_place),
                onBackClick = {
                    viewModel.cleanLoadedAreas()
                    onBackClick.invoke()
                }
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
                is WorkPlacesScreenState.Content -> {
                    if (state.chosenCountry != null) {
                        Text(state.chosenCountry!!.name ?: "")
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    if (state.chosenArea != null) {
                        Text(state.chosenArea!!.name ?: "")
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    Button(toFilterCountry) {
                        Text(stringResource(R.string.filter_country_label))
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        enabled = state.chosenCountry != null,
                        onClick = { toFilterRegion() }
                    ) {
                        Text(stringResource(R.string.filter_area_label))
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        enabled = state.chosenArea != null,
                        onClick = {
                            viewModel.onSaveChoice(state.chosenArea!!)
                            onBackClick.invoke()
                        }
                    ) {
                        Text(stringResource(R.string.filter_choose_label))
                    }
                }

                is WorkPlacesScreenState.Loading -> LoadingComponent()

                is WorkPlacesScreenState.NoInternetConnection -> {
                    Toast.makeText(context, stringResource(R.string.no_internet), Toast.LENGTH_SHORT).show()
                }

                is WorkPlacesScreenState.InternalServerError -> {
                    Toast.makeText(context, stringResource(R.string.server_error), Toast.LENGTH_SHORT).show()
                }

                is WorkPlacesScreenState.Default -> {

                }

                else -> {
                    Toast.makeText(context, stringResource(R.string.empty_favorites), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
