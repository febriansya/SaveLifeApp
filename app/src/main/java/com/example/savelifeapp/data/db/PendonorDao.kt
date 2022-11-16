package com.example.savelifeapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface PendonorDao {
    @Query("SELECT * FROM pendonor")
    fun getPendonorData(): List<CalonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPendonor(pendonor: CalonEntity): Long

    @Update
    fun updatePendonor(pendonor: CalonEntity)
}