package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.domain.api.HelloWorldRepository

//TODO 23.10.2025 класс для демонстрации работы koin. Удалить перед ПР в main
class HelloWorldRepositoryImpl : HelloWorldRepository {
    override fun getGreetings(): String {
        return "Hello World from Koin!"
    }
}
