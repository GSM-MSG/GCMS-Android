package com.msg.gcms.data.local.datasource.club

import com.msg.gcms.data.local.entity.ClubEntity

interface ClubLocalDataSource {

    suspend fun getClubData(type: String): List<ClubEntity>

    suspend fun insertClubData(vararg clubData: ClubEntity)

    suspend fun deleteClubData(type: String)

    suspend fun deleteAndInsertClubData(type: String, clubData: List<ClubEntity>)
}