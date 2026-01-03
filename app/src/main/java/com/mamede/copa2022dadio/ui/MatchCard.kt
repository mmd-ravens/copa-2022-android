package com.mamede.copa2022dadio.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mamede.copa2022dadio.domain.model.Match
import com.mamede.copa2022dadio.domain.model.Stadium
import com.mamede.copa2022dadio.R


/**
 * Este componente representa um "Card" (cartão) visual de uma partida da Copa.
 *
 * Imagine o [MatchCard] como um template: ele define ONDE cada informação
 * (nome do time, imagem do estádio, data) deve aparecer, mas os dados reais
 * vêm do objeto [match].
 *
 * @param match O objeto que contém todos os dados da partida (times, estádio, etc).
 * @param onNotificationClick Uma função que será executada quando o usuário clicar no sino.
 *        Passamos o [Match] de volta para que a ViewModel saiba qual jogo atualizar.
 * @param modifier Permite ajustar o visual externo do card (como margens) de quem o chama.
 */
@Composable
fun MatchCard(
    match: Match,
    onNotificationClick: (Match) -> Unit,
    modifier: Modifier = Modifier
) {

    // A estrutura do Card segue esta hierarquia:
    // Card -> Column -> [Box (Imagem + Estádio + Sino), Row (Times e Placar)]

    Card(
        //config de sombra e cor de fundo
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {},
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White
        )
    ) {
        /*
           Remember:
           - Column coloca itens um EMBAIXO do outro.
           - Row coloca itens um ao LADO do outro.
           - Box coloca itens um em CIMA do outro (como camadas).
        */
        //coluna principal
        Column{
            //Head = estadio e imagem
            Box(
                modifier = Modifier.fillMaxWidth().height(140.dp)
            ) {
                //carrega a imagem
                AsyncImage(
                    model = match.stadium.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop, // corta a img para caber
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = painterResource(id = R.drawable.ic_launcher_background),
                    error = painterResource(id = R.drawable.ic_launcher_background)

                )
            }

            Box( // uma caixa para deixar a letra melhor pra ler
                modifier = Modifier
                    .align(Alignment.BottomStart as Alignment.Horizontal)
                    .fillMaxWidth()
                    .background(Color.Black.copy(0.5f)) //quase transparente
                    .padding(8.dp)
            ){
                Text(
                    text = match.stadium.name,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(
                onClick = { onNotificationClick(match) },
                modifier = Modifier
                    .align(
                        Alignment.TopEnd as Alignment.Horizontal)
                    .padding(4.dp)
                    .background(Color.White.copy(0.7f)),
                shape = RoundedCornerShape(50)
            ) {
               Icon(
                   imageVector = if (match.notificationEnabled) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                   contentDescription = "Notificação",
                   tint =
                       if (match.notificationEnabled) Color(0xFFD32F2F) else Color.Gray // Vermelho se ativo)
               )
            }

            // linha do time e placar
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //time 1
                TeamItem(name = match.team1, alignEnd = true)

                Spacer(modifier = Modifier.width(16.dp))
                //VS
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(text = "x",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Gray)
                    //data
                    Text(
                        text = formatDate(match.date),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                //time 2
                TeamItem(name = match.team2, alignEnd = false)
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    match.date,
                    style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

/**
 * Um sub-componente para exibir o nome de um time de forma organizada dentro da Row.
 *
 * @param name Nome do país/time.
 * @param alignEnd Define se o texto deve "grudar" na direita ou esquerda.
 *        Isso é usado para que os nomes dos times apontem para o centro do placar.
 */
@Composable
fun RowScope.TeamItem(name: String, alignEnd: Boolean) {
    Text(
        text = name,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        textAlign = if (alignEnd) TextAlign.End else TextAlign.Start,
        modifier = Modifier.weight(1f) // para ocupar o espaço disponível
    )
}

/**
 * Função utilitária para transformar uma data de "computador" em data de "humano".
 *
 * Exemplo: Transforma "2022-11-24T19:00:00Z" em "24/11 - 19:00".
 *
 * @param dateString A string bruta que vem da API/Banco de dados.
 * @return A data formatada ou a original caso ocorra um erro de leitura.
 */
// fun simples para formata data
fun formatDate(dateString: String): String {
    // Ex: "2022-11-24T19:00:00Z" -> pega só o que queremos, dia mes e ano
    return try {
        val data = dateString.substring(8, 10) + "/" + dateString.substring(5, 7)
        val hora = dateString.substring(11, 16)
        "$data - $hora"
    } catch (e: Exception) {
        dateString // Retorna original se der erro
    }
}

@Preview(showBackground = true)
@Composable
fun MatchCardPreview() {
    //criar um dado fake, só para testar o layout
    val matchFake = Match(
        id = 1,
        name = "1 rodada",
        stadium = Stadium(
            name = "Estádio 1",
            image = ""
        ),
        team1 = "BRA",
        team2 = "FLU",
        date = "2022-11-24T16:00:00",
        notificationEnabled = true
    )
    MaterialTheme {
        MatchCard(
            matchFake,
            onNotificationClick = { } // tá vazia pq no preview não tem click
        )
    }
}