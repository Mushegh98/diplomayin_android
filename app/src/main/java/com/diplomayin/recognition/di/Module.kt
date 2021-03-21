package com.diplomayin.recognition.di

import com.diplomayin.recognition.activity.MainActivityViewModel
import com.diplomayin.recognition.fragment.auth.AuthScreenViewModel
import com.diplomayin.recognition.fragment.login.LoginScreenViewModel
import com.diplomayin.recognition.fragment.map.MapScreenViewModel
import com.diplomayin.recognition.fragment.register.RegisterScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MapScreenViewModel(get()) }
    viewModel { LoginScreenViewModel(get()) }
    viewModel { RegisterScreenViewModel(get()) }
    viewModel { AuthScreenViewModel(get()) }
    viewModel { MainActivityViewModel(get()) }

}