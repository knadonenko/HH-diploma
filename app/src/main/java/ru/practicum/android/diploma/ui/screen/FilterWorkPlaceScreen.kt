package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.FilterItem
import ru.practicum.android.diploma.ui.components.topbars.FilterTopBar
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography
import ru.practicum.android.diploma.ui.theme.cornerRadius
import ru.practicum.android.diploma.ui.theme.paddingBase
import ru.practicum.android.diploma.ui.theme.size18
import ru.practicum.android.diploma.ui.theme.size60
import ru.practicum.android.diploma.ui.theme.size8

@Composable
fun FilterWorkPlaceScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    toFilterCountry: () -> Unit,
    toFilterRegion: (Int?) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            FilterTopBar(
                stringResource(id = R.string.top_bar_label_filter_work_place),
                onBackClick
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .padding(horizontal = paddingBase),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(size8))

            val data = "Россия"
            val areaData = ""
            FilterItem(
                modifier = Modifier.clickable(onClick = toFilterCountry),
                stringResource(R.string.filter_country_label),
                data = data,
                composableElement = {
                    if (data.isEmpty()) {
                        Icon(
                            modifier = Modifier.height(size18),
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            tint = LocalCustomColors.current.icons.defaultIconColors,
                            contentDescription = "Arrow Forward"
                        )
                    } else {
                        Icon(
                            modifier = Modifier.size(size18).clickable { },
                            imageVector = Icons.Default.Clear,
                            tint = LocalCustomColors.current.icons.defaultIconColors,
                            contentDescription = "Clear"
                        )
                    }
                },
                color = LocalCustomColors.current.text.secondaryTextColors.textColor
            )
            FilterItem(
                modifier = Modifier.clickable(onClick = { toFilterRegion(1) }),
                stringResource(R.string.filter_area_label),
                data = areaData,
                composableElement = {
                    if (areaData.isEmpty()) {
                        Icon(
                            modifier = Modifier.height(size18),
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            tint = LocalCustomColors.current.icons.defaultIconColors,
                            contentDescription = "Arrow Forward"
                        )
                    } else {
                        Icon(
                            modifier = Modifier.size(size18).clickable { },
                            imageVector = Icons.Default.Clear,
                            tint = LocalCustomColors.current.icons.defaultIconColors,
                            contentDescription = "Clear"
                        )
                    }
                },
                color = LocalCustomColors.current.text.secondaryTextColors.textColor
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .height(size60)
                    .fillMaxSize(),
                shape = RoundedCornerShape(cornerRadius),
                onClick = { },
                content = {
                    Text(
                        text = stringResource(R.string.filter_choose_label),
                        style = LocalTypography.current.body16Medium
                    )
                }
            )

            Spacer(modifier = Modifier.height(size8))

        }
    }
}
