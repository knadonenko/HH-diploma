package ru.practicum.android.diploma.domain.vacancydetails.impl

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import ru.practicum.android.diploma.domain.vacancydetails.api.interactor.VacancyDetailsLinkManagerInteractor

class VacancyDetailsLinkManagerInteractorImpl(
    private val context: Context
) : VacancyDetailsLinkManagerInteractor {
    override fun sendEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.apply {
            data = "mailto:".toUri()
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    override fun shareLink(url: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    override fun callPhone(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.apply {
            data = "tel:$phone".toUri()
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}

