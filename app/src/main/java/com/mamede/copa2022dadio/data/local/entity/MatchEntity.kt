package com.mamede.copa2022dadio.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class MatchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,

    @Embedded //para obj aninhados simples
    val stadium: StadiumEntity,

    val team1: String,
    val team2: String,
    val date: String
)
