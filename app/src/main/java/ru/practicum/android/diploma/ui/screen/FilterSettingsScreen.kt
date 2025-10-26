package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.topbars.FilterTopBar
import ru.practicum.android.diploma.ui.theme.paddingBase

@Composable
fun FilterSettingsScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    toFilterWorkPlace: () -> Unit,
    toFilterIndustry: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            FilterTopBar(
                stringResource(id = R.string.top_bar_label_filter_settings),
                onBackClick
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = paddingBase),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(toFilterWorkPlace) {
                Text(stringResource(R.string.filter_work_place_label))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(toFilterIndustry) {
                Text(stringResource(R.string.filter_industry_label))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onBackClick) {
                Text(stringResource(R.string.filter_apply_label))
            }
        }
    }
}
