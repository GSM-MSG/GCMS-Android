package com.msg.gcms.data.local.dao

import androidx.room.Query
import com.msg.gcms.data.local.entity.ClubEntity

interface ClubDao {

    @Query("SELECT * FROM clubEntity")
    fun getClubList(): List<ClubEntity>
}