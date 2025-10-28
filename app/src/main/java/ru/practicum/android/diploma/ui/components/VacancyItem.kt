package ru.practicum.android.diploma.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.theme.Typography

private const val ICON_WEIGHT = 0.2f
private const val DESCRIPTION_WEIGHT = 0.8f

@Composable
fun VacancyItem(vacancy: Vacancy, onClick: (Vacancy) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 9.dp)
            .clickable(onClick = { onClick.invoke(vacancy) })
    ) {
        Column(modifier = Modifier.weight(ICON_WEIGHT)) {
            val iconModifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.stroke),
                    shape = RoundedCornerShape(8.dp)
                )
            if (vacancy.image == null) {
                Image(
                    modifier = iconModifier,
                    painter = painterResource(R.drawable.ic_vacancy_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    modifier = iconModifier,
                    model = vacancy.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(DESCRIPTION_WEIGHT)
                .padding(start = 8.dp)
        ) {
            Text(
                text = "${vacancy.vacancyName}, ${vacancy.cityName}",
                style = Typography.body22Medium,
                color = colorResource(R.color.text)
            )
            Text(
                text = vacancy.companyName,
                style = Typography.body16Regular,
                color = colorResource(R.color.text)
            )
            Text(
                text = getSalaryText(vacancy),
                style = Typography.body16Regular,
                color = colorResource(R.color.text)
            )
        }
    }
}

@Composable
private fun getSalaryText(vacancy: Vacancy): String {
    val fromText = stringResource(R.string.from)
    val toText = stringResource(R.string.to)
    return if (vacancy.salaryFrom != null && vacancy.salaryTo != null) {
        "$fromText ${vacancy.salaryFrom} $toText ${vacancy.salaryTo} ${vacancy.salaryCurrencySymbol}"
    } else if (vacancy.salaryFrom != null) {
        "$fromText ${vacancy.salaryFrom} ${vacancy.salaryCurrencySymbol}"
    } else if (vacancy.salaryTo != null) {
        "$toText ${vacancy.salaryTo} ${vacancy.salaryCurrencySymbol}"
    } else {
        stringResource(R.string.no_salary)
    }
}

@Suppress("MagicNumber")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
private fun VacancyItemPreviewNight() {
    VacancyItem(Vacancy(null, "Vacancy Name", "City", "Company", 228, 1000, "₽"))
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
private fun VacancyItemPreviewDay() {
    VacancyItem(Vacancy(null, "Vacancy Name", "City", "Company", null, null, "₽"))
}
