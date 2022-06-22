package com.msg.gcms.base.di.module

import com.msg.gcms.domain.repository.CommonRepository
import com.msg.gcms.domain.repository.ImageRepository
import com.msg.gcms.domain.repository.UserRepository
import com.msg.gcms.domain.usecase.common.RegistrationUseCase
import com.msg.gcms.domain.usecase.image.ImageUseCase
import com.msg.gcms.domain.usecase.user.UserUseCase
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
    fun provideUserUseCase(repository: UserRepository): UserUseCase = UserUseCase(repository)

    @Provides
    @Singleton
    fun provideImageUseCase(repository: ImageRepository): ImageUseCase = ImageUseCase(repository)
}
