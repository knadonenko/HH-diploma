package ru.practicum.android.diploma.domain.vacanceis.models

data class VacanciesSearchSettings(
    val area: Int? = null,
    val industry: Int? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean? = null
)
