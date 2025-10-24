package ru.practicum.android.diploma.data.network.response

import ru.practicum.android.diploma.data.dto.FilterAreaDto

data class AreasResponse(val results: List<FilterAreaDto>) : Response()
