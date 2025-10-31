package ru.practicum.android.diploma.data.network.response

import ru.practicum.android.diploma.data.dto.VacancyDetailDto

data class VacanciesResponse(val found: Int, val pages: Int, val page: Int, val items: List<VacancyDetailDto>) : Response()
