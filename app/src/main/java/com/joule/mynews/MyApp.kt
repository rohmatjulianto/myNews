package com.joule.mynews

import android.app.Application
import com.joule.mynews.data.NewsDataSource
import com.joule.mynews.data.NewsRepository
import com.joule.mynews.data.NewsRepositoryImpl
import com.joule.mynews.remote.AuthInterceptor
import com.joule.mynews.remote.NewsService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(module)
            modules(createRemoteModule("https://newsapi.org/"))
        }
    }

    val module = module {
        factory { NewsDataSource(get()) as NewsDataSource }
        factory { NewsRepositoryImpl(get()) as NewsRepository }
        viewModel { MainViewModel(get()) }
    }

    fun createRemoteModule(baseUrl: String) = module {
        factory<Interceptor> { AuthInterceptor() }
        factory { OkHttpClient.Builder().addInterceptor(get()).build() }

        single {
            Retrofit.Builder()
                .client(get())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        factory{ get<Retrofit>().create(NewsService::class.java) }
    }

}


