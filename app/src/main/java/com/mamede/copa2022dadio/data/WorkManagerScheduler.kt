package com.mamede.copa2022dadio.data


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.mamede.copa2022dadio.domain.model.Match
import com.mamede.copa2022dadio.worker.NotificationWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkManagerScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun schedule(match: Match) {
        val delay = calculateDelay(match.date)

        // Dados para exibir na notificação
        val inputData = workDataOf(
            "title" to "Vai começar!",
            "content" to "${match.team1} x ${match.team2} no ${match.stadium.name}"
        )

        // Cria a requisição
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .addTag("match_${match.id}") // Tag para poder cancelar depois
            .build()

        // Enfileira
        WorkManager.getInstance(context).enqueue(workRequest)
    }

    fun cancel(matchId: Int) {
        WorkManager.getInstance(context).cancelAllWorkByTag("match_$matchId")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateDelay(dateString: String): Long {
        return try {
            // Parsing da data ISO 8601 (ex: 2022-11-24T19:00:00Z)
            // Como seu minSdk é 24, podemos usar java.time (API moderna)
            // Se der erro de classe não encontrada, ative o "coreLibraryDesugaring" no gradle,
            // ou use SimpleDateFormat antigo.
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val matchDate = LocalDateTime.parse(dateString, formatter)
                .atZone(ZoneId.of("UTC")) // O JSON vem em Z (UTC)
                .toInstant()
                .toEpochMilli()

            val now = System.currentTimeMillis()

            // Retorna a diferença. Se for negativo (jogo passado), retorna 0 (executa na hora)
            // Na prática, você poderia impedir agendamento de jogo passado.
            maxOf(0L, matchDate - now)
        } catch (e: Exception) {
            e.printStackTrace()
            10000L // 10 segundos de teste se der erro no parse
        }
    }
}