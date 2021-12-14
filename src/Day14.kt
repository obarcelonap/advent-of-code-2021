fun main() {
    fun parse(readInput: List<String>): Pair<String, Map<String, String>> {
        val template = readInput.first()
        val insertionRules = readInput.drop(2)
            .map { it.split("->") }
            .groupBy({ it[0].trim() }, { it[1].trim() })
            .mapValues { it.value.first() }

        return Pair(template, insertionRules)
    }


    fun steps(n: Int, template: String, insertionRules: Map<String, String>): Int {
        tailrec fun step(n: Int, template: String): String {
            if (n == 0) {
                return template
            }

            println("step $n template $template")

            val nextTemplate = template
                .windowed(2, partialWindows = true)
                .joinToString("") {
                    it[0] + (insertionRules[it] ?: "")
                }
            return step(n - 1, nextTemplate)
        }

        val finalTemplate = step(n, template)
        val elementOccurrences = finalTemplate.groupBy { it }.mapValues { it.value.size }
        return elementOccurrences.maxOf { it.value } - elementOccurrences.minOf { it.value }
    }

    fun part1(template: String, insertionRules: Map<String, String>): Int {
        return steps(10, template, insertionRules)
    }

    fun part2(template: String, insertionRules: Map<String, String>): Int {
        return steps(40, template, insertionRules)
    }

    // test if implementation meets criteria from the description, like:
    val (testTemplate, testInsertionRules) = parse(readInput("Day14_test"))
    check(part1(testTemplate, testInsertionRules) == 1588)

    val (template, insertionRules) = parse(readInput("Day14"))
    println(part1(template, insertionRules))
//    println(part2(template, insertionRules))
}
