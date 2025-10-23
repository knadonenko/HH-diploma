package ru.practicum.android.diploma.data.network.response

import ru.practicum.android.diploma.data.dto.FilterIndustryDto

data class IndustriesResponse(val results: List<FilterIndustryDto>): Response()
