package ru.practicum.android.diploma.data.dto

data class VacancyDetailDto(
    val id: String,
    val name: String,
    val salary: SalaryDto,
    val address: AddressDto?,
    val experience: ExperienceDto?,
    val schedule: ScheduleDto?,
    val employment: EmploymentDto?,
    val contacts: ContactsDto?,
    val description: String,
    val employer: EmployerDto,
    val area: FilterAreaDto,
    val skills: List<String>,
    val url: String,
    val industry: FilterIndustryDto
) {
    data class SalaryDto(
        val id: String,
        val currency: String?,
        val from: Int?,
        val to: Int?
    )

    data class AddressDto(
        val id: String,
        val city: String,
        val street: String,
        val building: String,
        val raw: String
    )

    data class ExperienceDto(
        val id: String,
        val name: String
    )

    data class ScheduleDto(
        val id: String,
        val name: String
    )

    data class EmploymentDto(
        val id: String,
        val name: String
    )

    data class ContactsDto(
        val id: String,
        val name: String,
        val email: String,
        val phone: List<Phone>
    )

    data class Phone(
        val comment: Any?,
        val formatted: String,
    )

    data class EmployerDto(
        val id: String,
        val name: String,
        val logo: String
    )

    data class FilterIndustryDto(
        val id: String,
        val name: String
    )
}
