package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography
import ru.practicum.android.diploma.ui.theme.blue
import ru.practicum.android.diploma.ui.theme.paddingBase
import ru.practicum.android.diploma.ui.theme.size48
import ru.practicum.android.diploma.ui.theme.size60

@Composable
fun IndustryItem(
    id: Int,
    title: String,
    selected: Boolean,
    onSelect: (id: Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(size60)
            .selectable(
                selected = selected,
                onClick = {
                    onSelect(id)
                },
                role = Role.RadioButton
            )
            .padding(horizontal = paddingBase),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = LocalTypography.current.body16Regular,
            color = LocalCustomColors.current.text.primaryTextColors.textColor,
            modifier = Modifier
                .weight(1f)
        )
        RadioButton(
            modifier = Modifier
                .fillMaxHeight()
                .width(size48),
            selected = selected,
            onClick = { onSelect(id) },
            colors = RadioButtonDefaults.colors(
                unselectedColor = blue,
                selectedColor = blue
            )
        )
    }
}
