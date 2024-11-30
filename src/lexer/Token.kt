package lexer

interface Token {
    val id: Int
}

data class SimpleToken(override val id: Int = -1): Token

data class DataToken(override val id: Int = -1, val data: String): Token