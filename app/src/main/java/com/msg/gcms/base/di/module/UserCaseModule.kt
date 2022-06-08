package com.msg.gcms.base.di.module

import com.msg.gcms.domain.repository.ClubRepository
import com.msg.gcms.domain.repository.CommonRepository
import com.msg.gcms.domain.repository.UserRepository
import com.msg.gcms.domain.usecase.club.ClubUseCase
import com.msg.gcms.domain.usecase.club.GetDetailUseCase
import com.msg.gcms.domain.usecase.common.LoginUseCase
import com.msg.gcms.domain.usecase.common.LogoutUseCase
import com.msg.gcms.domain.usecase.common.RegistrationUseCase
import com.msg.gcms.domain.usecase.user.ProfileUseCase
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

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: CommonRepository): LoginUseCase = LoginUseCase(repository)

    @Provides
    @Singleton
    fun provideGetDetailUseCase(repository: ClubRepository): GetDetailUseCase = GetDetailUseCase(repository)

    @Provides
    @Singleton
    fun provideClubUseCase(repository: ClubRepository): ClubUseCase = ClubUseCase(repository)

    @Provides
    @Singleton
    fun provideLogoutUseCase(repository: CommonRepository): LogoutUseCase = LogoutUseCase(repository)

    @Provides
    @Singleton
    fun provideProfileUseCase(repository: UserRepository): ProfileUseCase = ProfileUseCase(repository)

}
