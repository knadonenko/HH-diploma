package ru.practicum.android.diploma.domain.filtersettings.models

data class FilterSettings(
    val area: Int? = null,
    val industry: Int? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean? = null
)
