package ru.practicum.android.diploma.domain.workplaces.api.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.workplaces.models.WorkPlacesResponseState

interface WorkPlacesInteractor {
    fun getAreas(): Flow<WorkPlacesResponseState>
}
