package ru.practicum.android.diploma.di

import android.content.Context.MODE_PRIVATE
import androidx.room.Room
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.AppDatabase
import ru.practicum.android.diploma.data.converter.VacancyConverter
import ru.practicum.android.diploma.data.dao.VacancyDao
import ru.practicum.android.diploma.data.network.api.APIService
import ru.practicum.android.diploma.data.network.api.NetworkClient
import ru.practicum.android.diploma.data.network.impl.NetworkClientImpl
import ru.practicum.android.diploma.data.network.interceptor.AuthInterceptor
import ru.practicum.android.diploma.util.NetworkProvider

private const val DATABASE_NAME = "database_db"
private const val FILTER_SETTINGS_SHARED_PREFERENCES = "filter_settings_shared_preferences"

val dataModule = module {

    factory {
        Gson()
    }

    single<AuthInterceptor> {
        AuthInterceptor()
    }

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .addNetworkInterceptor(interceptor)
            .build()
    }

    single<APIService> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }

    single {
        NetworkProvider(
            androidContext()
        )
    }

    single<NetworkClient> {
        NetworkClientImpl(get(), get())
    }

    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, DATABASE_NAME)
            .build()
    }

    single<VacancyDao> {
        get<AppDatabase>().vacancyDao()
    }

    single<VacancyConverter> {
        VacancyConverter(get())
    }

    single {
        androidApplication().getSharedPreferences(
            FILTER_SETTINGS_SHARED_PREFERENCES,
            MODE_PRIVATE
        )
    }
}
