package com.diplomayin.domain.di

import com.diplomayin.domain.interactor.*
import com.diplomayin.domain.usecase.*
import org.koin.dsl.module

val interactorModule = module {

    single<MapScreenInteractor> { MapScreenUseCase(get()) }
    single<LoginScreenInteractor> { LoginScreenUseCase(get()) }
    single<RegisterScreenInteractor> { RegisterScreenUseCase(get()) }
    single<AuthScreenInteractor> { AuthScreenUseCase(get()) }
    single<MainActivityInteractor> { MainActivityUseCase(get()) }
}