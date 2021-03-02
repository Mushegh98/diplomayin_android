package com.diplomayin.data.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.diplomayin.data.dataservice.RetrofitService
import com.diplomayin.data.dataservice.appservice.preferenceservice.PreferenceService
import com.diplomayin.data.dataservice.appservice.preferenceservice.PreferenceServiceImpl
import com.diplomayin.data.datastore.LoginScreenRepository
import com.diplomayin.data.datastore.MapScreenRepository
import com.diplomayin.data.datastore.RegisterScreenRepository
import com.diplomayin.data.repository.LoginScreenRepositoryImpl
import com.diplomayin.data.repository.MapScreenRepositoryImpl
import com.diplomayin.data.repository.RegisterScreenRepositoryImpl
import com.diplomayin.data.utils.Constants.Companion.BASE_URL
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {
    single { Moshi.Builder().build() }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
//               .addConverterFactory(MoshiConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .apply {
                client(
                    OkHttpClient.Builder()
                        .addInterceptor { chain ->
                            val request = chain.request()
                            val response = chain.proceed(request)
                            response
                        }
                        .addNetworkInterceptor(StethoInterceptor())
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                        .build()
                )
            }
            .build()
    }

    single<RetrofitService> { get<Retrofit>().create(RetrofitService::class.java) }

}
val databaseModule = module {
//    fun provideDatabase(application: Application): AppDatabase {
//        return Room.databaseBuilder(application, AppDatabase::class.java, "ParkingDB")
////                 .fallbackToDestructiveMigration()
//            .allowMainThreadQueries()
//            .build()
//    }
//    single { provideDatabase(androidApplication()) }
//    single { get<AppDatabase>().roomDao() }
//    single { get<AppDatabase>().readoutDAO() }
//    single { get<AppDatabase>().vegaDao() }
}

val repositoryModule = module {

    /**Services**/
    single<PreferenceService> { PreferenceServiceImpl(get()) }

    /**Repositorys**/
    single<LoginScreenRepository> { LoginScreenRepositoryImpl() }
    single<RegisterScreenRepository> { RegisterScreenRepositoryImpl(get()) }
    single<MapScreenRepository> { MapScreenRepositoryImpl() }

}