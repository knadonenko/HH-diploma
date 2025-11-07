package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography
import ru.practicum.android.diploma.ui.theme.padding16
import ru.practicum.android.diploma.ui.theme.size60

@Composable
fun FilterItem(
    text: String,
    composableElement: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(size60),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = LocalTypography.current.body16Regular,
            color = LocalCustomColors.current.text.secondaryTextColors.textColor,
            modifier = Modifier.weight(1f)
        )
        composableElement()
    }
}
