package com.example.savelifeapp.data.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [CalonEntity::class],
    version = 1,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)

abstract class RoomAppDb : RoomDatabase() {
    abstract fun pendonorDao(): PendonorDao?
    companion object {
        private var INSTANCE: RoomAppDb? = null
        fun getAppDatabase(context: Context): RoomAppDb? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder<RoomAppDb>(
                    context.applicationContext,
                    RoomAppDb::class.java, "pendonor"
                ).allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }
}

