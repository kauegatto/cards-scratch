package cards

import Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor
import model.Player
import model.PlayerId

// The Room class uses the actor pattern to handle concurrent message processing in a safe manner.
// The actor pattern encapsulates state and processes messages sequentially, which avoids race conditions and ensures thread safety.
@OptIn(ObsoleteCoroutinesApi::class)
class ActorRoom(override val players: Collection<Player>) : Room {
    private val actor = CoroutineScope(Dispatchers.Default).actor<RoomMessage> {
        for (message in channel) {
            when (message) {
                is RoomMessage.Broadcast -> broadcastMessage(message)
                is RoomMessage.SendToPlayer -> sendMessageToPlayer(message, message.playerId)
            }
        }
    }

    override suspend fun broadcastMessage(message: RoomMessage) {
        players.forEach { player ->
            player.sendMessageToPlayer(message)
        }
    }

    override suspend fun sendMessageToPlayer(message: RoomMessage, playerId: PlayerId) {
        players.find { it.playerId == playerId }?.sendMessageToPlayer(message)
    }

    override suspend fun sendMessage(message: RoomMessage) {
        actor.send(message)
    }
}

sealed class RoomMessage {
    data class Broadcast(val message: RoomMessageContent) : RoomMessage()
    data class SendToPlayer(val message: RoomMessageContent, val playerId: PlayerId) : RoomMessage()
}

data class RoomMessageContent(val todo: String)