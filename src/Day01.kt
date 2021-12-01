fun main() {
    fun part1(depths: List<Int>): Int = depths
        .windowed(2)
        .count { it[1] > it[0] }

    fun part2(depths: List<Int>): Int = depths
        .windowed(3) { it.sum() }
        .windowed(2)
        .count { it[1] > it[0] }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test").map { it.toInt() }
    check(part1(testInput) == 7)

    val input = readInput("Day01").map { it.toInt() }
    println(part1(input))
    println(part2(input))
}
