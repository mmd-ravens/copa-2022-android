package com.mamede.copa2022dadio.domain.usecase

import com.mamede.copa2022dadio.domain.repository.MatchesRepository
import javax.inject.Inject

class EnableNotificationUseCase @Inject constructor(
    private val repository: MatchesRepository
) {
    suspend operator fun invoke(matchId: Int) {
        repository.enableNotificationFor(matchId)
    }
}