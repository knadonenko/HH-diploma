package ru.practicum.android.diploma.data.converter

import ru.practicum.android.diploma.data.dto.VacanciesDto
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesInfo
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesPage

class VacancyConverter {
    fun map(vacanciesResponse: VacanciesDto): VacanciesPage {
        return VacanciesPage(
            found = vacanciesResponse.found,
            pages = vacanciesResponse.pages,
            page = vacanciesResponse.page,
            vacancies = vacanciesResponse.vacancies.map { vacancyDto ->
                VacanciesInfo(
                    id = vacancyDto.id,
                    name = vacancyDto.name,
                    city = vacancyDto.address?.city,
                    employerName = vacancyDto.employer.name,
                    employerLogo = vacancyDto.employer.logo,
                    salaryRow = convertSalaryToRow(vacancyDto.salary)
                )
            }
        )
    }

    private fun convertSalaryToRow(salary: VacancyDetailDto.SalaryDto?): String? {
        return salary?.let { s ->
            listOfNotNull(
                s.from?.let { "от $it" },
                s.to?.let { "до $it" }
            ).joinToString(" ").trim().takeIf { it.isNotEmpty() }
        }
    }
}
