package com.mamede.copa2022dadio.data.repository

import com.mamede.copa2022dadio.MatchesApi
import com.mamede.copa2022dadio.data.local.dao.MatchesDao
import com.mamede.copa2022dadio.data.mapper.toDomain
import com.mamede.copa2022dadio.data.mapper.toEntity
import com.mamede.copa2022dadio.domain.model.Match
import com.mamede.copa2022dadio.domain.repository.MatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


class MatchesRepositoryImpl @Inject constructor(
    private val matchesApi: MatchesApi,
    private val matchesDao: MatchesDao
) : MatchesRepository {

    override  fun getMatches(): Flow<List<Match>> {
        return matchesDao.getMatches().map { entities ->
            //chamar o mpper (banco -> domain)
            entities.map { it.toDomain() }
        }.onStart {
            //executar a att da api no background
            refreshMatches()
        }


//        //tratar para funcionar mesmo offline
//        try {
//            val matchesResponse = matchesApi.getMatches()
//            //mapper = response -> entity
//            val entities = matchesResponse.matches.map { match ->
//                match.toEntity()
//            }
//
//            //salvar no banco
//            matchesDao.insertAll(entities)
//        } catch (e: Exception) {
//            //se der ruim, segue  e exibe o que tem no banco
//            e.printStackTrace()
//        }
//
//        //retorna o que ta no bancod, convertido para Domain
//        // DAO retorna o FLow<list<mnatchEntity>>, vou mapear para Flow<list<match>>
//        return matchesDao.getMatches().map { entitiesList ->
//            entitiesList.map { entity ->
//                entity.toDomain()
//            }
//        }
    }
    private suspend fun refreshMatches() {
        try {
            val responde = matchesApi.getMatches()
            //api -> banco
            val entities = responde.matches.map {it.toEntity() }
            matchesDao.insertAll(entities)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun disableNotificationFor(matchId: Int) {
        matchesDao.updateNotificationSetting(matchId, false)
    }


    override suspend fun enableNotificationFor(matchId: Int) {
        matchesDao.updateNotificationSetting(matchId, true)

    }
}