package ru.practicum.android.diploma.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.dao.VacancyDao
import ru.practicum.android.diploma.data.entity.VacancyEntity

@Database(version = 1,
    entities = [VacancyEntity::class],
    exportSchema = true)

abstract class AppDatabase : RoomDatabase(){
    abstract fun vacancyDao(): VacancyDao
}
