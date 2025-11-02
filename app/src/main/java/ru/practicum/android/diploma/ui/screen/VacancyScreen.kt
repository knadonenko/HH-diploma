package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.topbars.FilterTopBar
import ru.practicum.android.diploma.ui.theme.paddingBase

@Composable
fun VacancyScreen(modifier: Modifier, vacancyId: String, onBackClick: () -> Unit) {
    Scaffold(
        modifier = modifier,
        topBar = {
            FilterTopBar(
                stringResource(id = R.string.top_bar_label_vacancy),
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
            /* body */
        }
    }
}
