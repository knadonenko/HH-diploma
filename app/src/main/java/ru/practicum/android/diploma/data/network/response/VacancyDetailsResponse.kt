package ru.practicum.android.diploma.data.network.response

import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.data.dto.VacancyDetailsDto

data class VacancyDetailsResponse(
    val id: String,
    val name: String?,
    val description: String?,
    val salary: VacancyDetailsDto.SalaryDto?,
    val address: VacancyDetailsDto.AddressDto?,
    val experience: VacancyDetailsDto.ExperienceDto?,
    val schedule: VacancyDetailsDto.ScheduleDto?,
    val employment: VacancyDetailsDto.EmploymentDto?,
    val contacts: VacancyDetailsDto.ContactsDto?,
    val employer: VacancyDetailsDto.EmployerDto?,
    val area: FilterAreaDto?,
    val skills: List<String>?,
    val url: String?,
    val industry: VacancyDetailsDto.FilterIndustryDto?
) : Response()
