package com.diplomayin.domain.usecase

import com.diplomayin.data.datastore.MainActivityRepository
import com.diplomayin.domain.interactor.MainActivityInteractor

class MainActivityUseCase(private val mainActivityRepository: MainActivityRepository) : MainActivityInteractor {
    override fun getToken(): String? {
        return mainActivityRepository.getToken()
    }
}