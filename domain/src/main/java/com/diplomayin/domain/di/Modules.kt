package com.diplomayin.domain.di

import com.diplomayin.domain.interactor.LoginScreenInteractor
import com.diplomayin.domain.interactor.MapScreenInteractor
import com.diplomayin.domain.interactor.RegisterScreenInteractor
import com.diplomayin.domain.usecase.LoginScreenUseCase
import com.diplomayin.domain.usecase.MapScreenUseCase
import com.diplomayin.domain.usecase.RegisterScreenUseCase
import org.koin.dsl.module

val interactorModule = module {

    single<MapScreenInteractor> { MapScreenUseCase(get()) }
    single<LoginScreenInteractor> { LoginScreenUseCase(get()) }
    single<RegisterScreenInteractor> { RegisterScreenUseCase(get()) }
}