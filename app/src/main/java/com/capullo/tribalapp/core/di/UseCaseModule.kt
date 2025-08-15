package com.capullo.tribalapp.core.di

import com.capullo.tribalapp.core.domain.usecase.GetAllChuckUseCaseImpl
import com.capullo.tribalapp.core.domain.usecase.GetAllChuckUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/* This module provides the use case for getting all Chuck categories.
    * It binds the implementation of the use case to the interface.
    * The use case is scoped to the ViewModel lifecycle, meaning it will be created
    * when the ViewModel is created and destroyed when the ViewModel is cleared.
    * <3 Vmoreno
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGetAllChuckUseCase(
        impl: GetAllChuckUseCaseImpl
    ): GetAllChuckUseCase
}