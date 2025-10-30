package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.topbars.CommonTopBar
import ru.practicum.android.diploma.ui.theme.LocalTypography
import ru.practicum.android.diploma.ui.theme.grey500
import ru.practicum.android.diploma.ui.theme.iconSmall
import ru.practicum.android.diploma.ui.theme.paddingBase
import ru.practicum.android.diploma.ui.theme.paddingHalfBase

@Composable
fun TeamScreen(modifier: Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CommonTopBar(stringResource(R.string.top_bar_label_team))
        }
    ) { padding ->

        val typography = LocalTypography.current
        val uriHandler = LocalUriHandler.current
        val team = listOf(
            Teammate(
                stringResource(R.string.team_konstantin),
                stringResource(R.string.team_konstantin_link),
                stringResource(R.string.team_konstantin_emoji)
            ),
            Teammate(
                stringResource(R.string.team_dmitriy_r),
                stringResource(R.string.team_dmitriy_r_link),
                stringResource(R.string.team_dmitriy_r_emoji)
            ),
            Teammate(
                stringResource(R.string.team_dmitriy_g),
                stringResource(R.string.team_dmitriy_g_link),
                stringResource(R.string.team_dmitriy_g_emoji)
            ),
            Teammate(
                stringResource(R.string.team_victor),
                stringResource(R.string.team_victor_link),
                stringResource(R.string.team_victor_emoji)
            ),
            Teammate(
                stringResource(R.string.team_dmitriy_s),
                stringResource(R.string.team_dmitriy_s_link),
                stringResource(R.string.team_dmitriy_s_emoji)
            )
        )

        LazyColumn(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            item {
                Spacer(modifier = modifier.height(paddingBase))
                Text(
                    text = stringResource(R.string.team_about),
                    style = typography.h1,
                    modifier = modifier
                        .padding(horizontal = paddingBase)
                        .padding(vertical = paddingHalfBase)
                )
                Spacer(modifier = modifier.height(paddingBase))
            }

            items(team) { teammate ->
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable { uriHandler.openUri(teammate.link) }
                        .padding(horizontal = paddingBase)
                        .padding(vertical = paddingHalfBase),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${teammate.emoji} ${teammate.name}",
                        style = typography.body16Medium
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_github_logo),
                        contentDescription = null,
                        tint = grey500,
                        modifier = Modifier
                            .padding(horizontal = paddingHalfBase)
                            .size(iconSmall)
                    )
                }
            }
        }
    }
}

data class Teammate(
    val name: String,
    val link: String,
    val emoji: String
)
