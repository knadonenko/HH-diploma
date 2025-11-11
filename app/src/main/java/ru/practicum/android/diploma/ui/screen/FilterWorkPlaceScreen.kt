package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filters.models.WorkPlacesScreenState
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterWorkPlaceViewModel
import ru.practicum.android.diploma.ui.components.FilterItem
import ru.practicum.android.diploma.ui.components.LoadingComponent
import ru.practicum.android.diploma.ui.components.topbars.FilterTopBar
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography
import ru.practicum.android.diploma.ui.theme.cornerRadius
import ru.practicum.android.diploma.ui.theme.paddingBase
import ru.practicum.android.diploma.ui.theme.size18
import ru.practicum.android.diploma.ui.theme.size60
import ru.practicum.android.diploma.ui.theme.size8

@Composable
fun FilterWorkPlaceScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    toFilterCountry: () -> Unit,
    toFilterRegion: () -> Unit,
    viewModel: FilterWorkPlaceViewModel
) {

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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var state = viewModel.screenState.collectAsState().value
            when (state) {
                is WorkPlacesScreenState.Loading -> LoadingComponent()
                is WorkPlacesScreenState.Default -> Content(
                    countryData = "",
                    regionData = "",
                    toFilterCountry = toFilterCountry,
                    toFilterRegion = toFilterRegion,
                    clearCountry = { },
                    clearRegion = { viewModel.clearRegion() },
                    onApplyClick = { }
                )

                is WorkPlacesScreenState.Content -> Content(
                    countryData = state.chosenCountry?.name ?: "",
                    regionData = state.chosenArea?.name ?: "",
                    toFilterCountry = toFilterCountry,
                    toFilterRegion = toFilterRegion,
                    clearCountry = {
                        viewModel.clearCountry()
                    },
                    clearRegion = {
                        viewModel.clearRegion()
                    },
                    onApplyClick = {
                        viewModel.onSaveChoice()
                        onBackClick()
                    }
                )

                else -> {}
            }
        }
    }
}

@Composable
fun Content(
    countryData: String = "",
    regionData: String = "",
    toFilterCountry: () -> Unit,
    toFilterRegion: () -> Unit,
    clearCountry: () -> Unit,
    clearRegion: () -> Unit,
    onApplyClick: () -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(size8))
        FilterItem(
            modifier = Modifier.clickable(onClick = toFilterCountry),
            stringResource(R.string.filter_country_label),
            data = countryData,
            composableElement = {
                if (countryData.isEmpty()) {
                    Icon(
                        modifier = Modifier.height(size18),
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        tint = LocalCustomColors.current.icons.defaultIconColors,
                        contentDescription = "Arrow Forward"
                    )
                } else {
                    Icon(
                        modifier = Modifier
                            .size(size18)
                            .clickable { clearCountry() },
                        imageVector = Icons.Default.Clear,
                        tint = LocalCustomColors.current.icons.defaultIconColors,
                        contentDescription = "Clear"
                    )
                }
            },
            color = LocalCustomColors.current.text.secondaryTextColors.textColor
        )
        FilterItem(
            modifier = Modifier.clickable(onClick = toFilterRegion),
            stringResource(R.string.filter_area_label),
            data = regionData,
            composableElement = {
                if (regionData.isEmpty()) {
                    Icon(
                        modifier = Modifier.height(size18),
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        tint = LocalCustomColors.current.icons.defaultIconColors,
                        contentDescription = "Arrow Forward"
                    )
                } else {
                    Icon(
                        modifier = Modifier
                            .size(size18)
                            .clickable { clearRegion() },
                        imageVector = Icons.Default.Clear,
                        tint = LocalCustomColors.current.icons.defaultIconColors,
                        contentDescription = "Clear"
                    )
                }
            },
            color = LocalCustomColors.current.text.secondaryTextColors.textColor
        )
        Spacer(modifier = Modifier.weight(1f))
        if (!countryData.isEmpty() || !regionData.isEmpty()) {
            Button(
                modifier = Modifier
                    .height(size60)
                    .fillMaxSize(),
                shape = RoundedCornerShape(cornerRadius),
                onClick = onApplyClick,
                content = {
                    Text(
                        text = stringResource(R.string.filter_choose_label),
                        style = LocalTypography.current.body16Medium
                    )
                }
            )
        }
    }
}
