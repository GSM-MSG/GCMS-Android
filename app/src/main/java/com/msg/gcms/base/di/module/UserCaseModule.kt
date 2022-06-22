package com.msg.gcms.base.di.module

import com.msg.gcms.domain.repository.CommonRepository
import com.msg.gcms.domain.usecase.common.RegistrationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserCaseModule {

    @Provides
    @Singleton
    fun provideRegistrationUseCase(repository: CommonRepository): RegistrationUseCase = RegistrationUseCase(repository)
}
