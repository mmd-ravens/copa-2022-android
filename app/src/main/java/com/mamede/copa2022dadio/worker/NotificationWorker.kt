package com.mamede.copa2022dadio.worker

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import com.mamede.copa2022dadio.R

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters
) : Worker(context, workerParams){

    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Hora do Jogo!"
        val content = inputData.getString("content") ?: "Partida preste a começar."

        showNotification(title, content)

        return Result.success()
    }

    private fun showNotification(title: String, content: String) {
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
        val channelId = "matches_channel_id"
    }
}