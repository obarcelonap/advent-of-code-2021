import java.util.*

fun main() {

    fun part1(input: List<String>): Int {
        fun firstIllegalCharacter(line: String): Char? {
            val stack = Stack<Char>()
            for (c in line) {
                if (c.opensChunk()) {
                    stack.push(c)
                } else if (!c.closesChunk(stack.pop())) {
                    return c
                }
            }
            return null
        }

        return input.mapNotNull { firstIllegalCharacter(it) }.sumOf {
                when (it) {
                    ')' -> 3
                    ']' -> 57
                    '}' -> 1197
                    '>' -> 25137
                    else -> 0
                }.toInt()
            }
    }

    fun part2(input: List<String>): Long {
        fun sequenceClosingCharacters(line: String): List<Char>? {
            val stack = Stack<Char>()
            for (c in line) {
                if (c.opensChunk()) {
                    stack.push(c)
                } else if (!c.closesChunk(stack.pop())) {
                    return null
                }
            }
            return stack.reversed().mapNotNull { it.closeChunk() }.toList()
        }

        fun totalScore(it: List<Char>) = it.fold(0L) { score, c ->
            (score * 5) + when (c) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> 0
            }
        }

        val allScores = input.mapNotNull { sequenceClosingCharacters(it) }.map { totalScore(it) }.sorted()

        return allScores[allScores.size / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

private fun Char.closesChunk(open: Char) = when (open) {
    '(' -> this == ')'
    '[' -> this == ']'
    '{' -> this == '}'
    '<' -> this == '>'
    else -> false
}

private fun Char.opensChunk() = setOf('(', '[', '{', '<').contains(this)

private fun Char.closeChunk() = when (this) {
    '(' -> ')'
    '[' -> ']'
    '{' -> '}'
    '<' -> '>'
    else -> null
}

