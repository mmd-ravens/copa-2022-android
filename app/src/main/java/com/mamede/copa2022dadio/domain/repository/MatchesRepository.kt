package com.mamede.copa2022dadio.domain.repository

import com.mamede.copa2022dadio.domain.model.Match
import kotlinx.coroutines.flow.Flow

interface MatchesRepository {
    fun getMatches(): Flow<List<Match>>
    suspend fun enableNotificationFor(matchId: Int)
    suspend fun disableNotificationFor(matchId: Int)

}