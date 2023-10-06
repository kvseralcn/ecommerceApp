package com.pixelark.capstoneproject.core.di

import com.pixelark.capstoneproject.core.repository.StoreRepository
import com.pixelark.capstoneproject.core.repository.StoreRepositoryImpl
import com.pixelark.capstoneproject.core.service.StoreApi
import com.pixelark.capstoneproject.core.service.StoreApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideContentRepository(
        contentRepository: StoreRepositoryImpl
    ): StoreRepository

    @Binds
    abstract fun provideContentApi(
        contentApi: StoreApiImpl
    ): StoreApi
}