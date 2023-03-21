package com.msg.gcms.data.local.datasource.club

import com.msg.gcms.data.local.entity.ClubEntity

interface ClubLocalDataSource {

    suspend fun getClubData(type: String): List<ClubEntity>

    suspend fun insertClubData(clubData: ClubEntity)

    suspend fun deleteClubData(type: String)
}