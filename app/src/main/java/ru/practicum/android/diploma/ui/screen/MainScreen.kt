package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.ui.components.topbars.MainTopBar
import ru.practicum.android.diploma.ui.theme.emptyDimen
import ru.practicum.android.diploma.ui.theme.paddingBase

@Composable
fun MainScreen(modifier: Modifier, onFilterClick: () -> Unit) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            MainTopBar(onFilterClick)
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .padding(vertical = emptyDimen)
                .fillMaxSize()
                .padding(horizontal = paddingBase),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /* body */
        }
    }
}
