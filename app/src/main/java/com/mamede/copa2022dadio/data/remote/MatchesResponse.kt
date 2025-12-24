package com.mamede.copa2022dadio.data.remote

import com.mamede.copa2022dadio.domain.model.Match

data class MatchesResponse(
    val matches: List<Match>
)