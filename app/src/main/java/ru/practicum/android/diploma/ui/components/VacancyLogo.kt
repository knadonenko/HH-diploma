package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.cornerRadius
import ru.practicum.android.diploma.ui.theme.padding4
import ru.practicum.android.diploma.ui.theme.size1
import ru.practicum.android.diploma.ui.theme.size48

@Composable
fun VacancyLogo(modifier: Modifier = Modifier, logo: String? = null) {
    Column {
        Box(
            modifier = modifier
                .size(size48)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(cornerRadius))
                .border(
                    width = size1,
                    color = colorResource(R.color.stroke),
                    shape = RoundedCornerShape(cornerRadius)
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier.padding(padding4),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(logo)
                    .addHeader("User-Agent", "Chrome/138.0.0.0")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                placeholder = painterResource(R.drawable.ic_vacancy_placeholder),
                error = painterResource(R.drawable.ic_vacancy_placeholder),
            )
        }
    }
}
