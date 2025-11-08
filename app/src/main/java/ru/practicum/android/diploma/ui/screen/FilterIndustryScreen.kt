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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filters.models.FilterIndustryScreenState
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
    onBackClick: () -> Unit
) {
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
                .padding(horizontal = paddingBase)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // todo убрать, использовать список из API
            val listOf = listOf("Индустрия 1", "Индустрия 2")
            val query = remember { mutableStateOf("") }
            val state = FilterIndustryScreenState.Content(listOf)

            SearchField(
                searchQuery = query.value,
                onQueryChange = { },
                placeHolder = stringResource(R.string.filter_industry),
                onSearchClear = { }
            )

            when (state) {
                is FilterIndustryScreenState.Loading -> LoadingComponent()
                is FilterIndustryScreenState.Error -> Placeholder(
                    R.drawable.error_placeholder,
                    stringResource(R.string.filter_industry_error)
                )

                is FilterIndustryScreenState.Content -> IndustriesList(
                    modifier = modifier.weight(1f),
                    selectedIndex = null,
                    industriesFound = state.industries
                )
            }
            // todo добавить условие отображения
            Button(
                modifier = Modifier
                    .height(size60)
                    .fillMaxSize(),
                shape = RoundedCornerShape(cornerRadius),
                onClick = { },
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

@Composable
fun IndustriesList(
    modifier: Modifier,
    selectedIndex: Int?,
    industriesFound: List<String>
) {

    val listState = rememberLazyListState()
    val newIndex = remember { mutableStateOf(selectedIndex) }

    Spacer(modifier = Modifier.height(size4))
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = listState
    ) {
        items(industriesFound) { industry ->
            IndustryItem(
                id = industriesFound.indexOf(industry),
                title = industry,
                selected = newIndex.value == industriesFound.indexOf(industry),
                onSelect = {
                    newIndex.value = industriesFound.indexOf(industry)
                }
            )
        }
    }
}
