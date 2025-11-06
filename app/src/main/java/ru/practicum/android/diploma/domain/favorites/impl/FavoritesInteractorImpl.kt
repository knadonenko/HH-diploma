package ru.practicum.android.diploma.domain.favorites.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.favorites.api.interactor.FavoritesInteractor
import ru.practicum.android.diploma.domain.favorites.api.repository.FavoritesRepository
import ru.practicum.android.diploma.domain.vacancydetails.models.Vacancy

class FavoritesInteractorImpl(private val repository: FavoritesRepository) : FavoritesInteractor {

    override suspend fun saveInFavorites(vacancy: Vacancy) = repository.saveInFavorites(vacancy)

    override suspend fun deleteFromFavorites(vacancy: Vacancy) = repository.deleteFromFavorites(vacancy)

    override suspend fun getFavorites(): Flow<List<Vacancy>> = repository.getFavorites()

    override suspend fun getFavoritesById(id: Int): Flow<Vacancy> = repository.getFavoritesById(id)

    override suspend fun checkIsFavorite(id: String): Boolean = repository.checkIsFavorite(id)
}
