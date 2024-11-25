package lexer

class DFABuilder {

    val rootState: DFAState = DFAState()

    fun addLiteral(literal: String, id: Int) {

        var state = rootState

        // Traverse DFA for given string literal and make sure all necessary nodes are present
        literal.forEach {

            // Add transition, if not already present
            if(state.hasTransition(it)) state = state.transition(it)
            else state.addTransition(it, DFAState())

        }

        // Mark the last state as accepting
        state.accepting = id

    }

    fun addRegex(regex: String, id: Int) {



    }

}