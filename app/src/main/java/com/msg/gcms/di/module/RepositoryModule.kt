package com.msg.gcms.di.module

import com.msg.gcms.data.local.datasource.LocalDataSourceImpl
import com.msg.gcms.data.remote.datasource.applicant.ApplicantDataSourceImpl
import com.msg.gcms.data.remote.datasource.auth.AuthDataSourceImpl
import com.msg.gcms.data.remote.datasource.club.ClubDataSourceImpl
import com.msg.gcms.data.remote.datasource.club_member.ClubMemberDataSourceImpl
import com.msg.gcms.data.remote.datasource.image.ImageDataSourceImpl
import com.msg.gcms.data.remote.datasource.user_data.UserDataSourceImpl
import com.msg.gcms.data.repository.ApplicantRepositoryImpl
import com.msg.gcms.data.repository.AuthRepositoryImpl
import com.msg.gcms.data.repository.ClubMemberRepositoryImpl
import com.msg.gcms.data.repository.ClubRepositoryImpl
import com.msg.gcms.data.repository.ImageRepositoryImpl
import com.msg.gcms.data.repository.UserRepositoryImpl
import com.msg.gcms.domain.repository.ApplicantRepository
import com.msg.gcms.domain.repository.AuthRepository
import com.msg.gcms.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.repository.ClubRepository
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
    fun provideAuthRepository(
        remoteDataSource: AuthDataSourceImpl,
        localDataSource: LocalDataSourceImpl
    ): AuthRepository =
        AuthRepositoryImpl(remoteDataSource, localDataSource)

    @Provides
    @Singleton
    fun provideApplicantRepository(dataSource: ApplicantDataSourceImpl): ApplicantRepository =
        ApplicantRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideClubRepository(dataSource: ClubDataSourceImpl): ClubRepository =
        ClubRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideClubMemberRepository(dataSource: ClubMemberDataSourceImpl): ClubMemberRepository =
        ClubMemberRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideImageRepository(dataSource: ImageDataSourceImpl): ImageRepository =
        ImageRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideUserRepository(dataSource: UserDataSourceImpl): UserRepository =
        UserRepositoryImpl(dataSource)
}
