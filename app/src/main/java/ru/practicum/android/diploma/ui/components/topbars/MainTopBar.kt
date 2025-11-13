package ru.practicum.android.diploma.ui.components.topbars

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.paddingActionEnd

@Composable
fun MainTopBar(
    onFilterIconClick: () -> Unit,
    filterApplied: Boolean
) {
    CommonTopBar(
        title = stringResource(id = R.string.top_bar_label_main),
        actions = {
            ToggleActionIcon(
                checkedIconId = R.drawable.ic_filter_on,
                uncheckedIconId = R.drawable.ic_filter_off,
                filterApplied = filterApplied,
                onClick = onFilterIconClick
            )
        }
    )
}

@Composable
fun ToggleActionIcon(
    checkedIconId: Int,
    uncheckedIconId: Int,
    filterApplied: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick, modifier = Modifier.padding(end = paddingActionEnd)) {
        if (filterApplied) {
            Icon(
                painter = painterResource(id = checkedIconId),
                contentDescription = stringResource(R.string.top_bar_filter_settings_description),
                tint = Color.Unspecified
            )
        } else {
            Icon(
                painter = painterResource(id = uncheckedIconId),
                contentDescription = stringResource(R.string.top_bar_filter_settings_description),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
