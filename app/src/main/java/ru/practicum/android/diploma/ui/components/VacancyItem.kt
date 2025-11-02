package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesInfo
import ru.practicum.android.diploma.ui.theme.Typography
import ru.practicum.android.diploma.ui.theme.iconRounding
import ru.practicum.android.diploma.ui.theme.paddingHalfBase

@Composable
fun VacancyItem(vacancy: VacanciesInfo, onClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onClick(vacancy.id) })
            .fillMaxSize()
            .padding(vertical = paddingHalfBase)
    ) {
        Column {
            val iconModifier = Modifier
                .size(48.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(iconRounding))
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.stroke),
                    shape = RoundedCornerShape(iconRounding)
                )
            AsyncImage(
                modifier = iconModifier,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(vacancy.employerLogo)
                    .addHeader("User-Agent", "Chrome/138.0.0.0")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_vacancy_placeholder),
                error = painterResource(R.drawable.ic_vacancy_placeholder),
            )
        }
        Column(
            modifier = Modifier
                .padding(start = paddingHalfBase)
        ) {
            Text(
                text = "${vacancy.name}, ${vacancy.city}",
                style = Typography.body22Medium,
                color = colorResource(R.color.text)
            )
            Text(
                text = vacancy.employerName,
                style = Typography.body16Regular,
                color = colorResource(R.color.text)
            )
            val salary = getSalaryText(vacancy = vacancy)
            Text(
                text = salary,
                style = Typography.body16Regular,
                color = colorResource(R.color.text)
            )
        }
    }
}

@Composable
private fun getSalaryText(vacancy: VacanciesInfo): String {
    val fromText = stringResource(R.string.from)
    val toText = stringResource(R.string.to)
    val noSalaryText = stringResource(R.string.no_salary)

    return when {
        vacancy.salaryFrom != null && vacancy.salaryTo != null ->
            "$fromText ${vacancy.salaryFrom} $toText ${vacancy.salaryTo} ${vacancy.salaryCurrencySymbol}"

        vacancy.salaryFrom != null ->
            "$fromText ${vacancy.salaryFrom} ${vacancy.salaryCurrencySymbol}"

        vacancy.salaryTo != null ->
            "$toText ${vacancy.salaryTo} ${vacancy.salaryCurrencySymbol}"

        else -> noSalaryText
    }
}
