package ru.practicum.android.diploma.domain.workplaces.api.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.workplaces.models.WorkPlacesResponseState

interface WorkPlacesRepository {
    fun getAreas(): Flow<WorkPlacesResponseState>
}
