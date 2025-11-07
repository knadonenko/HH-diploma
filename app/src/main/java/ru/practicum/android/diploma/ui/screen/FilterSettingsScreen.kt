package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.FilterItem
import ru.practicum.android.diploma.ui.components.MoneyField
import ru.practicum.android.diploma.ui.components.topbars.FilterTopBar
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.paddingBase

@Composable
fun FilterSettingsScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    toFilterWorkPlace: () -> Unit,
    toFilterIndustry: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            FilterTopBar(
                stringResource(id = R.string.top_bar_label_filter_settings),
                onBackClick
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = paddingBase),
        ) {
            FilterItem(
                stringResource(R.string.filter_work_place_label),
                composableElement = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        tint = LocalCustomColors.current.icons.defaultIconColors,
                        contentDescription = "Arrow Forward"
                    )
                }
            )
            FilterItem(
                stringResource(R.string.filter_industry_label),
                composableElement = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        tint = LocalCustomColors.current.icons.defaultIconColors,
                        contentDescription = "Arrow Forward"
                    )
                }
            )
            MoneyField("")
            FilterItem(
                stringResource(R.string.filter_without_salary),
                composableElement = {
                    Checkbox(
                        checked = false,
                        onCheckedChange = {

                        }
                    )
                }
            )
        }
    }
}
