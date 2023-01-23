package com.msg.gcms.base.di.module

import com.msg.gcms.domain.repository.ClubRepository
import com.msg.gcms.domain.repository.CommonRepository
import com.msg.gcms.domain.repository.ImageRepository
import com.msg.gcms.domain.repository.UserRepository
import com.msg.gcms.domain.usecase.club.ApplicantAcceptUseCase
import com.msg.gcms.domain.usecase.club.ApplicantRejectUseCase
import com.msg.gcms.domain.usecase.club.ClubDeleteUseCase
import com.msg.gcms.domain.usecase.club.EditClubInfoUseCase
import com.msg.gcms.domain.usecase.club.GetApplicantUseCase
import com.msg.gcms.domain.usecase.club.GetClubListUseCase
import com.msg.gcms.domain.usecase.club.GetDetailUseCase
import com.msg.gcms.domain.usecase.club.GetMemberUseCase
import com.msg.gcms.domain.usecase.club.MandateUseCase
import com.msg.gcms.domain.usecase.club.PostClubApplyUseCase
import com.msg.gcms.domain.usecase.club.PostClubCancelUseCase
import com.msg.gcms.domain.usecase.club.PostCreateClubUseCase
import com.msg.gcms.domain.usecase.club.PutClubCloseUseCase
import com.msg.gcms.domain.usecase.club.PutClubOpenUseCase
import com.msg.gcms.domain.usecase.club.UserKickUseCase
import com.msg.gcms.domain.usecase.common.LogoutUseCase
import com.msg.gcms.domain.usecase.common.RefreshUseCase
import com.msg.gcms.domain.usecase.common.RegistrationUseCase
import com.msg.gcms.domain.usecase.image.ImageUseCase
import com.msg.gcms.domain.usecase.user.DeleteUserUseCase
import com.msg.gcms.domain.usecase.user.EditProfileUseCase
import com.msg.gcms.domain.usecase.user.ExitUseCase
import com.msg.gcms.domain.usecase.user.GetSearchUserUseCase
import com.msg.gcms.domain.usecase.user.GetUserInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    // --- Club UseCase ---
    @Provides
    @Singleton
    fun provideApplicantAcceptUseCase(repository: ClubRepository): ApplicantAcceptUseCase =
        ApplicantAcceptUseCase(repository)

    @Provides
    @Singleton
    fun provideApplicantRejectUseCase(repository: ClubRepository): ApplicantRejectUseCase =
        ApplicantRejectUseCase(repository)

    @Provides
    @Singleton
    fun provideClubDeleteUseCase(repository: ClubRepository): ClubDeleteUseCase =
        ClubDeleteUseCase(repository)

    @Provides
    @Singleton
    fun provideEditClubInfoUseCase(repository: ClubRepository): EditClubInfoUseCase =
        EditClubInfoUseCase(repository)

    @Provides
    @Singleton
    fun provideGetApplicantUseCase(repository: ClubRepository): GetApplicantUseCase =
        GetApplicantUseCase(repository)

    @Provides
    @Singleton
    fun provideGetClubListUseCase(repository: ClubRepository): GetClubListUseCase =
        GetClubListUseCase(repository)

    @Provides
    @Singleton
    fun provideGetDetailUseCase(repository: ClubRepository): GetDetailUseCase =
        GetDetailUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMemberUseCase(repository: ClubRepository): GetMemberUseCase =
        GetMemberUseCase(repository)

    @Provides
    @Singleton
    fun provideMandateUseCase(repository: ClubRepository): MandateUseCase =
        MandateUseCase(repository)

    @Provides
    @Singleton
    fun providePostClubApplyUseCase(repository: ClubRepository): PostClubApplyUseCase =
        PostClubApplyUseCase(repository)

    @Provides
    @Singleton
    fun providePostClubCancelUseCase(repository: ClubRepository): PostClubCancelUseCase =
        PostClubCancelUseCase(repository)

    @Provides
    @Singleton
    fun providePostCreateClubUseCase(repository: ClubRepository): PostCreateClubUseCase =
        PostCreateClubUseCase(repository)

    @Provides
    @Singleton
    fun providePutCloseUseCase(repository: ClubRepository): PutClubCloseUseCase =
        PutClubCloseUseCase(repository)

    @Provides
    @Singleton
    fun providePutClubOpenUseCase(repository: ClubRepository): PutClubOpenUseCase =
        PutClubOpenUseCase(repository)

    @Provides
    @Singleton
    fun provideUserKickUseCase(repository: ClubRepository): UserKickUseCase =
        UserKickUseCase(repository)


    // --- Common UseCase ---

    @Provides
    @Singleton
    fun provideLogoutUseCase(repository: CommonRepository): LogoutUseCase =
        LogoutUseCase(repository)

    @Provides
    @Singleton
    fun provideRefreshUseCase(repository: CommonRepository): RefreshUseCase =
        RefreshUseCase(repository)

    @Provides
    @Singleton
    fun provideRegistrationUseCase(repository: CommonRepository): RegistrationUseCase =
        RegistrationUseCase(repository)


    // --- Image UseCase ---

    @Provides
    @Singleton
    fun provideImageUseCase(repository: ImageRepository): ImageUseCase =
        ImageUseCase(repository)


    // --- User UseCase ----

    @Provides
    @Singleton
    fun provideDeleteUserUseCase(repository: UserRepository): DeleteUserUseCase =
        DeleteUserUseCase(repository)

    @Provides
    @Singleton
    fun provideEditProfileUseCase(repository: UserRepository): EditProfileUseCase =
        EditProfileUseCase(repository)

    @Provides
    @Singleton
    fun provideExitUseCase(repository: UserRepository): ExitUseCase =
        ExitUseCase(repository)

    @Provides
    @Singleton
    fun provideGetSearchUserUseCase(repository: UserRepository): GetSearchUserUseCase =
        GetSearchUserUseCase(repository)

    @Provides
    @Singleton
    fun provideGetUserInfoUseCase(repository: UserRepository): GetUserInfoUseCase =
        GetUserInfoUseCase(repository)
}
