package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.domain.api.HelloWorldRepository

// todo класс для демонстрации работы koin. Удалить перед ПР в main
class HelloWorldRepositoryImpl : HelloWorldRepository {
    override fun getGreetings(): String {
        return "Hello World from Koin!"
    }
}
