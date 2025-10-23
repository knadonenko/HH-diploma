package ru.practicum.android.diploma.presentation

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.api.HelloWorldRepository

//TODO 23.10.2025 класс для демонстрации работы koin. Удалить перед ПР в main
class HelloWorldViewModel(private val repository: HelloWorldRepository) : ViewModel() {

    fun getGreetings() : String {
        return repository.getGreetings()
    }
}
