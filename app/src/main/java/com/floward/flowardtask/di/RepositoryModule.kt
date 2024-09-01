package com.floward.flowardtask.di

import com.floward.flowardtask.data.repository.RepositoryImpl
import com.floward.flowardtask.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRepository(
        repositoryImpl: RepositoryImpl
    ): Repository


}