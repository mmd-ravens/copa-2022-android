package com.mamede.copa2022dadio.data.mapper

import com.mamede.copa2022dadio.data.local.entity.MatchEntity
import com.mamede.copa2022dadio.data.local.entity.StadiumEntity
import com.mamede.copa2022dadio.domain.model.Match
import com.mamede.copa2022dadio.domain.model.Stadium

// Converter: API -> Banco
fun Match.toEntity() = MatchEntity(
    id = if (this.id != 0) this.id else 0, // Garante 0 se vier vazio
    name = this.name,
    stadium = StadiumEntity(this.stadium.name, this.stadium.image),
    team1 = this.team1,
    team2 = this.team2,
    date = this.date,
    notificationEnabled = false
)

// Converter: Banco -> Domain
fun MatchEntity.toDomain() = Match(
    id = this.id,
    name = this.name,
    stadium = Stadium(this.stadium.stadiumName, this.stadium.stadiumImage),
    team1 = this.team1,
    team2 = this.team2,
    date = this.date
)