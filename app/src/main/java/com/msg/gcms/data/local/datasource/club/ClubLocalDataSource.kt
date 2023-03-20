package com.msg.gcms.data.local.datasource.club

import com.msg.gcms.data.local.entity.ClubEntity

interface ClubLocalDataSource {

    fun getClubData(type: String): List<ClubEntity>

    fun insertClubData(clubData: List<ClubEntity>)

    fun deleteClubData(type: String)
}