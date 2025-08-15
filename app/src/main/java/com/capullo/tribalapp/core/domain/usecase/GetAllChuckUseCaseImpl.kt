package com.capullo.tribalapp.core.domain.usecase

import com.capullo.tribalapp.core.domain.repo.ChuckRepo
import com.capullo.tribalapp.core.domain.usecase.GetAllChuckUseCase
import javax.inject.Inject

/**
 * GetAllChuckUseCaseImpl is an implementation of the GetAllChuckUseCase interface.
 * It is responsible for fetching all categories from the API.
 * <3 Vmoreno
 */
class GetAllChuckUseCaseImpl @Inject constructor(
   val repository: ChuckRepo
) : GetAllChuckUseCase {
    /**
     * This use case is responsible for fetching all categories from the API.
     * It uses the repository to get the data and returns a list of categories.
     * <3 Vmoreno
     */
    override suspend fun getCategories(): List<String> {
        val allCategories = repository.getCategories()
        return allCategories
    }
}