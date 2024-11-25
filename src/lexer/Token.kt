package lexer

interface Token {
    val id: Int
}

data class SimpleToken(override val id: Int = -1): Token

data class DataToken<T>(override val id: Int = -1, val data: T): Token