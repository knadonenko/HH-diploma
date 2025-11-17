package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import okhttp3.internal.toImmutableList
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.filters.models.FilterIndustry
import ru.practicum.android.diploma.presentation.filters.models.FilterIndustryScreenState
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterIndustryViewModel
import ru.practicum.android.diploma.ui.components.IndustryItem
import ru.practicum.android.diploma.ui.components.LoadingComponent
import ru.practicum.android.diploma.ui.components.Placeholder
import ru.practicum.android.diploma.ui.components.SearchField
import ru.practicum.android.diploma.ui.components.topbars.FilterTopBar
import ru.practicum.android.diploma.ui.theme.LocalTypography
import ru.practicum.android.diploma.ui.theme.cornerRadius
import ru.practicum.android.diploma.ui.theme.paddingBase
import ru.practicum.android.diploma.ui.theme.size4
import ru.practicum.android.diploma.ui.theme.size60

@Composable
fun FilterIndustryScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    viewModel: FilterIndustryViewModel = koinViewModel()
) {
    val query = viewModel.currentSearchText.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = modifier,
        topBar = {
            FilterTopBar(
                stringResource(id = R.string.top_bar_label_filter_industry),
                onBackClick
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val state = viewModel.screenState.collectAsState().value
            val selectedId = viewModel.selectedId.collectAsState().value
            val isSelected = viewModel.isSelected.collectAsState().value

            SearchField(
                searchQuery = query,
                onQueryChange = {
                    viewModel.onSearchTextChange(it)
                },
                placeHolder = stringResource(R.string.filter_industry),
                onSearchClear = { viewModel.onClearSearchText() }
            )

            when (state) {
                is FilterIndustryScreenState.Loading -> LoadingComponent()

                is FilterIndustryScreenState.NoInternetConnection -> Placeholder(
                    R.drawable.error_placeholder,
                    stringResource(R.string.no_internet)
                )

                is FilterIndustryScreenState.NotFound -> Placeholder(
                    R.drawable.no_vacancy_placeholder,
                    stringResource(R.string.empty_favorites)
                )

                is FilterIndustryScreenState.Error -> Placeholder(
                    R.drawable.server_error_placeholder,
                    stringResource(R.string.filter_industry_error)
                )

                is FilterIndustryScreenState.Content -> IndustriesList(
                    modifier = modifier.weight(1f),
                    selectedIndex = selectedId,
                    industriesFound = state.data.toImmutableList(),
                    onSelect = viewModel::onSelectIndustry
                )
            }

            if (isSelected) {
                Button(
                    modifier = Modifier
                        .height(size60)
                        .fillMaxSize()
                        .padding(horizontal = paddingBase),
                    shape = RoundedCornerShape(cornerRadius),
                    onClick = {
                        viewModel.onSaveChoice()
                        onBackClick()
                    },
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
}

@Composable
fun IndustriesList(
    modifier: Modifier,
    selectedIndex: Int?,
    industriesFound: List<FilterIndustry>,
    onSelect: (id: Int) -> Unit
) {
    val listState = rememberLazyListState()
    val newIndex = remember(selectedIndex) { mutableStateOf(selectedIndex) }

    Spacer(modifier = Modifier.height(size4))
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = listState
    ) {
        items(industriesFound) { industry ->
            IndustryItem(
                id = industry.id,
                title = industry.name,
                selected = newIndex.value == industry.id,
                onSelect = {
                    onSelect(industry.id)
                    newIndex.value = industry.id
                }
            )
        }
    }
}
