package com.mamede.copa2022dadio.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mamede.copa2022dadio.data.local.entity.MatchEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MatchesDao {

    /**
     * Busca todas as partidas do banco de dados.
     * Retornamos um [Flow] para que a UI seja atualizada automaticamente
     * sempre que houver qualquer mudança na tabela 'matches'.
     */
    @Query("SELECT * FROM matches")
    fun getMatches(): Flow<List<MatchEntity>>

    /**
     * Atualiza o estado da notificação de uma partida específica.
     * @param id O identificador da partida.
     * @param enable Verdadeiro se a notificação deve ser ligada, falso caso contrário.
     */
    @Query("UPDATE matches SET notificationEnabled = :enable WHERE id = :id")
    suspend fun updateNotificationSetting(id: Int, enable: Boolean)

    /**
     * Insere uma lista de partidas de uma vez.
     * [OnConflictStrategy.REPLACE] faz com que, se a partida já existir (mesmo ID),
     * ela seja atualizada com os novos dados vindos da internet.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(matches: List<MatchEntity>)





}