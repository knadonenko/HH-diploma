package ru.practicum.android.diploma.domain.models

import androidx.compose.runtime.Immutable

// todo замените на domain модель из задачи #25
@Immutable
data class Vacancy(
    val image: String?,
    val vacancyName: String,
    val cityName: String,
    val companyName: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrencySymbol: String?
)
