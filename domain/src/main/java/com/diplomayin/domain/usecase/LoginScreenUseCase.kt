package com.diplomayin.domain.usecase

import com.diplomayin.data.datastore.LoginScreenRepository
import com.diplomayin.domain.interactor.LoginScreenInteractor

class LoginScreenUseCase(private val loginScreenRepository: LoginScreenRepository) :
    LoginScreenInteractor {


}