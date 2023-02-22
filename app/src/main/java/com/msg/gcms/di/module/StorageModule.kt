package com.msg.gcms.di.module

import com.msg.gcms.data.local.datastorage.AuthDataStorage
import com.msg.gcms.data.local.datastorage.AuthDataStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {
    @Binds
    abstract fun provideAuthDataStorage(
        authDataStorageImpl: AuthDataStorageImpl
    ): AuthDataStorage
}