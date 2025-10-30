package ru.practicum.android.diploma.data.converter

import ru.practicum.android.diploma.data.dto.VacanciesDto
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
                    salaryFrom = vacancyDto.salary?.from,
                    salaryTo = vacancyDto.salary?.to,
                    salaryCurrencySymbol = vacancyDto.salary?.currency?.let { convert(it) }
                )
            }
        )
    }

    private fun convert(currency: String): String {
        return when (currency) {
            RUSSIAN_RUBLE_RUR -> SYMBOL_RUBLE
            RUSSIAN_RUBLE_RUB -> SYMBOL_RUBLE
            BELARUSIAN_RUBLE -> SYMBOL_BELARUSIAN_RUBLE
            US_DOLLAR -> SYMBOL_DOLLAR
            EURO -> SYMBOL_EURO
            KAZAKHSTAN_TENGE -> SYMBOL_TENGE
            UKRAINIAN_HRYVNIA -> SYMBOL_HRYVNIA
            AZERBAIJAN_MANAT -> SYMBOL_MANAT
            UZBEK_SUM -> SYMBOL_SUM
            GEORGIAN_LARI -> SYMBOL_LARI
            KYRGYZSTAN_SOM -> SYMBOL_SOM
            else -> SYMBOL_RUBLE
        }
    }

    companion object {
        private const val RUSSIAN_RUBLE_RUR = "RUR"
        private const val RUSSIAN_RUBLE_RUB = "RUB"
        private const val BELARUSIAN_RUBLE = "BYR"
        private const val US_DOLLAR = "USD"
        private const val EURO = "EUR"
        private const val KAZAKHSTAN_TENGE = "KZT"
        private const val UKRAINIAN_HRYVNIA = "UAH"
        private const val AZERBAIJAN_MANAT = "AZN"
        private const val UZBEK_SUM = "UZS"
        private const val GEORGIAN_LARI = "GEL"
        private const val KYRGYZSTAN_SOM = "KGT"

        private const val SYMBOL_RUBLE = "₽"
        private const val SYMBOL_BELARUSIAN_RUBLE = "Br"
        private const val SYMBOL_DOLLAR = "$"
        private const val SYMBOL_EURO = "€"
        private const val SYMBOL_TENGE = "₸"
        private const val SYMBOL_HRYVNIA = "₴"
        private const val SYMBOL_MANAT = "₼"
        private const val SYMBOL_SUM = "So'm"
        private const val SYMBOL_LARI = "₾"
        private const val SYMBOL_SOM = "с"
    }
}
