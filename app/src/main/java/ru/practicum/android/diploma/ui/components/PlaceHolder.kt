package ru.practicum.android.diploma.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography
import ru.practicum.android.diploma.ui.theme.padding64
import ru.practicum.android.diploma.ui.theme.paddingBase
import ru.practicum.android.diploma.ui.theme.placeholderHeight
import ru.practicum.android.diploma.ui.theme.placeholderWidth

@Composable
fun Placeholder(@DrawableRes imageResId: Int, text: String? = null) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier.size(width = placeholderWidth, height = placeholderHeight),
                painter = painterResource(imageResId),
                contentDescription = text
            )
            if (!text.isNullOrEmpty()) {
                Text(
                    modifier = Modifier.padding(horizontal = padding64, vertical = paddingBase),
                    textAlign = TextAlign.Center,
                    text = text,
                    style = LocalTypography.current.body22Medium,
                    color = LocalCustomColors.current.text.primaryTextColors.textColor
                )
            }
        }
    }
}
