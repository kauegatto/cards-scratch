package model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.actor

// The Room class uses the actor pattern to handle concurrent message processing in a safe manner.
// The actor pattern encapsulates state and processes messages sequentially, which avoids race conditions and ensures thread safety.
class Room(
    private val players: Collection<Player>
) {
    private val actor = CoroutineScope(Dispatchers.Default).actor<RoomMessage> {
        for (message in channel) {
            when (message) {
                is RoomMessage.Broadcast -> broadcastMessage(message)
                is RoomMessage.SendToPlayer -> sendMessageToPlayer(message, message.playerId)
            }
        }
    }

    private fun broadcastMessage(message: RoomMessage) {
        players.forEach { player ->
            player.sendMessageToPlayer(message)
        }
    }

    private fun sendMessageToPlayer(message: RoomMessage, playerId: PlayerId) {
        players.find { it.playerId == playerId }?.sendMessageToPlayer(message)
    }

    suspend fun sendMessage(message: RoomMessage) {
        actor.send(message)
    }
}

sealed class RoomMessage {
    data class Broadcast(val message: RoomMessageContent) : RoomMessage()
    data class SendToPlayer(val message: RoomMessageContent, val playerId: PlayerId) : RoomMessage()
}

data class RoomMessageContent(val todo: String)