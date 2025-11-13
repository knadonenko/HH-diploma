package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converter.VacancyConverter
import ru.practicum.android.diploma.data.dao.VacancyDao
import ru.practicum.android.diploma.domain.favorites.api.repository.FavoritesRepository
import ru.practicum.android.diploma.domain.vacancydetails.models.Vacancy

class FavoritesRepositoryImpl(
    private val dao: VacancyDao,
    private val converter: VacancyConverter
) : FavoritesRepository {

    override suspend fun saveInFavorites(vacancy: Vacancy) {
        dao.save(converter.mapVacancyToEntity(vacancy))
    }

    override suspend fun deleteFromFavorites(vacancy: Vacancy) {
        dao.delete(converter.mapVacancyToEntity(vacancy))
    }

    override fun getFavorites(): Flow<List<Vacancy>> {
        return flow {
            emit(
                dao.getAll().map { vacancyEntity -> converter.mapEntityToVacancy(vacancyEntity) }
            )
        }
    }

    override fun getFavoritesById(id: String): Flow<Vacancy?> {
        return flow {
            val vacancy = dao.getById(id)
            emit(vacancy?.let { converter.mapEntityToVacancy(it) })
        }
    }

    override suspend fun checkIsFavorite(id: String): Boolean {
        return dao.isFavorite(id)
    }
}
