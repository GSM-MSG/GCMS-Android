package com.msg.gcms.di.module

import com.msg.gcms.data.local.datasource.LocalDataSource
import com.msg.gcms.data.local.datasource.LocalDataSourceImpl
import com.msg.gcms.data.local.datasource.club.ClubLocalDataSource
import com.msg.gcms.data.local.datasource.club.ClubLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun provideLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource

    @Binds
    abstract fun provideClubLocalDataSource(
        clubLocalDataSourceImpl: ClubLocalDataSourceImpl
    ): ClubLocalDataSource
}