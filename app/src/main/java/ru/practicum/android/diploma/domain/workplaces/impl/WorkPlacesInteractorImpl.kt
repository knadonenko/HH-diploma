package ru.practicum.android.diploma.domain.workplaces.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.workplaces.api.interactor.WorkPlacesInteractor
import ru.practicum.android.diploma.domain.workplaces.api.repository.WorkPlacesRepository
import ru.practicum.android.diploma.domain.workplaces.models.WorkPlacesResponseState

class WorkPlacesInteractorImpl(private val repository: WorkPlacesRepository) : WorkPlacesInteractor {
    override fun getAreas(): Flow<WorkPlacesResponseState> {
        return repository.getAreas()
    }
}
