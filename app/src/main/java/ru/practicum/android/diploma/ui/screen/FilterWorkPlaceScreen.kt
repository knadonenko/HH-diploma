package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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

private const val MIN_TEST: Int = 1
private const val MAX_TEST: Int = 100

@Composable
fun FilterWorkPlaceScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    toFilterCountry: () -> Unit,
    toFilterRegion: (Int?) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            FilterTopBar(
                stringResource(id = R.string.top_bar_label_filter_work_place),
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
            Button(toFilterCountry) {
                Text(stringResource(R.string.filter_country_label))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button({ toFilterRegion((MIN_TEST..MAX_TEST).random()) }) {
                Text(stringResource(R.string.filter_area_label))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onBackClick) {
                Text(stringResource(R.string.filter_choose_label))
            }

        }
    }
}
