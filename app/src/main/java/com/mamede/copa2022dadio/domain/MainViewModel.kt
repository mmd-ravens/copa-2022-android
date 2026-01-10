package com.mamede.copa2022dadio.domain

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamede.copa2022dadio.domain.Match.MainUiState
import com.mamede.copa2022dadio.domain.model.Match
import com.mamede.copa2022dadio.domain.usecase.DisableNotificationUseCase
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
 * A **ViewModel** é como o "Gerente" ou o "Cérebro" da tela.
 *
 * Imagine que a tela (View) é apenas um desenho bonito que não sabe fazer nada sozinha.
 * A **ViewModel** é quem decide o que deve ser desenhado e o que acontece quando o usuário clica em algo.
 *
 * Por que usar **ViewModel**? No Android, se você girar o celular, a tela é destruída e recriada.
 * A **ViewModel** sobrevive a isso, garantindo que os dados (como a lista de jogos) não desapareçam.
 *
 * @property getMatchesUseCase O "especialista" que sabe buscar os jogos no banco ou internet.
 * @property enableNotificationUseCase O "especialista" que ativa o sininho de notificação.
 * @property disableNotificationUseCase O "especialista" que desativa o sininho de notificação.
 *
 * @constructor O `@Inject` avisa ao Hilt (nosso entregador de ferramentas) para trazer os
 * UseCases automaticamente para nós.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase,
    private val enableNotificationUseCase: EnableNotificationUseCase,
    private val disableNotificationUseCase: DisableNotificationUseCase
) : ViewModel() {

    /**
     * O `_state` é onde guardamos a "situação atual" da tela de forma privada.
     * Usamos [MutableStateFlow] porque ele funciona como um cano que sempre tem um valor dentro.
     * Começamos com [MainUiState.Loading] (Carregando).
     */
    // O _state é privado para que apenas a ViewModel possa alterar o valor
    private val _state : MutableStateFlow<MainUiState> =
        MutableStateFlow(MainUiState.Loading)

    /**
     * O `state` é a versão "só para leitura" do nosso estado.
     * A tela (Compose) fica "vigiando" esse valor. Se ele mudar de 'Loading' para 'Success',
     * a tela desenha a lista de jogos automaticamente.
     */
    // O state é público para a View (Compose) observar, mas ela não pode alterá-lo diretamente
    val state : StateFlow<MainUiState> = _state.asStateFlow()

    /**
     * O bloco `init` é executado assim que a ViewModel nasce.
     * Aqui, já mandamos buscar os jogos para o usuário não ver uma tela vazia.
     */
    //roda assim que a viewmodel é criada
    init {
        fetchMatches()
    }

    /**
     * Inicia a busca das partidas.
     *
     * Usamos o `viewModelScope.launch` para criar uma **Corrotina**.
     * Isso permite que a busca ocorra em "segundo plano" sem travar o aplicativo
     * enquanto os dados são carregados.
     */
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
    /**
     * Inverte o estado da notificação de um jogo.
     *
     * Se o usuário clica no sininho:
     * 1. Se já estava ligado, nós desligamos usando o [disableNotificationUseCase].
     * 2. Se estava desligado, nós ligamos usando o [enableNotificationUseCase].
     *
     * @param match A partida que o usuário interagiu.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun toggleNotification(match: Match) {
        viewModelScope.launch {
            if (match.notificationEnabled) {
                disableNotificationUseCase(match.id)
            } else {
                enableNotificationUseCase(match)
            }
            // não precisa att o _state manualmente, pois
            // o getMatchesUseCase é um Flow, então assim que o BD mudar,
            // o fetchMatches percebe e a tela att

        }
    }
}