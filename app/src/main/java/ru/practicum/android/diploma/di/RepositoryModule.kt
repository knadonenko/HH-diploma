package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.HelloWorldRepositoryImpl
import ru.practicum.android.diploma.domain.api.HelloWorldRepository

/**
 * Модуль для описания компонентов-репозиториев
 */
val repositoryModule = module {

    factory<HelloWorldRepository> {
        HelloWorldRepositoryImpl()
    }
}
