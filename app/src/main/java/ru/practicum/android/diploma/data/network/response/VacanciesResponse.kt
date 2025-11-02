package ru.practicum.android.diploma.data.network.response

import ru.practicum.android.diploma.data.dto.VacancyDetailsDto

data class VacanciesResponse(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<VacancyDetailsDto>
) : Response()
