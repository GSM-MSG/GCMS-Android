package com.msg.gcms.di.module

import com.msg.gcms.data.remote.datasource.applicant.ApplicantDataSourceImpl
import com.msg.gcms.data.remote.datasource.club.ClubDataSourceImpl
import com.msg.gcms.data.remote.datasource.auth.AuthDataSourceImpl
import com.msg.gcms.data.remote.datasource.club_member.ClubMemberDataSourceImpl
import com.msg.gcms.data.remote.datasource.image.ImageDataSourceImpl
import com.msg.gcms.data.remote.network.api.ClubAPI
import com.msg.gcms.data.remote.network.api.AuthAPI
import com.msg.gcms.data.remote.network.api.ImageAPI
import com.msg.gcms.data.remote.datasource.user_data.UserDataSourceImpl
import com.msg.gcms.data.remote.network.api.ApplicantAPI
import com.msg.gcms.data.remote.network.api.ClubMemberAPI
import com.msg.gcms.data.remote.network.api.UserAPI
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
    fun provideAuthDataSource(service: AuthAPI) =
        AuthDataSourceImpl(service)

    @Provides
    @Singleton
    fun provideClubDataSource(service: ClubAPI) =
        ClubDataSourceImpl(service)

    @Provides
    @Singleton
    fun provideImageDataSource(service: ImageAPI) =
        ImageDataSourceImpl(service)

    @Provides
    @Singleton
    fun provideUserDataSource(service: UserAPI) =
        UserDataSourceImpl(service)

    @Provides
    @Singleton
    fun provideApplicantSource(service: ApplicantAPI) =
        ApplicantDataSourceImpl(service)

    @Provides
    @Singleton
    fun provideClubMemberSource(service: ClubMemberAPI) =
        ClubMemberDataSourceImpl(service)
}
