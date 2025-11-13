package ru.practicum.android.diploma.data.converter

import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.domain.filters.models.FilterIndustry
import ru.practicum.android.diploma.domain.vacancydetails.models.FilterArea

class FilterConverter {
    fun map(industries: List<FilterIndustryDto>): List<FilterIndustry> {
        return industries.map { FilterIndustry(it.id, it.name) }
    }

    fun map(area: FilterAreaDto?): FilterArea? {
        return area?.let {
            FilterArea(
                id = it.id,
                name = it.name,
                parentId = it.parentId,
                areas = it.areas?.mapNotNull { areaDto ->
                    map(areaDto)
                }
            )
        }
    }
}
