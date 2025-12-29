package com.mamede.copa2022dadio.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mamede.copa2022dadio.domain.model.Match

@Composable
fun MatchCard(
    match: Match,
    modifier: Modifier,
    onClick: (() -> Unit)? = null
) {

    val clickableModifier = onClick?.let {
        modifier.fillMaxWidth().clickable{ it() }
    } ?: modifier.fillMaxWidth()

    Card

}