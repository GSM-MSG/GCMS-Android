package com.msg.gcms.di.module

import com.msg.gcms.data.repository.ApplicantRepositoryImpl
import com.msg.gcms.data.repository.AttendRepositoryImpl
import com.msg.gcms.data.repository.AuthRepositoryImpl
import com.msg.gcms.data.repository.ClubMemberRepositoryImpl
import com.msg.gcms.data.repository.ClubRepositoryImpl
import com.msg.gcms.data.repository.ImageRepositoryImpl
import com.msg.gcms.data.repository.NotificationRepositoryImpl
import com.msg.gcms.data.repository.UserRepositoryImpl
import com.msg.gcms.domain.repository.ApplicantRepository
import com.msg.gcms.domain.repository.AttendRepository
import com.msg.gcms.domain.repository.AuthRepository
import com.msg.gcms.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.repository.ClubRepository
import com.msg.gcms.domain.repository.ImageRepository
import com.msg.gcms.domain.repository.NotificationRepository
import com.msg.gcms.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun provideApplicantRepository(
        applicantRepositoryImpl: ApplicantRepositoryImpl
    ): ApplicantRepository

    @Binds
    abstract fun provideClubRepository(
        clubRepositoryImpl: ClubRepositoryImpl
    ): ClubRepository

    @Binds
    abstract fun provideClubMemberRepository(
        clubMemberRepositoryImpl: ClubMemberRepositoryImpl
    ): ClubMemberRepository

    @Binds
    abstract fun provideImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository

    @Binds
    abstract fun provideUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindAttendRepository(
        attendRepositoryImpl: AttendRepositoryImpl
    ): AttendRepository

    @Binds
    abstract fun bindNotification(
        notificationImpl: NotificationRepositoryImpl
    ): NotificationRepository
}
