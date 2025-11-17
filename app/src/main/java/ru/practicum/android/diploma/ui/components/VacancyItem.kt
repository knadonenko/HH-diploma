package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesInfo
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.Typography
import ru.practicum.android.diploma.ui.theme.paddingBase
import ru.practicum.android.diploma.ui.theme.paddingHalfBase

@Composable
fun VacancyItem(vacancy: VacanciesInfo, onClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onClick(vacancy.id) })
            .fillMaxSize()
            .padding(vertical = paddingHalfBase)
            .padding(horizontal = paddingBase)
    ) {
        VacancyLogo(logo = vacancy.employerLogo)
        Column(
            modifier = Modifier
                .padding(start = paddingHalfBase)
        ) {
            Text(
                text = "${vacancy.name}, ${vacancy.city}",
                style = Typography.body22Medium,
                color = LocalCustomColors.current.text.primaryTextColors.textColor
            )
            Text(
                text = vacancy.employerName,
                style = Typography.body16Regular,
                color = LocalCustomColors.current.text.primaryTextColors.textColor
            )
            SalaryText(vacancy.salaryFrom, vacancy.salaryTo, vacancy.salaryCurrencySymbol, Typography.body16Regular)
        }
    }
}
