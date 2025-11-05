package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.AppDatabase
import ru.practicum.android.diploma.data.converter.VacancyConverter
import ru.practicum.android.diploma.domain.favorites.api.repository.FavoritesRepository
import ru.practicum.android.diploma.domain.vacancydetails.models.Vacancy

class FavoritesRepositoryImpl(
    private val database: AppDatabase,
    private val converter: VacancyConverter
) : FavoritesRepository {

    override suspend fun saveInFavorites(vacancy: Vacancy) {
        database.vacancyDao().save(converter.mapVacancyToEntity(vacancy))
    }

    override suspend fun deleteFromFavorites(vacancy: Vacancy) {
        database.vacancyDao().delete(converter.mapVacancyToEntity(vacancy))
    }

    override suspend fun getFavorites(): Flow<List<Vacancy>> {
        return flow {
            emit(
                database.vacancyDao()
                    .getAll()
                    .map { vacancyEntity -> converter.mapEntityToVacancy(vacancyEntity) }
            )
        }
    }

    override suspend fun getFavoritesById(id: Int): Flow<Vacancy> {
        return flow {
            emit(converter.mapEntityToVacancy(database.vacancyDao().getById(id)))
        }
    }

    override suspend fun checkIsFavorite(id: String): Boolean {
        return database.vacancyDao().isFavorite(id)
    }
}
