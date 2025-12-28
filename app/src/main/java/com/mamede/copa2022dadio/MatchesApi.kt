package com.mamede.copa2022dadio

import com.mamede.copa2022dadio.data.remote.MatchesResponse
import retrofit2.http.GET

interface MatchesApi {
    //get -> diz ao retrogit que é uma requisão http get
    // supend,para chamar sem travar a tela (coroutines)
    @GET("api.json")
    suspend fun getMatches(): MatchesResponse
}