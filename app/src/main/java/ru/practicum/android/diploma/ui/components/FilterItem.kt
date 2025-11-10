package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography
import ru.practicum.android.diploma.ui.theme.size60

@Composable
fun FilterItem(
    modifier: Modifier,
    text: String,
    data: String = "",
    color: Color,
    composableElement: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(size60),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
        ) {
            if (data.isNotEmpty()) {
                Text(
                    text = text,
                    style = LocalTypography.current.body12Regular,
                    color = LocalCustomColors.current.text.primaryTextColors.textColor,
                )
                Text(
                    text = data,
                    style = LocalTypography.current.body16Regular,
                    color = LocalCustomColors.current.text.primaryTextColors.textColor,
                )
            } else {
                Text(
                    text = text,
                    style = LocalTypography.current.body16Regular,
                    color = color,
                )
            }
        }
        composableElement()
    }
}
