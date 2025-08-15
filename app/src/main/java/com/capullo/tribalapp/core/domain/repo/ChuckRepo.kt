package com.capullo.tribalapp.core.domain.repo

interface ChuckRepo {

    /**
     * This function is suspendable, meaning it can be
     * called from a coroutine or another suspend function.
     * <3 Vmoreno
     */
    suspend fun getCategories(): List<String>
}