package com.mamede.copa2022dadio.domain.model


data class Match(
    val id: Int = 0,
    val name: String,
    val stadium: Stadium,
    val team1: String,
    val team2: String,
    val date: String,
    val notificationEnabled: Boolean
)

