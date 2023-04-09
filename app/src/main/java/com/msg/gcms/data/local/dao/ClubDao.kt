package com.msg.gcms.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.msg.gcms.data.local.entity.ClubEntity

@Dao
interface ClubDao {

    @Query("SELECT * FROM club WHERE type = :type")
    suspend fun getClubList(type: String): List<ClubEntity>

    @Query("DELETE FROM club WHERE type = :type")
    fun deleteClubData(type: String)

    @Insert
    fun insertAllClubData(clubData: List<ClubEntity>)

    @Transaction
    suspend fun deleteAndInsertData(type: String, clubData: List<ClubEntity>) {
        deleteClubData(type = type)
        insertAllClubData(clubData = clubData)
    }
}