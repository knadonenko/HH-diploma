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
fun VacancyItem(vacancy: VacanciesInfo, onClick: (VacanciesInfo) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = paddingHalfBase)
            .clickable(onClick = { onClick.invoke(vacancy) })
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
