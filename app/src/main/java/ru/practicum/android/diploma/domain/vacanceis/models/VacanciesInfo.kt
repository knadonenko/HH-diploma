package ru.practicum.android.diploma.domain.vacanceis.models

data class VacanciesInfo(
    val id: String,
    val name: String,
    val city: String?,
    val employerName: String,
    val employerLogo: String?,
    val salaryRow: String?
)
