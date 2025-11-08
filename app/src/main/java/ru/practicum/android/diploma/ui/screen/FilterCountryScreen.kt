package ru.practicum.android.diploma.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import ru.practicum.android.diploma.ui.components.SearchField
import ru.practicum.android.diploma.presentation.workplaces.models.WorkPlacesScreenState
import ru.practicum.android.diploma.presentation.workplaces.viewmodel.WorkPlacesViewModel
import ru.practicum.android.diploma.ui.components.FilterItem
import ru.practicum.android.diploma.ui.components.LoadingComponent
import ru.practicum.android.diploma.ui.components.topbars.FilterTopBar
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.paddingBase
import ru.practicum.android.diploma.ui.theme.size18

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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}

//            when (val state = viewModel.screenState.collectAsState().value) {
//                is WorkPlacesScreenState.Content -> {
//                    LazyColumn(
//                        modifier = Modifier
//                            .fillMaxSize(),
//                        verticalArrangement = Arrangement.Top
//                    ) {
//                        items(state.data) { area ->
//                            Text(text = area.name ?: "Daleko")
//
//                            area.areas?.forEach { region ->
//                                Text(
//                                    text = region.name ?: "Tuta",
//                                    modifier = Modifier.padding(start = paddingBase)
//                                )
//                            }
//                        }
//
//                        item {
//                            Button(onBackClick) {
//                                Text(stringResource(R.string.filter_choose_label))
//                            }
//                        }
//                    }
//                }
//
//                is WorkPlacesScreenState.Default -> {}
//                is WorkPlacesScreenState.Loading -> LoadingComponent()
//                is WorkPlacesScreenState.NotFound -> {
//                    Toast.makeText(context, stringResource(R.string.empty_favorites), Toast.LENGTH_SHORT).show()
//                }
//
//                is WorkPlacesScreenState.NoInternetConnection -> {
//                    Toast.makeText(context, stringResource(R.string.no_internet), Toast.LENGTH_SHORT).show()
//                }
//
//                is WorkPlacesScreenState.InternalServerError -> {
//                    Toast.makeText(context, stringResource(R.string.server_error), Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//}
