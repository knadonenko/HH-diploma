package ru.practicum.android.diploma.domain.favorites.api.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.vacancydetails.models.Vacancy

interface FavoritesRepository {

    suspend fun saveInFavorites(vacancy: Vacancy)

    suspend fun deleteFromFavorites(vacancy: Vacancy)

    suspend fun getFavorites(): Flow<List<Vacancy>>

    suspend fun getFavoritesById(id: Int): Flow<Vacancy>

    suspend fun checkIsFavorite(id: String): Boolean

}
