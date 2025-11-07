package ru.practicum.android.diploma.data.network.request

sealed class Request {
    data object AreasRequest : Request()
    class IndustriesRequest : Request()
    class VacanciesRequest(val options: HashMap<String, String>) : Request()
    class VacancyDetailsRequest(val vacancyId: String) : Request()
}
