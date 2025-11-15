package ru.practicum.android.diploma.domain.filtersettings.models

data class FilterSettings(
    val area: Int? = null,
    val areaName: String? = null,
    val countryName: String? = null,
    val generalAreaName: String? = null,
    val industry: Int? = null,
    val industryName: String? = null,
    val salary: String? = null,
    val onlyWithSalary: Boolean? = null
)
