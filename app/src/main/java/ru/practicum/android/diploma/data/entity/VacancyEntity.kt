package ru.practicum.android.diploma.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_vacancy_table")
data class VacancyEntity(
    @PrimaryKey
    val vacancyId: String
)
