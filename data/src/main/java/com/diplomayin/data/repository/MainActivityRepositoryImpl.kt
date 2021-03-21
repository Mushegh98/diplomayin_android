package com.diplomayin.data.repository

import com.diplomayin.data.dataservice.appservice.preferenceservice.PreferenceService
import com.diplomayin.data.datastore.MainActivityRepository

class MainActivityRepositoryImpl(private val preferenceService: PreferenceService) : MainActivityRepository {

    override fun getToken() : String?{
        return preferenceService.getToken()
    }
}