package com.msg.gcms.base.di.module

import com.msg.gcms.data.remote.datasource.ClubDataSourceImpl
import com.msg.gcms.data.remote.datasource.CommonDataSourceImpl
import com.msg.gcms.data.remote.datasource.ImageDataSourceImpl
import com.msg.gcms.data.remote.network.ClubAPI
import com.msg.gcms.data.remote.network.CommonAPI
import com.msg.gcms.data.remote.network.ImageAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideCommonDataSource(service: CommonAPI) =
        CommonDataSourceImpl(service)

    @Provides
    @Singleton
    fun provideClubDataSource(service: ClubAPI) =
        ClubDataSourceImpl(service)

    @Provides
    @Singleton
    fun provideImageDataSource(service: ImageAPI) =
        ImageDataSourceImpl(service)
}
