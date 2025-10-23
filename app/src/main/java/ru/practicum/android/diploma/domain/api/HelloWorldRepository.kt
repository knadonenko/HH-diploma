package ru.practicum.android.diploma.domain.api

// todo 23.10.2025 класс для демонстрации работы koin. Удалить перед ПР в main
interface HelloWorldRepository {
    fun getGreetings() : String
}
