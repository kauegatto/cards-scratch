import model.Player
import model.PlayerId
import model.RoomMessage

interface Room {
    val players: Collection<Player>

    suspend fun broadcastMessage(message: RoomMessage)
    suspend fun sendMessageToPlayer(message: RoomMessage, playerId: PlayerId)
    suspend fun sendMessage(message: RoomMessage)
}