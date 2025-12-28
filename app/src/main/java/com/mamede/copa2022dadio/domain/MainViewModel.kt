package com.mamede.copa2022dadio.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamede.copa2022dadio.domain.Match.MainUiState
import com.mamede.copa2022dadio.domain.usecase.EnableNotificationUseCase
import com.mamede.copa2022dadio.domain.usecase.GetMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A ViewModel é o "cérebro" da tela principal.
 * Ela decide o que a tela deve mostrar e processa as ações do usuário.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase,
    private val enableNotificationUseCase: EnableNotificationUseCase
) : ViewModel() {

    // O _state é privado para que apenas a ViewModel possa alterar o valor
    private val _state : MutableStateFlow<MainUiState> =
        MutableStateFlow(MainUiState.Loading)

    // O state é público para a View (Compose) observar, mas ela não pode alterá-lo diretamente
    val state : StateFlow<MainUiState> = _state.asStateFlow()

    //roda assim que a viewmodel é criada
    init {
        fetchMatches()
    }

    private fun fetchMatches() {
        viewModelScope.launch {
            //possivel fazer isso graças ao [Invoke()]
            getMatchesUseCase().catch {
                //se der erro att a mainUi para error
                _state.value = MainUiState.Error(
                    it.message ?: "Erro desconhecido")
                }.collect { matches ->
                    //quando chegar a oartida, att para sucesso
                    _state.value = MainUiState.Success(matches)
                }
        }
    }
}