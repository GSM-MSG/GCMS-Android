package com.msg.gcms.data.local.dao

import android.util.Log
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
    suspend fun deleteClubData(type: String)

    @Insert
    suspend fun insertAllClubData(vararg clubData: ClubEntity)

    @Transaction
    suspend fun deleteAndInsertData(type: String, clubData: List<ClubEntity>) {
        Log.d("TAG", "deleteAndInsertData: $type")
        deleteClubData(type = type)
        Log.d("TAG", "deleteAndInsertData: $clubData")
        insertAllClubData(clubData = clubData.toTypedArray())
    }
}