package com.diplomayin.recognition.base

import android.app.Application
import android.util.Log
import com.diplomayin.data.di.apiModule
import com.diplomayin.data.di.databaseModule
import com.diplomayin.data.di.repositoryModule
import com.diplomayin.domain.di.interactorModule
import com.diplomayin.recognition.di.viewModelModule
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.io.EOFException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLException


class App : Application() {
    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler { error ->
            Log.d("LogAY", "***** UndeliverableException ${error.message} ${error.cause} *****")
            if (error is UndeliverableException) {
                Log.d("LogAY", "***** UndeliverableException ${error.message} ${error.cause} *****")
                when (error.cause) {
                    is SSLException -> return@setErrorHandler
                    is SocketTimeoutException -> return@setErrorHandler
                    is ConnectException -> return@setErrorHandler
                    is SocketException -> return@setErrorHandler
                    is EOFException -> return@setErrorHandler
                    else -> throw error
                }
            }
            throw error
        }
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(modules)
        }
    }

    private val modules = listOf(
        viewModelModule,
        databaseModule,
        apiModule,
        repositoryModule,
        interactorModule
    )
}