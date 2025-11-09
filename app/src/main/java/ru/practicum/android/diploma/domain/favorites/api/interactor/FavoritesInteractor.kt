package ru.practicum.android.diploma.domain.favorites.api.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.vacancydetails.models.Vacancy

interface FavoritesInteractor {

    suspend fun saveInFavorites(vacancy: Vacancy)

    suspend fun deleteFromFavorites(vacancy: Vacancy)

    suspend fun getFavorites(): Flow<List<Vacancy>>

    suspend fun getFavoritesById(id: String): Flow<Vacancy?>

    suspend fun checkIsFavorite(id: String): Boolean

}
