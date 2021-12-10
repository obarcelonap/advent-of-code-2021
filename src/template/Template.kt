package template

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        return 1
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Template_test")
    check(part1(testInput) == 1)

    val input = readInput("Template")
    println(part1(input))
    println(part2(input))
}
