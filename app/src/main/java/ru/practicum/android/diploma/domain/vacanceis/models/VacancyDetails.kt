package ru.practicum.android.diploma.domain.vacanceis.models

data class VacancyDetails(
    val id: String,
    val vacancyName: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrencySymbol: String?,
    val employerName: String,
    val employerPhone: List<EmployerPhone>,
    val employerEmail: String?,
    val schedule: String?,
    val address: String?,
    val areaName: String,
    val experience: String?,
    val description: String,
    val skills: List<String>
) {
    data class EmployerPhone(val phone: String, val comment: String? = null)
}

