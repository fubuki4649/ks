package lexer

data class DFAState(var accepting: Int = -1) {

    private val transitions: MutableMap<Char, DFAState> = HashMap()

    fun hasTransition(char: Char): Boolean {
        return transitions.containsKey(char)
    }

    fun transition(c: Char): DFAState {
        return transitions.getValue(c)
    }

    fun addTransition(c: Char, state: DFAState): DFAState? {
        return transitions.put(c, state)
    }

}