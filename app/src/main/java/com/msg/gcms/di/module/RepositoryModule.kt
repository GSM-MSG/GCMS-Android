package com.msg.gcms.di.module

import com.msg.gcms.data.remote.datasource.ClubDataSourceImpl
import com.msg.gcms.data.remote.datasource.AuthDataSourceImpl
import com.msg.gcms.data.remote.datasource.ImageDataSourceImpl
import com.msg.gcms.data.remote.datasource.UserDataSourceImpl
import com.msg.gcms.data.repository.ClubRepositoryImpl
import com.msg.gcms.data.repository.AuthRepositoryImpl
import com.msg.gcms.data.repository.ImageRepositoryImpl
import com.msg.gcms.data.repository.UserRepositoryImpl
import com.msg.gcms.domain.repository.ClubRepository
import com.msg.gcms.domain.repository.AuthRepository
import com.msg.gcms.domain.repository.ImageRepository
import com.msg.gcms.domain.repository.UserRepository
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
    fun provideAuthRepository(dataSource: AuthDataSourceImpl): AuthRepository =
        AuthRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideClubRepository(dataSource: ClubDataSourceImpl): ClubRepository =
        ClubRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideImageRepository(dataSource: ImageDataSourceImpl): ImageRepository =
        ImageRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideUserRepository(dataSource: UserDataSourceImpl): UserRepository =
        UserRepositoryImpl(dataSource)
}
