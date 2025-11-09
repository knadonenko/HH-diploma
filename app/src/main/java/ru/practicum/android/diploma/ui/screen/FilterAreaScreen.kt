package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filters.models.WorkPlacesScreenState
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterWorkPlaceViewModel
import ru.practicum.android.diploma.ui.components.SearchField
import ru.practicum.android.diploma.ui.components.topbars.FilterTopBar
import ru.practicum.android.diploma.ui.theme.paddingBase

@Composable
fun FilterAreaScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    viewModel: FilterWorkPlaceViewModel
) {
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
            modifier = modifier
                .padding(padding)
                .padding(horizontal = paddingBase),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = viewModel.screenState.collectAsState().value) {
                is WorkPlacesScreenState.Content -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top
                    ) {
//            SearchField(
//                searchQuery = "",
//                onQueryChange = {
//
//                },
//                placeHolder = stringResource(R.string.filter_area_search),
//                onSearchClear = {
//
//                }
//            )

                        items(state.availableAreas.first { it.equals(state.chosenCountry) }.areas!!) { area ->
                            Text(
                                modifier = Modifier.clickable(onClick = { viewModel.chooseArea(area) }),
                                text = area.name ?: "Daleko"
                            )
                        }

                        item {
                            Button(onBackClick) {
                                Text(stringResource(R.string.filter_choose_label))
                            }
                        }
                    }
                }

                else -> onBackClick.invoke()
            }
        }
    }
}
