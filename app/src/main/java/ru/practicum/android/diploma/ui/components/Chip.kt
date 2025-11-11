package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import ru.practicum.android.diploma.ui.theme.LocalTypography
import ru.practicum.android.diploma.ui.theme.blue
import ru.practicum.android.diploma.ui.theme.chipHeight
import ru.practicum.android.diploma.ui.theme.cornerRadius
import ru.practicum.android.diploma.ui.theme.padding12
import ru.practicum.android.diploma.ui.theme.padding4
import ru.practicum.android.diploma.ui.theme.white

@Composable
fun Chip(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(chipHeight)
            .background(blue, RoundedCornerShape(cornerRadius))
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(vertical = padding4, horizontal = padding12),
            text = text,
            color = white,
            style = LocalTypography.current.body16Regular
        )
    }
}

@Preview
@Composable
private fun ChipPreview() {
    Chip("chip text")
}
