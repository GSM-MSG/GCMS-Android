package com.msg.gcms.di.module

import android.content.Context
import androidx.room.Room
import com.msg.gcms.data.local.dao.ClubDao
import com.msg.gcms.data.local.database.GCMSDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideGCMSDatabase(
        @ApplicationContext context: Context
    ): GCMSDataBase = Room
        .databaseBuilder(
            context,
            GCMSDataBase::class.java,
            "gcms_database"
        ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideClubDao(
        gcmsDataBase: GCMSDataBase
    ): ClubDao = gcmsDataBase.clubDao()
}