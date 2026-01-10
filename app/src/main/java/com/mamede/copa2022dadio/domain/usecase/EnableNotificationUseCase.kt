package com.mamede.copa2022dadio.domain.usecase



import android.os.Build
import androidx.annotation.RequiresApi
import com.mamede.copa2022dadio.data.WorkManagerScheduler
import com.mamede.copa2022dadio.domain.model.Match
import com.mamede.copa2022dadio.domain.repository.MatchesRepository
import javax.inject.Inject

class EnableNotificationUseCase @Inject constructor(
    private val repository: MatchesRepository,
    private val sheduler: WorkManagerScheduler
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(match: Match) {
        //att no repositorio (BD)
        repository.enableNotificationFor(match.id)

        //agendar no sistema android
        sheduler.schedule(match)
    }

}