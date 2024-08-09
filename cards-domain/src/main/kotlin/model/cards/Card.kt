package model.cards

sealed class Card(open val text: String)

data class FillableCard(override val text: String, val spaceAt: String) : Card(text)

data class AnswerCard(override val text: String) : Card(text)