package ru.practicum.android.diploma.domain.vacancydetails.api.interactor

interface VacancyDetailsLinkManagerInteractor {
    fun sendEmail(email: String)

    fun shareLink(url: String)

    fun callPhone(phone: String)
}
