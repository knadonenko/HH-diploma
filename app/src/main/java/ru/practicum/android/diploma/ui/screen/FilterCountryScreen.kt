package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.vacancydetails.models.FilterArea
import ru.practicum.android.diploma.presentation.filters.models.WorkPlacesScreenState
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterWorkPlaceViewModel
import ru.practicum.android.diploma.ui.components.LoadingComponent
import ru.practicum.android.diploma.ui.components.Placeholder
import ru.practicum.android.diploma.ui.components.topbars.FilterTopBar
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography
import ru.practicum.android.diploma.ui.theme.paddingBase
import ru.practicum.android.diploma.ui.theme.size18
import ru.practicum.android.diploma.ui.theme.size60

@Composable
fun FilterCountryScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    viewModel: FilterWorkPlaceViewModel
) {
    LaunchedEffect(Unit) { viewModel.loadCountries() }

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
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val state = viewModel.screenState.collectAsState().value
            when (state) {
                is WorkPlacesScreenState.Content -> RegionsList(
                    state.countries,
                    onClick = { area ->
                        viewModel.chooseCountry(area)
                        onBackClick()
                    }
                )

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

                else -> {}
            }
        }
    }
}

@Composable
fun RegionsList(
    list: List<FilterArea>,
    onClick: (FilterArea) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        items(list) { area ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(size60)
                    .clickable(onClick = { onClick(area) })
                    .padding(horizontal = paddingBase),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = area.name ?: stringResource(R.string.filter_regions_other),
                        style = LocalTypography.current.body16Regular,
                        color = LocalCustomColors.current.text.primaryTextColors.textColor
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(size60),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = "Arrow",
                        modifier = Modifier.size(size18),
                        tint = LocalCustomColors.current.icons.defaultIconColors,
                    )
                }
            }
        }
    }
}
