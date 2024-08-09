package model

data class Room(
    val players: Collection<Player>
) {
    fun broadCastMessage(message: RoomMessage) {
        players.forEach { player -> player.sendMessageToPlayer(message)}
    }

    fun sendMessageToPlayer(message: RoomMessage, playerId: PlayerId) {
        players.find { it.playerId == playerId }?.sendMessageToPlayer(message)
    }
}

data class RoomMessage(
    private val todo: String
)