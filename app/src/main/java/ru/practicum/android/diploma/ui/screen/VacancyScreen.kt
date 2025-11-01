package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.vacanceis.models.VacancyDetails
import ru.practicum.android.diploma.ui.components.SalaryText
import ru.practicum.android.diploma.ui.components.VacancyLogo
import ru.practicum.android.diploma.ui.components.topbars.FilterTopBar
import ru.practicum.android.diploma.ui.theme.Typography
import ru.practicum.android.diploma.ui.theme.paddingBase
import ru.practicum.android.diploma.ui.theme.paddingDouble
import ru.practicum.android.diploma.ui.theme.paddingHalfBase
import ru.practicum.android.diploma.ui.theme.searchFieldCorner

@Composable
fun VacancyScreen(modifier: Modifier, onBackClick: () -> Unit) {
    Scaffold(
        modifier = modifier,
        topBar = {
            FilterTopBar(
                stringResource(id = R.string.top_bar_label_vacancy),
                onBackClick
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .padding(horizontal = paddingBase),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        }
    }
}

@Composable
private fun VacancyBody(vacancy: VacancyDetails) {
    Column(modifier = Modifier.padding(all = paddingBase)) {
        VacancyHeader(vacancy)
        Spacer(modifier = Modifier.padding(top = paddingBase))
        EmployerDescription(vacancy)
        Spacer(modifier = Modifier.padding(top = paddingBase))
        RequiredExperience(vacancy)
        Spacer(modifier = Modifier.padding(top = paddingDouble))
        VacancyDescription(vacancy)
        Spacer(modifier = Modifier.padding(top = paddingDouble))
        VacancySkills(vacancy)
        Spacer(modifier = Modifier.padding(top = paddingDouble))
        EmployerContacts(vacancy)
    }
}

@Composable
private fun VacancyHeader(vacancy: VacancyDetails) {
    Text(
        text = vacancy.vacancyName,
        style = Typography.h1,
        color = colorResource(R.color.text)
    )
    SalaryText(
        from = vacancy.salaryFrom,
        to = vacancy.salaryTo,
        symbol = vacancy.salaryCurrencySymbol,
        style = Typography.body22Medium
    )
}

@Composable
private fun EmployerDescription(vacancy: VacancyDetails) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.stroke),
                shape = RoundedCornerShape(searchFieldCorner)
            )
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(all = paddingBase)) {
            VacancyLogo()
            Column(
                modifier = Modifier.padding(start = paddingHalfBase)
            ) {
                Text(
                    text = vacancy.employerName,
                    style = Typography.body22Medium,
                    color = colorResource(R.color.text)
                )
                Text(
                    text = vacancy.address ?: vacancy.areaName,
                    style = Typography.body16Regular,
                    color = colorResource(R.color.text)
                )
            }
        }
    }
}

@Composable
private fun RequiredExperience(vacancy: VacancyDetails) {
    Text(
        text = stringResource(R.string.exp_title),
        style = Typography.body16Medium,
        color = colorResource(R.color.text)
    )
    Text(
        text = vacancy.experience ?: stringResource(R.string.no_exp),
        style = Typography.body16Regular,
        color = colorResource(R.color.text)
    )
    Spacer(modifier = Modifier.padding(top = paddingHalfBase))
    Text(
        text = vacancy.schedule ?: "Тип занятости не указан",
        style = Typography.body16Regular,
        color = colorResource(R.color.text)
    )
}

@Composable
private fun VacancyDescription(vacancyDetails: VacancyDetails) {
    Text(
        text = "Описание вакансии",
        style = Typography.body22Medium,
        color = colorResource(R.color.text)
    )
    Spacer(modifier = Modifier.padding(top = paddingBase))
    Text(
        text = vacancyDetails.description,
        style = Typography.body16Regular,
        color = colorResource(R.color.text)
    )
}

@Composable
private fun VacancySkills(vacancy: VacancyDetails) {
    Text(
        text = "Ключевые навыки",
        style = Typography.body22Medium,
        color = colorResource(R.color.text)
    )
    Spacer(modifier = Modifier.padding(top = paddingBase))
    Text(
        text = vacancy.skills.joinToString("\n"),
        style = Typography.body16Regular,
        color = colorResource(R.color.text)
    )
}

@Composable
private fun EmployerContacts(vacancy: VacancyDetails) {
    if (vacancy.employerEmail != null && vacancy.employerPhone.isNotEmpty()) {
        Text(
            text = "Контакты",
            style = Typography.body22Medium,
            color = colorResource(R.color.text)
        )
        Spacer(modifier = Modifier.padding(top = paddingBase))
        if (vacancy.employerPhone.isNotEmpty()) {
            Text(
                text = "Номер телефона",
                style = Typography.body16Medium,
                color = colorResource(R.color.text)
            )
            vacancy.employerPhone.forEach { it -> EmployerPhone(it) }
            Spacer(modifier = Modifier.padding(top = paddingBase))
        }
        if (vacancy.employerEmail != null) {
            Text(
                text = "Электронная почта",
                style = Typography.body16Medium,
                color = colorResource(R.color.text)
            )
            Text(
                text = vacancy.employerEmail,
                style = Typography.body16Regular,
                color = colorResource(R.color.text)
            )
        }
    }
}

@Composable
private fun EmployerPhone(phone: VacancyDetails.EmployerPhone) {
    Row {
        Text(
            text = phone.phone,
            style = Typography.body16Regular,
            color = colorResource(R.color.text)
        )
        if (phone.comment != null) {
            Text(
                modifier = Modifier.padding(start = paddingBase),
                text = phone.comment,
                style = Typography.body16Regular,
                color = colorResource(R.color.text)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
@Suppress("MagicNumber")
private fun VacancyBodyPreview() {
    val vacancyDetails = VacancyDetails(
        id = "mock",
        vacancyName = "Переворчиватель пингвинов",
        address = "Большая Грузинская, д.1с99, Москва",
        salaryFrom = 1000,
        salaryTo = 100000,
        salaryCurrencySymbol = "$",
        employerName = "Московский зоопарк",
        employerPhone = listOf(
            VacancyDetails.EmployerPhone("+7 (495) 775-33-70", "основной"),
            VacancyDetails.EmployerPhone("+7 (495) 775-33-70")
        ),
        employerEmail = "zoopark@culture.mos.ru",
        schedule = "Полный день",
        areaName = "Москва",
        experience = "Не требуется",
        description = "Ответственность за переворачивание пингвинов, упавших на спину.\n" +
            "Контроль за правильным положением птиц. Взаимодействие с ветеринарной службой.\n" +
            "Наблюдение за поведением пингвинов в вольере.",
        skills = listOf("Работа с животными", "Внимательность", "Физическая выносливость", "Любовь к птицам")
    )
    VacancyBody(vacancyDetails)
}
