package com.msg.gcms.base.di.module

import com.msg.gcms.data.remote.datasource.CommonDataSourceImpl
import com.msg.gcms.data.repository.CommonRepositoryImpl
import com.msg.gcms.domain.repository.CommonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCommonRepository(dataSource: CommonDataSourceImpl): CommonRepository =
        CommonRepositoryImpl(dataSource)
}
