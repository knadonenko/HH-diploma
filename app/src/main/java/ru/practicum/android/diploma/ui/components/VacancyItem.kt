package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesInfo
import ru.practicum.android.diploma.ui.theme.Typography
import ru.practicum.android.diploma.ui.theme.paddingHalfBase

@Composable
fun VacancyItem(vacancy: VacanciesInfo, onClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onClick(vacancy.id) })
            .fillMaxSize()
            .padding(vertical = paddingHalfBase)
    ) {
        VacancyLogo(logo = vacancy.employerLogo)
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
            SalaryText(vacancy.salaryFrom, vacancy.salaryTo, vacancy.salaryCurrencySymbol, Typography.body16Regular)
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
