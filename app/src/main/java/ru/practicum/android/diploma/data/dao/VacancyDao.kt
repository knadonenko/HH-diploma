package ru.practicum.android.diploma.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import ru.practicum.android.diploma.data.entity.VacancyEntity

@Dao
interface VacancyDao {
    @Upsert(entity = VacancyEntity::class)
    suspend fun save(vacancy: VacancyEntity)

    @Delete(entity = VacancyEntity::class)
    suspend fun delete(vacancy: VacancyEntity)

    @Query("SELECT * FROM favourite_vacancy_table")
    suspend fun getAll(): List<VacancyEntity>

    @Query("SELECT * FROM favourite_vacancy_table WHERE id = :id")
    suspend fun getById(id: String): VacancyEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_vacancy_table WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean
}
