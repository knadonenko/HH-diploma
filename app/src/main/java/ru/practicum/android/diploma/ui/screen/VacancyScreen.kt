package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.vacancydetails.models.Address
import ru.practicum.android.diploma.domain.vacancydetails.models.Contacts
import ru.practicum.android.diploma.domain.vacancydetails.models.Employer
import ru.practicum.android.diploma.domain.vacancydetails.models.FilterArea
import ru.practicum.android.diploma.domain.vacancydetails.models.Phone
import ru.practicum.android.diploma.domain.vacancydetails.models.Salary
import ru.practicum.android.diploma.domain.vacancydetails.models.Vacancy
import ru.practicum.android.diploma.presentation.vacancydetails.models.VacancyDetailsScreenState
import ru.practicum.android.diploma.presentation.vacancydetails.viewmodel.VacancyDetailsViewModel
import ru.practicum.android.diploma.ui.components.SalaryText
import ru.practicum.android.diploma.ui.components.VacancyLogo
import ru.practicum.android.diploma.ui.components.topbars.FilterTopBar
import ru.practicum.android.diploma.ui.theme.Typography
import ru.practicum.android.diploma.ui.theme.paddingBase
import ru.practicum.android.diploma.ui.theme.paddingDouble
import ru.practicum.android.diploma.ui.theme.paddingHalfBase
import ru.practicum.android.diploma.ui.theme.searchFieldCorner

@Composable
fun VacancyScreen(
    modifier: Modifier,
    onBackClick: () -> Unit,
    viewModel: VacancyDetailsViewModel = koinViewModel()
) {
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
            val state = viewModel.screenState.collectAsState().value

            when (state) {
                VacancyDetailsScreenState.Default -> {}
                is VacancyDetailsScreenState.Found -> VacancyBody(
                    vacancy = state.data,
                    onPhoneClick = { viewModel.onPhoneClick(it) },
                    onEmailClick = { viewModel.onEmailClick(it) }
                )

                VacancyDetailsScreenState.InternalServerError -> {}
                VacancyDetailsScreenState.Loading -> {}
                VacancyDetailsScreenState.NoInternetConnection -> {}
                VacancyDetailsScreenState.NotFound -> {}
            }
        }
    }
}

@Composable
private fun VacancyBody(
    vacancy: Vacancy,
    onPhoneClick: (phone: String) -> Unit = {},
    onEmailClick: (email: String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        VacancyHeader(vacancy)
        EmployerDescription(vacancy)
        RequiredExperience(vacancy)
        VacancyDescription(vacancy)
        VacancySkills(vacancy)
        EmployerContacts(vacancy, onPhoneClick, onEmailClick)
    }
}

@Composable
private fun VacancyHeader(vacancy: Vacancy) {
    Text(
        text = vacancy.name ?: "",
        style = Typography.h1,
        color = colorResource(R.color.text)
    )
    SalaryText(
        from = vacancy.salary?.from,
        to = vacancy.salary?.to,
        symbol = vacancy.salary?.currency,
        style = Typography.body22Medium
    )
    Spacer(modifier = Modifier.padding(top = paddingBase))
}

@Composable
private fun EmployerDescription(vacancy: Vacancy) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.highlighted_block),
                shape = RoundedCornerShape(searchFieldCorner)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = paddingBase)
        ) {
            VacancyLogo()
            Column(
                modifier = Modifier.padding(start = paddingHalfBase)
            ) {
                Text(
                    text = vacancy.employer?.name ?: "",
                    style = Typography.body22Medium,
                    color = colorResource(R.color.highlighted_block_text)
                )
                Text(
                    text = vacancy.address?.fullAddress ?: vacancy.area?.name ?: "",
                    style = Typography.body16Regular,
                    color = colorResource(R.color.highlighted_block_text)
                )
            }
        }
    }
    Spacer(modifier = Modifier.padding(top = paddingBase))
}

@Composable
private fun RequiredExperience(vacancy: Vacancy) {
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
        text = vacancy.schedule ?: stringResource(R.string.no_schedule),
        style = Typography.body16Regular,
        color = colorResource(R.color.text)
    )
    Spacer(modifier = Modifier.padding(top = paddingDouble))
}

@Composable
private fun VacancyDescription(vacancyDetails: Vacancy) {
    Text(
        text = stringResource(R.string.description_title),
        style = Typography.body22Medium,
        color = colorResource(R.color.text)
    )
    Spacer(modifier = Modifier.padding(top = paddingBase))
    VacancyDescriptionText(vacancyDetails.description ?: "")
    Spacer(modifier = Modifier.padding(top = paddingDouble))
}

