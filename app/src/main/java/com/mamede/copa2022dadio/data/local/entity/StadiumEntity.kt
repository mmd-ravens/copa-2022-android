package com.mamede.copa2022dadio.data.local.entity

/**
 * Representa as informações de um estádio onde ocorre uma partida da Copa.
 *
 * No banco de dados (Room), esta classe não cria uma tabela própria. Em vez disso,
 * ela é usada como um "objeto embutido" (@Embedded) dentro da [MatchEntity].
 * Isso significa que as colunas de nome e imagem do estádio aparecerão diretamente
 * na tabela de partidas.
 *
 * @property stadiumName O nome oficial do estádio (ex: "Lusail Stadium").
 * @property stadiumImage O endereço (URL) da imagem ou foto do estádio para ser exibida na tela.
 */
data class StadiumEntity(
    val stadiumName: String,
    val stadiumImage: String
)
