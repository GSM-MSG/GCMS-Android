package com.msg.gcms.di.module

import android.util.Log
import com.msg.gcms.BuildConfig
import com.msg.gcms.data.remote.network.api.ClubAPI
import com.msg.gcms.data.remote.network.api.AuthAPI
import com.msg.gcms.data.remote.network.api.ImageAPI
import com.msg.gcms.data.remote.network.LoginInterceptor
import com.msg.gcms.data.remote.network.api.ApplicantAPI
import com.msg.gcms.data.remote.network.api.ClubMemberAPI
import com.msg.gcms.data.remote.network.api.UserAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message -> Log.v("HTTP", message) }
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        loginInterceptor: LoginInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            // 요청을 시작한 후 서버와의 TCP handshake 가 완료되기까지 지속되는 시간
            .connectTimeout(30, TimeUnit.SECONDS)
            // 모든 바이트가 전송되는 속도륵 감시
            .readTimeout(30, TimeUnit.SECONDS)
            // 읽기 타임 아웃의 반대 방향. 얼마나 빨리 서버에 바이트를 보낼 수 있는지 확인
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loginInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideClubService(retrofit: Retrofit): ClubAPI {
        return retrofit.create(ClubAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideImageService(retrofit: Retrofit): ImageAPI {
        return retrofit.create(ImageAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideApplicantService(retrofit: Retrofit): ApplicantAPI {
        return retrofit.create(ApplicantAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideClubMemberService(retrofit: Retrofit): ClubMemberAPI {
        return retrofit.create(ClubMemberAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserAPI {
        return retrofit.create(UserAPI::class.java)
    }
}
