package ru.practicum.android.diploma.ui.components.topbars

import androidx.compose.runtime.Composable

@Composable
fun FilterTopBar(
    title: String,
    onBackClick: () -> Unit
) {
    CommonTopBar(
        title = title,
        onBackClick = onBackClick
    )
}
