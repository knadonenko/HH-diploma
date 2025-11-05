package ru.practicum.android.diploma.data.converter

import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.data.dto.VacancyDetailsDto
import ru.practicum.android.diploma.data.entity.VacancyEntity
import ru.practicum.android.diploma.data.network.response.VacanciesResponse
import ru.practicum.android.diploma.data.network.response.VacancyDetailsResponse
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesInfo
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesPage
import ru.practicum.android.diploma.domain.vacancydetails.models.Address
import ru.practicum.android.diploma.domain.vacancydetails.models.Contacts
import ru.practicum.android.diploma.domain.vacancydetails.models.Employer
import ru.practicum.android.diploma.domain.vacancydetails.models.FilterArea
import ru.practicum.android.diploma.domain.vacancydetails.models.Phone
import ru.practicum.android.diploma.domain.vacancydetails.models.Salary
import ru.practicum.android.diploma.domain.vacancydetails.models.Vacancy

class VacancyConverter {
    fun map(vacanciesResponse: VacanciesResponse): VacanciesPage {
        return VacanciesPage(
            found = vacanciesResponse.found,
            pages = vacanciesResponse.pages,
            page = vacanciesResponse.page,
            vacancies = vacanciesResponse.items.map { vacancyDto ->
                VacanciesInfo(
                    id = vacancyDto.id,
                    name = vacancyDto.name,
                    city = vacancyDto.address?.city,
                    employerName = vacancyDto.employer.name,
                    employerLogo = vacancyDto.employer.logo,
                    salaryFrom = vacancyDto.salary.from,
                    salaryTo = vacancyDto.salary.to,
                    salaryCurrencySymbol = vacancyDto.salary.currency?.let { convert(it) }
                )
            }
        )
    }

    fun map(vacancy: VacancyDetailsResponse): Vacancy {
        return Vacancy(
            id = vacancy.id,
            name = vacancy.name,
            description = vacancy.description,
            salary = convertSalary(vacancy.salary),
            address = convertAddress(vacancy.address),
            experience = vacancy.experience?.name,
            schedule = vacancy.schedule?.name,
            employment = vacancy.employment?.name,
            contacts = convertContracts(vacancy.contacts),
            employer = convertEmployer(vacancy.employer),
            area = convertFilterArea(vacancy.area),
            skills = vacancy.skills,
            url = vacancy.url,
            industry = vacancy.industry?.name,
            isFavorite = false
        )
    }

    private fun convertSalary(salaryDto: VacancyDetailsDto.SalaryDto?): Salary? {
        return salaryDto?.let {
            Salary(
                from = it.from,
                to = it.to,
                currency = it.currency
            )
        }
    }

    private fun convertAddress(addressDto: VacancyDetailsDto.AddressDto?): Address? {
        return addressDto?.let {
            Address(
                city = it.city,
                street = it.street,
                building = it.building,
                fullAddress = it.raw
            )
        }
    }

    private fun convertContracts(contactsDto: VacancyDetailsDto.ContactsDto?): Contacts? {
        return contactsDto?.let {
            Contacts(
                id = it.id,
                name = it.name,
                email = it.email,
                phones = it.phones?.map { phone ->
                    Phone(
                        phone.comment,
                        phone.formatted
                    )
                }
            )
        }
    }

    private fun convertEmployer(employerDto: VacancyDetailsDto.EmployerDto?): Employer? {
        return employerDto?.let {
            Employer(
                id = it.id,
                name = it.name,
                logo = it.logo
            )
        }
    }

    private fun convertFilterArea(areaDto: FilterAreaDto?): FilterArea? {
        return areaDto?.let {
            FilterArea(
                id = it.id,
                name = it.name,
                parentId = it.parentId,
                areas = it.areas?.mapNotNull { areaDto ->
                    convertFilterArea(areaDto)
                }
            )
        }
    }

    fun mapVacancyToEntity(vacancy: Vacancy): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id,
            name = vacancy.name,
            description = vacancy.description
        )
    }

    fun mapEntityToVacancy(entity: VacancyEntity): Vacancy {
        return Vacancy(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            salary = null,
            address = null,
            experience = null,
            schedule = null,
            employment = null,
            contacts = null,
            employer = null,
            area = null,
            skills = null,
            url = null,
            industry = null,
            isFavorite = false
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
