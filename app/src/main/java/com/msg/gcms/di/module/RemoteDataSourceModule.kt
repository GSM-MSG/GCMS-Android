package com.msg.gcms.di.module

import com.msg.gcms.data.remote.datasource.applicant.ApplicantDataSource
import com.msg.gcms.data.remote.datasource.applicant.ApplicantDataSourceImpl
import com.msg.gcms.data.remote.datasource.attend.AttendDataSource
import com.msg.gcms.data.remote.datasource.attend.AttendDataSourceImpl
import com.msg.gcms.data.remote.datasource.auth.AuthDataSource
import com.msg.gcms.data.remote.datasource.auth.AuthDataSourceImpl
import com.msg.gcms.data.remote.datasource.club.ClubDataSource
import com.msg.gcms.data.remote.datasource.club.ClubDataSourceImpl
import com.msg.gcms.data.remote.datasource.club_member.ClubMemberDataSource
import com.msg.gcms.data.remote.datasource.club_member.ClubMemberDataSourceImpl
import com.msg.gcms.data.remote.datasource.image.ImageDataSource
import com.msg.gcms.data.remote.datasource.image.ImageDataSourceImpl
import com.msg.gcms.data.remote.datasource.user.UserDataSource
import com.msg.gcms.data.remote.datasource.user.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun provideAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl
    ): AuthDataSource

    @Binds
    abstract fun provideClubDataSource(
        clubDataSourceImpl: ClubDataSourceImpl
    ): ClubDataSource

    @Binds
    abstract fun provideImageDataSource(
        imageDataSourceImpl: ImageDataSourceImpl
    ): ImageDataSource

    @Binds
    abstract fun provideUserDataSource(
        userDataSourceImpl: UserDataSourceImpl
    ): UserDataSource

    @Binds
    abstract fun provideApplicantDataSource(
        applicantDataSourceImpl: ApplicantDataSourceImpl
    ): ApplicantDataSource

    @Binds
    abstract fun provideClubMemberDataSource(
        clubMemberDataSourceImpl: ClubMemberDataSourceImpl
    ): ClubMemberDataSource

    @Binds
    abstract fun bindAttendDataSource(
        attendDataSourceImpl: AttendDataSourceImpl
    ): AttendDataSource
}
