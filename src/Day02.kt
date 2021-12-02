fun main() {
    fun part1(input: List<Pair<String, Int>>): Int {
        data class Submarine(var horizontal: Int = 0, var depth: Int = 0)

        val (horizontal, depth) = input
            .fold(Submarine()) { submarine, (command, value) ->
                when (command) {
                    "forward" -> submarine.apply { horizontal += value }
                    "down" -> submarine.apply { depth += value }
                    "up" -> submarine.apply { depth -= value }
                    else -> throw RuntimeException("Unknown direction in $command")
                }
            }

        return horizontal * depth
    }

    fun part2(input: List<Pair<String, Int>>): Int {
        data class Submarine(var horizontal: Int = 0, var depth: Int = 0, var aim: Int = 0)

        val (horizontal, depth) = input
            .fold(Submarine()) { submarine, (command, value) ->
                when (command) {
                    "forward" -> submarine.apply {
                        horizontal += value
                        depth += aim * value
                    }
                    "down" -> submarine.apply { aim += value }
                    "up" -> submarine.apply { aim -= value }
                    else -> throw RuntimeException("Unknown direction in $command")
                }
            }

        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test").map { it.toFirstPair() }
    check(part1(testInput) == 150)

    val input = readInput("Day02").map { it.toFirstPair() }
    println(part1(input))
    println(part2(input))
}

private fun String.toFirstPair(delimiter: Char = ' ') = split(delimiter)
    .zipWithNext { a, b -> Pair(a.trim(), b.toInt()) }
    .single()
