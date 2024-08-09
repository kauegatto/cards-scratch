package model

import java.util.UUID

typealias PlayerId = UUID

data class Player(val playerId: PlayerId) {
    fun sendMessageToPlayer(message: RoomMessage) {
        TODO("Not yet implemented")
    }
}