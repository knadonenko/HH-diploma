package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.topbars.FilterTopBar

@Composable
fun FilterAreaScreen(
    countryId: Int? = null,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            FilterTopBar(
                stringResource(id = R.string.top_bar_label_filter_area),
                onBackClick
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = countryId?.toString() ?: stringResource(id = R.string.test))

            Spacer(modifier = Modifier.height(20.dp))

            Button( onBackClick ) {
                Text(stringResource(R.string.filter_choose_label))
            }
        }
    }
}
