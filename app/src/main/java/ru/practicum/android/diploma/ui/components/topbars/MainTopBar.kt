package ru.practicum.android.diploma.ui.components.topbars

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R

@Composable
fun MainTopBar(
    onFilterIconClick: () -> Unit
) {
    CommonTopBar(
        title = stringResource(id = R.string.top_bar_label_main),
        actions = {
            ToggleActionIcon(
                checkedIconId = R.drawable.ic_filter_on,
                uncheckedIconId = R.drawable.ic_filter_off,
                onClick = onFilterIconClick,
                modifier = Modifier.padding(end = 12.dp)
            )
        }
    )
}

@Composable
fun ToggleActionIcon(
    checkedIconId: Int,
    uncheckedIconId: Int,
    onClick: () -> Unit,
    modifier: Modifier
) {
    var isChecked by remember { mutableStateOf(false) }

    IconButton(onClick = onClick, modifier = modifier) {
        if (isChecked) {
            Icon(
                painter = painterResource(id = checkedIconId),
                contentDescription = stringResource(R.string.top_bar_filter_settings_description),
                tint = Color.Unspecified
            )
        } else {
            Icon(
                painter = painterResource(id = uncheckedIconId),
                contentDescription = stringResource(R.string.top_bar_filter_settings_description),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
