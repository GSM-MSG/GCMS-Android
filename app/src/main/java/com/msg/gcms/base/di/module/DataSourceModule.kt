package com.msg.gcms.base.di.module

import com.msg.gcms.data.remote.datasource.CommonDataSourceImpl
import com.msg.gcms.data.remote.network.CommonAPI
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
    fun provideCommonDataSource(service : CommonAPI) =
        CommonDataSourceImpl(service)
}