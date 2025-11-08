package ru.practicum.android.diploma.data.converter

import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.domain.filters.models.FilterIndustry

class FilterConverter {
    fun map(industries: List<FilterIndustryDto>): List<FilterIndustry> {
        return industries.map { FilterIndustry(it.id, it.name) }
    }
}
