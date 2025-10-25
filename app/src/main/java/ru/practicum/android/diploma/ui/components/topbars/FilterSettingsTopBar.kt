package ru.practicum.android.diploma.ui.components.topbars

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R

@Composable
fun FilterTopBar(
    onBackClick: () -> Unit
) {
    CommonTopBar(
        title = stringResource(id = R.string.top_bar_label_filter_settings),
        onBackClick = onBackClick
    )
}
