package com.msg.gcms.data.local.dao

import androidx.room.Insert
import androidx.room.Query
import com.msg.gcms.data.local.entity.ClubEntity

interface ClubDao {

    @Query("SELECT * FROM clubEntity WHERE type = :type")
    fun getClubList(type: String): List<ClubEntity>

    @Query("DELETE FROM clubEntity WHERE type = :type")
    fun deleteClubData(type: String)

    @Insert
    fun insertClubData(clubEntity: List<ClubEntity>)
}