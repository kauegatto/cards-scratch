package model

import Room
import model.cards.AnswerCard

data class Game(
    private val room: Room,
    private val players: Set<Player>,
    private val currentJudge: PlayerId,
    private val rounds: Set<Round>,
) {
    private val playOrder: List<PlayerId> = players.shuffled().map { it.playerId }
    private val score = HashMap<PlayerId, Int>()
    private val winner: Player? = null

    fun addScore(player: PlayerId, score: Int = 1 ) {
        val oldScore = this.score[player] ?: return

        val newScore = oldScore + score
        this.score[player] = newScore

        if(newScore == WINNER_TURNS) {
            broadCastWinner(player)
        }
    }

    fun broadCastWinner(player: PlayerId) {
        room.broadCast(player)
    }

    companion object {
        val WINNER_TURNS: Int = 5
    }
}

data class Round(
    val answerCard: AnswerCard,
    val playsByPlayer: Map<AnswerCard, Player>,
    var winner: Player? = null
)