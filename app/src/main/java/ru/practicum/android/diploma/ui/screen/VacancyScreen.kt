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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.vacanceis.models.VacancyDetails
import ru.practicum.android.diploma.domain.vacancydetails.models.Phone
import ru.practicum.android.diploma.domain.vacancydetails.models.Vacancy
import ru.practicum.android.diploma.presentation.vacancydetails.models.VacancyDetailsScreenState
import ru.practicum.android.diploma.presentation.vacancydetails.viewmodel.VacancyDetailsViewModel
import ru.practicum.android.diploma.ui.components.LoadingComponent
import ru.practicum.android.diploma.ui.components.Placeholder
import ru.practicum.android.diploma.ui.components.SalaryText
import ru.practicum.android.diploma.ui.components.VacancyLogo
import ru.practicum.android.diploma.ui.components.topbars.CommonTopBar
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.Typography
import ru.practicum.android.diploma.ui.theme.paddingBase
import ru.practicum.android.diploma.ui.theme.paddingDouble
import ru.practicum.android.diploma.ui.theme.paddingHalfBase
import ru.practicum.android.diploma.ui.theme.searchFieldCorner

@Composable
fun VacancyScreen(
    modifier: Modifier,
    vacancyId: String,
    onBackClick: () -> Unit,
    viewModel: VacancyDetailsViewModel = koinViewModel()
) {
    val favoriteState = viewModel.favouriteState.collectAsState().value
    Scaffold(
        modifier = modifier,
        topBar = {
            CommonTopBar(
                stringResource(id = R.string.top_bar_label_vacancy),
                onBackClick,
                actions = {
                    IconButton(
                        onClick = { viewModel.onFavoriteClick() }
                    ) {
                        if (favoriteState) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                tint = LocalCustomColors.current.icons.activeIconColors,
                                contentDescription = "Favorite"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                tint = LocalCustomColors.current.icons.defaultIconColors,
                                contentDescription = "Favorite"
                            )
                        }
                    }
                    IconButton(
                        onClick = { viewModel.onShareClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            tint = LocalCustomColors.current.icons.defaultIconColors,
                            contentDescription = "Share"
                        )
                    }
                }
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
                    onPhoneClick = { viewModel.onPhoneClick(state.data.contacts?.phones?.first()?.formatted!!) },
                    onEmailClick = { viewModel.onEmailClick() }
                )

                VacancyDetailsScreenState.InternalServerError -> Placeholder(
                    R.drawable.server_error_placeholder,
                    stringResource(R.string.server_error)
                )

                VacancyDetailsScreenState.Loading -> LoadingComponent()
                VacancyDetailsScreenState.NoInternetConnection -> Placeholder(
                    R.drawable.error_placeholder,
                    stringResource(R.string.no_internet)
                )

                VacancyDetailsScreenState.NotFound -> Placeholder(
                    R.drawable.no_vacancy_placeholder,
                    stringResource(R.string.bad_request)
                )
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
            VacancyLogo(logo = vacancy.employer?.logo ?: "")
            Column(
                modifier = Modifier.padding(start = paddingHalfBase)
            ) {
                Text(
                    text = vacancy.employer?.name ?: "",
                    style = Typography.body22Medium,
                    color = colorResource(R.color.highlighted_block_text)
                )
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
    Text(
        text = vacancyDetails.description ?: "",
        style = Typography.body16Regular,
        color = colorResource(R.color.text)
    )
    Spacer(modifier = Modifier.padding(top = paddingDouble))
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
        vacancy.skills.forEach { VacancySkill(it) }
        Spacer(modifier = Modifier.padding(top = paddingDouble))
    }
}

@Composable
private fun VacancySkill(skill: String) {
    Row {
        Text(
            modifier = Modifier.padding(horizontal = paddingHalfBase),
            style = Typography.body16Medium,
            text = stringResource(R.string.skill_prefix)
        )
        Text(
            text = skill,
            style = Typography.body16Regular,
            color = colorResource(R.color.text)
        )
    }
}

@Composable
private fun EmployerContacts(
    vacancy: Vacancy,
    onPhoneClick: (phone: String) -> Unit,
    onEmailClick: (email: String) -> Unit
) {
    if (vacancy.contacts?.email != null && vacancy.contacts.phones != null) {
        Text(
            text = stringResource(R.string.contacts_title),
            style = Typography.body22Medium,
            color = colorResource(R.color.text)
        )
        Spacer(modifier = Modifier.padding(top = paddingBase))
        if (vacancy.contacts.phones.isNotEmpty()) {
            vacancy.contacts.phones.forEach { EmployerPhone(it, onPhoneClick) }
        }
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

@Composable
private fun EmployerPhone(
    phone: Phone,
    onPhoneClick: (phone: String) -> Unit
) {
    Text(
        modifier = Modifier.clickable(onClick = { onPhoneClick.invoke(phone.formatted!!) }),
        text = phone.comment ?: "",
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
    val vacancyDetails = VacancyDetails(
        id = "mock",
        vacancyName = "Переворчиватель пингвинов",
        address = "Большая Грузинская, д.1с99, Москва",
        salaryFrom = 1000,
        salaryTo = 100_000,
        salaryCurrencySymbol = "$",
        employerName = "Московский зоопарк",
        employerPhone = listOf(
            VacancyDetails.EmployerPhone("+7 (495) 775-33-70", "основной способ связи"),
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
}
