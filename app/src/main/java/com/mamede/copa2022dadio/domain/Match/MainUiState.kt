package com.mamede.copa2022dadio.domain.Match

import com.mamede.copa2022dadio.domain.model.Match

/**
 * Representa os diferentes estados possíveis da tela principal (Main Screen).
 *
 * No Android moderno, a nossa "Tela" (View) reage ao que está acontecendo aqui.
 * Imagine que este objeto é um "comunicador": ele avisa para a interface se ela
 * deve mostrar uma barra de carregamento, a lista de jogos ou uma mensagem de erro.
 *
 * Sendo uma `sealed interface`, ela garante que ninguém fora deste arquivo possa
 * criar novos estados, mantendo o comportamento da tela previsível e seguro.
 */
sealed interface MainUiState {

    /**
     * Estado de Carregamento.
     * Deve ser usado quando o aplicativo está buscando os dados (na internet ou no banco).
     * a tele deveria exibe algo como um circulo carregando.
     */
    object Loading : MainUiState

    /**
     * Estado de Sucesso.
     * Indica que os dados foram carregados corretamente.
     *
     * @property matches A lista de partidas da Copa que será exibida para o usuário.
     */
    data class Success(val matches: List<Match>) : MainUiState

    /**
     * Estado de Erro.
     * Usado quando algo dá errado (falta de internet, servidor fora do ar, etc).
     *
     * @property message Uma frase amigável explicando o que aconteceu para o usuário.
     */
    data class Error(val message: String) : MainUiState

}