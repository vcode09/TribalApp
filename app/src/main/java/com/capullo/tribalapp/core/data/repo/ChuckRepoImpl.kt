package com.capullo.tribalapp.core.data.repo

import com.capullo.tribalapp.core.data.remote.ChuckNorrisRequestApiInt
import com.capullo.tribalapp.core.domain.repo.ChuckRepo
import javax.inject.Inject


class ChuckRepoImpl @Inject constructor(
    private val api: ChuckNorrisRequestApiInt
) : ChuckRepo {
    /**
     * Implementation of the ChuckRepo interface that interacts
     * with the ChuckNorrisRequestApiInt API.
     * This class is responsible for fetching categories from the API.
     * <3 Vmoreno
     */
    override suspend fun getCategories():
            List<String> = api.getCategories()

}