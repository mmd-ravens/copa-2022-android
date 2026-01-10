package com.mamede.copa2022dadio.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mamede.copa2022dadio.data.local.dao.MatchesDao
import com.mamede.copa2022dadio.data.local.entity.MatchEntity

@Database(entities = [MatchEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun matchesDao(): MatchesDao
}