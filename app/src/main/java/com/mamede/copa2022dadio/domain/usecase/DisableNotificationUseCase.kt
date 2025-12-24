package com.mamede.copa2022dadio.domain.usecase

import com.mamede.copa2022dadio.domain.repository.MatchesRepository
import javax.inject.Inject


/**
 * Esta classe é um "Caso de Uso" (UseCase) especializado em uma única tarefa:
 * desativar as notificações de uma partida específica.
 *
 * Imagine o UseCase como um "especialista". Se o aplicativo precisa parar de avisar
 * o usuário sobre os gols de um jogo, ele chama este especialista.
 *
 * @property repository O "Almoxarifado" de dados. É ele quem realmente sabe como
 * chegar no banco de dados ou no servidor para salvar que a notificação foi desligada.
 *
 * @constructor O [Inject] faz parte do Hilt. Ele garante que o Kotlin saiba de onde
 * tirar o [repository] sem que você precise criá-lo manualmente (new MatchesRepository).
 */
class DisableNotificationUseCase @Inject constructor(
    private val repository: MatchesRepository
) {
    /**
     * Esta é a função principal que executa a ação.
     *
     * O nome `invoke` com a palavra `operator` é um "truque" do Kotlin: ele permite que
     * você chame a classe como se ela fosse uma função.
     * Exemplo: `disableNotificationUseCase(123)` em vez de `disableNotificationUseCase.execute(123)`.
     *
     * @param matchId O número de identificação único da partida que o usuário
     * não quer mais acompanhar via notificações.
     *
     * @run [suspend]: Esta palavra indica que a função é uma coroutines. Como mexer no banco
     * de dados pode demorar um pouco, o `suspend` faz com que o aplicativo "espere"
     * sem travar a tela do usuário.
     */
    // Recebe o ID da partida (matchID)
    // Retorna Unit (nada)
    suspend operator fun invoke(matchId: Int) {
        repository.disableNotificationFor(matchId)
    }
}