package lexer

class DFABuilder() {

    val rootNode: DFAState = DFAState()

    // Add a path for a fixed string to the DFA
    private fun DFAState.addString(identifier: String): DFAState {

        var state = this

        // Traverse DFA for given string literal and make sure all necessary nodes are present
        identifier.forEach {

            // Add transition, if not already present
            if(state.hasTransition(it)) state = state.transition(it)
            else state.addTransition(it, DFAState())

        }

        // Return the last state
        return state

    }

    // Add path for an identifier to the DFA
    fun addIdentifier(identifier: String, id: Int) {

        rootNode.addString(identifier).accepting = id

    }

    // Add path for any string literal surrounded by quotes to the DFA
    fun addStringLiteral(id: Int) {

        // Create path for a string sequence
        val stringSequence: () -> DFAState = {
            val char = DFAState()
            val backslash = DFAState()

            // Add transitions to self for all valid ASCII
            for(i in 0 until 128) {

                backslash.addTransition(i.toChar(), char)

                // Ignore quotes and backslashes
                if(i.toChar() in arrayOf('\'', '\"', '\\')) continue

                char.addTransition(i.toChar(), char)

            }

            char.addTransition('\\', backslash)
        }

        // Add string sequence path to root node, surrounded by single/double quotes
        val finalState = DFAState(id)
        rootNode.addTransition('\'', stringSequence().addTransition('\'', finalState))
        rootNode.addTransition('\"', stringSequence().addTransition('\"', finalState))

    }

    // Add path for a base 10 decimal to the DFA
    fun addBase10(id: Int) {

        val digit = DFAState(id)
        val decimal = DFAState()

        // Add transitions to digit
        for(i in CharRange('0', '9')) digit.addTransition(i, digit)
        digit.addTransition('.', decimal)

        // Add transitions to decimal
        for(i in CharRange('0', '9')) decimal.addTransition(i, digit)

        // Add transitions to root node
        for(i in CharRange('0', '9')) rootNode.addTransition(i, digit)

    }

    // Add path for a base 2 (binary) number to the DFA
    fun addBase2(id: Int) {

        val prefix = rootNode.addString("0b")
        val digit = DFAState(id)

        // Add transitions to prefix
        for(i in CharRange('0', '1')) prefix.addTransition(i, digit)

        // Add transitions to digit
        for(i in CharRange('0', '1')) digit.addTransition(i, digit)

    }

    // Add path for a base 8 (octal) number to the DFA
    fun addBase8(id: Int) {

        val prefix = rootNode.addString("0o")
        val digit = DFAState(id)

        // Add transitions to prefix
        for(i in CharRange('0', '8')) prefix.addTransition(i, digit)

        // Add transitions to digit
        for(i in CharRange('0', '8')) digit.addTransition(i, digit)

    }

    // Add path for a base 16 (hex) number to the DFA
    fun addBase16(id: Int) {

        val prefix = rootNode.addString("0x")
        val digit = DFAState(id)

        // Add transitions to prefix
        for(i in CharRange('0', '9')) prefix.addTransition(i, digit)
        for(i in CharRange('a', 'f')) prefix.addTransition(i, digit)
        for(i in CharRange('A', 'F')) prefix.addTransition(i, digit)

        // Add transitions to digit
        for(i in CharRange('0', '9')) digit.addTransition(i, digit)
        for(i in CharRange('a', 'f')) digit.addTransition(i, digit)
        for(i in CharRange('A', 'F')) digit.addTransition(i, digit)

    }

}

