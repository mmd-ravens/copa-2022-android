package com.mamede.copa2022dadio.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.mamede.copa2022dadio.domain.MainViewModel
import com.mamede.copa2022dadio.domain.Match.MainUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    //observ o stateflow como Estado do Compose
    val state by viewModel.state.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Jogos da Copa") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ){ paddingValues ->
        // Decide o que mostrar baseado no Estado
        Box(modifier = Modifier.padding(paddingValues)) {
            when (state) {
                is MainUiState.Loading -> {
                    // Exibe um CircularProgressIndicator no centro
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is MainUiState.Error -> {
                    // Exibe texto de erro
                    Text(text = "Algo deu errado...")
                }
                is MainUiState.Success -> {
                    // Pega a lista de dentro do estado
                    val matches = (state as MainUiState.Success).matches

                    // Lista Vertical (RecyclerView do Compose)
                    LazyColumn {
                        items(matches) { match ->
                            MatchCard(
                                match = match,
                                onNotificationClick = {
                                    viewModel.toggleNotification(match)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}