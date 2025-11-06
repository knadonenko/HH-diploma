package ru.practicum.android.diploma.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.data.entity.embedded.ContactsEmbedded
import ru.practicum.android.diploma.data.entity.embedded.FilterAreaEmbedded
import ru.practicum.android.diploma.domain.vacancydetails.models.Address
import ru.practicum.android.diploma.domain.vacancydetails.models.Employer
import ru.practicum.android.diploma.domain.vacancydetails.models.Salary

@Entity(tableName = "favourite_vacancy_table")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String?,
    val description: String?,
    @Embedded
    val salary: Salary?,
    @Embedded
    val address: Address?,
    val experience: String?,
    val schedule: String?,
    val employment: String?,
    @Embedded(prefix = "contacts_")
    val contacts: ContactsEmbedded?,
    @Embedded(prefix = "employer_")
    val employer: Employer?,
    @Embedded(prefix = "area_")
    val area: FilterAreaEmbedded?,
    val skills: String?,
    val url: String?,
    val industry: String?,
    val isFavorite: Boolean
)
