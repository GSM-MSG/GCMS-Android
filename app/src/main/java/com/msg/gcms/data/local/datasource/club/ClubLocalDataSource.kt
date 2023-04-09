package com.msg.gcms.data.local.datasource.club

import com.msg.gcms.data.local.entity.ClubEntity

interface ClubLocalDataSource {

    suspend fun getClubData(type: String): List<ClubEntity>

    fun insertClubData(clubData: List<ClubEntity>)

    fun deleteClubData(type: String)

    suspend fun deleteAndInsertClubData(type: String, clubData: List<ClubEntity>)
}