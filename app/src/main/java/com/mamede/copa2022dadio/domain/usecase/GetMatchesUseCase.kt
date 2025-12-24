package com.mamede.copa2022dadio.domain.usecase

import com.mamede.copa2022dadio.domain.model.Match
import com.mamede.copa2022dadio.domain.repository.MatchesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Esta classe é um "Caso de Uso" (UseCase).
 * No padrão de arquitetura que estou usando, ela representa uma única ação que o usuário
 * pode fazer ou uma única funcionalidade do sistema.
 *
 * O objetivo deste UseCase é buscar a lista de partidas da Copa.
 *
 * @property repository É a ponte para os dados. O UseCase não sabe se os dados
 * vêm da internet ou de um banco de dados local; ele apenas pede ao "repositório" para buscá-los.
 *
 * @constructor [Inject] avisa ao Hilt (sistema de injeção de dependência) que ele
 * deve criar e entregar o repositório automaticamente quando precisarmos desta classe.
 */
class GetMatchesUseCase @Inject constructor(
    private val repository: MatchesRepository
) {
    /**
     * O segredo aqui é a palavra `operator fun invoke`.
     * Isso nos permite chamar esta classe como se fosse uma função.
     * Exemplo: Em vez de `getMatchesUseCase.execute()`, fazemos apenas `getMatchesUseCase()`.
     *
     * @return Um [Flow] contendo uma lista de [Match].
     * O [Flow] funciona como um "cano de água": sempre que houver uma partida nova ou
     * alterada, os dados vão "escorrer" por ele e atualizar a tela automaticamente.
     */
    operator fun invoke(): Flow<List<Match>> = repository.getMatches()

}