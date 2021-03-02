package com.diplomayin.domain.usecase

import com.diplomayin.data.datastore.MapScreenRepository
import com.diplomayin.domain.interactor.MapScreenInteractor

class MapScreenUseCase(private val mapScreenRepository: MapScreenRepository) : MapScreenInteractor {
}