package com.msg.gcms.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.msg.gcms.data.local.entity.ClubEntity

@Dao
interface ClubDao {

    @Query("SELECT * FROM clubEntity WHERE type = :type")
    suspend fun getClubList(type: String): List<ClubEntity>

    @Query("DELETE FROM clubEntity WHERE type = :type")
    suspend fun deleteClubData(type: String)

    @Insert
    suspend fun insertAllClubData(clubEntity: List<ClubEntity>)
}