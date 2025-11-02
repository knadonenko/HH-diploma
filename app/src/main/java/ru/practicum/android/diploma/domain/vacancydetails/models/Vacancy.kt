package ru.practicum.android.diploma.domain.vacancydetails.models

import androidx.compose.runtime.Immutable

@Immutable
data class Vacancy(
    val id: String,
    val name: String?,
    val description: String?,
    val salary: Salary?,
    val address: Address?,
    val experience: String?,
    val schedule: String?,
    val employment: String?,
    val contacts: Contacts?,
    val employer: Employer?,
    val area: FilterArea?,
    val skills: List<String>?,
    val url: String?,
    val industry: String?,
    var isFavorite: Boolean
)