@Composable
private fun VacancyDescriptionText(description: String) {
    val lines = description.split("\n")
    Column {
        lines.forEach { line ->
            val trimmedLine = line.trim()
            when {
                trimmedLine.matches(".*[А-Яа-я]+:".toRegex())
                    && !trimmedLine.startsWith("-") -> VacancyTextSubtitle(trimmedLine)

                trimmedLine.startsWith("-") || trimmedLine.startsWith("\n") -> VacancyTextEnumeration(
                    trimmedLine.replaceFirst(
                        "-",
                        ""
                    )
                )

                else -> VacancyTextRegular(trimmedLine)
            }
        }
    }
}

@Composable
private fun VacancyTextSubtitle(line: String) {
    Spacer(modifier = Modifier.padding(top = paddingBase))
    Text(
        text = line,
        style = Typography.body16Medium,
        color = colorResource(R.color.text)
    )
    Spacer(modifier = Modifier.padding(top = 4.dp))
}

@Composable
private fun VacancyTextEnumeration(line: String) {
    Row {
        Text(
            modifier = Modifier.padding(horizontal = paddingHalfBase),
            style = Typography.body16Medium,
            text = stringResource(R.string.skill_prefix)
        )
        Text(
            text = line,
            style = Typography.body16Regular,
            color = colorResource(R.color.text)
        )
    }
}

@Composable
private fun VacancyTextRegular(line: String) {
    if (line.isNotEmpty()) {
        Text(
            text = line,
            style = Typography.body16Regular,
            color = colorResource(R.color.text)
        )
    }
}

@Composable
private fun VacancySkills(vacancy: Vacancy) {
    if (vacancy.skills?.isNotEmpty() == true) {
        Text(
            text = stringResource(R.string.skills_title),
            style = Typography.body22Medium,
            color = colorResource(R.color.text)
        )
        Spacer(modifier = Modifier.padding(top = paddingBase))
        vacancy.skills.forEach { VacancyTextEnumeration(it) }
        Spacer(modifier = Modifier.padding(top = paddingDouble))
    }
}

@Composable
private fun EmployerContacts(
    vacancy: Vacancy,
    onPhoneClick: (phone: String) -> Unit,
    onEmailClick: (email: String) -> Unit
) {
    if (vacancy.contacts != null
        && ((vacancy.contacts.email != null && vacancy.contacts.email.isNotEmpty()) || (vacancy.contacts.phones != null && vacancy.contacts.phones.isNotEmpty()))
    ) {
        Text(
            text = stringResource(R.string.contacts_title),
            style = Typography.body22Medium,
            color = colorResource(R.color.text)
        )
        Spacer(modifier = Modifier.padding(top = paddingBase))
        if (vacancy.contacts.phones != null && vacancy.contacts.phones.isNotEmpty()) {
            vacancy.contacts.phones.forEach { EmployerPhone(it, onPhoneClick) }
        }
        if (vacancy.contacts.email != null && vacancy.contacts.email.isNotEmpty()) {
            Text(
                text = stringResource(R.string.email_subtitle),
                style = Typography.body16Medium,
                color = colorResource(R.color.text)
            )
            Text(
                modifier = Modifier.clickable(onClick = { onEmailClick.invoke(vacancy.contacts.email) }),
                text = vacancy.contacts.email,
                style = Typography.body16Regular,
                color = colorResource(R.color.text)
            )
        }
    }
}

@Composable
private fun EmployerPhone(
    phone: Phone,
    onPhoneClick: (phone: String) -> Unit
) {
    Text(
        modifier = Modifier.clickable(onClick = { onPhoneClick.invoke(phone.formatted!!) }),
        text = phone.formatted ?: "",
        style = Typography.body16Medium,
        color = colorResource(R.color.text)
    )
    if (phone.comment != null) {
        Text(
            text = phone.comment,
            style = Typography.body16Regular,
            color = colorResource(R.color.text)
        )
    }
    Spacer(modifier = Modifier.padding(top = paddingHalfBase))
}

@Preview(showSystemUi = true)
@Composable
@Suppress("MagicNumber")
private fun VacancyBodyPreview() {
    val vacancyDetails = Vacancy(
        id = "mock",
        name = "Переворчиватель пингвинов",
        address = Address("Москва", "Большая Грузинская", "д.1с99", "Большая Грузинская, д.1с99, Москва"),
        salary = Salary(1000, 100_000, "$"),
        employer = Employer("mock", "Московский зоопарк", null),
        contacts = Contacts(
            "mock",
            "mock",
            "zoopark@culture.mos.ru",
            listOf(Phone("основной способ связи", "+7 (495) 775-33-70"), Phone(null, "+7 (495) 775-33-70"))
        ),
        schedule = "Полный день",
        area = FilterArea(0, "Москва", null, null),
        experience = "Не требуется",
        description = "123",
        skills = listOf("Работа с животными", "Внимательность", "Физическая выносливость", "Любовь к птицам"),
        employment = null,
        url = null,
        industry = null,
        isFavorite = false
    )
    VacancyBody(vacancyDetails)
}
