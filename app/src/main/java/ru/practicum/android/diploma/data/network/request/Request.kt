package ru.practicum.android.diploma.data.network.request

sealed class Request {
    class AreasRequest : Request()
    class IndustriesRequest : Request()
    class VacanciesRequest(val options: HashMap<String, String>) : Request()
    class VacancyRequest(val vacancyId: String) : Request()
}
