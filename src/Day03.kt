typealias Binary = String

fun main() {

    fun part1(input: List<String>): Int {
        val gamma = input.first().indices
            .map { idx -> input.sumOf { s -> Character.getNumericValue(s[idx]) } }
            .fold("") { gamma, sum ->
                if (sum >= input.size / 2) gamma + '1'
                else gamma + '0'
            }

        val epsilon = gamma.negation()
        return gamma.toInt() * epsilon.toInt()
    }

    fun part2(input: List<String>): Int {
        fun oxigenGeneratorRating(input: List<String>, index: Int = 0): Int =
            when (input.size) {
                0 -> throw IllegalArgumentException()
                1 -> input.first().toInt()
                else -> {
                    val (zeros, ones) = input.partition { it[index] == '0' }

                    oxigenGeneratorRating(
                        if (zeros.size > ones.size) zeros else ones,
                        index + 1
                    )
                }
            }

        fun co2ScrubberRating(input: List<String>, index: Int = 0): Int =
            when (input.size) {
                0 -> throw IllegalArgumentException()
                1 -> input.first().toInt()
                else -> {
                    val (zeros, ones) = input.partition { it[index] == '0' }

                    co2ScrubberRating(
                        if (zeros.size <= ones.size) zeros else ones,
                        index + 1
                    )
                }
            }

        return oxigenGeneratorRating(input) * co2ScrubberRating(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

fun Binary.toInt(): Int = Integer.parseInt(this, 2)
fun Binary.negation(): Binary = map { if (it == '0') '1' else '0' }.joinToString(separator = "")
