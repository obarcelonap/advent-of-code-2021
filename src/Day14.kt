fun main() {
    fun parse(readInput: List<String>): Pair<String, Map<String, String>> {
        val template = readInput.first()
        val insertionRules = readInput.drop(2)
            .map { it.split("->") }
            .groupBy({ it[0].trim() }, { it[1].trim() })
            .mapValues { it.value.first() }

        return Pair(template, insertionRules)
    }


    fun steps(n: Int, template: String, insertionRules: Map<String, String>): Long {
        tailrec fun step(n: Int, counts: Map<String, Long>): Map<String, Long> {
            if (n == 0) {
                return counts
            }

            val newCounts =
                counts.entries.fold<Map.Entry<String, Long>, MutableMap<String, Long>>(mutableMapOf()) { acc, (pair, count) ->
                    val insertion = insertionRules[pair] ?: ""
                    acc.merge(pair[0] + insertion, count, java.lang.Long::sum)
                    acc.merge(insertion + pair[1], count, java.lang.Long::sum)
                    acc
                }
            return step(n - 1, newCounts)
        }

        val initialPairCounts = template.windowed(2).groupBy { it }.mapValues { it.value.size.toLong() }
        val pairCounts = step(n, initialPairCounts)

        val charCounts = pairCounts.entries.fold(mutableMapOf<Char, Long>(template[0] to 1)) { acc, (pair, count) ->
            acc.merge(pair[1], count, java.lang.Long::sum)
            acc
        }
        return charCounts.maxOf { it.value } - charCounts.minOf { it.value }
    }

    fun part1(template: String, insertionRules: Map<String, String>): Long {
        return steps(10, template, insertionRules)
    }

    fun part2(template: String, insertionRules: Map<String, String>): Long {
        return steps(40, template, insertionRules)
    }

    // test if implementation meets criteria from the description, like:
    val (testTemplate, testInsertionRules) = parse(readInput("Day14_test"))
    check(part1(testTemplate, testInsertionRules) == 1588L)

    val (template, insertionRules) = parse(readInput("Day14"))
    println(part1(template, insertionRules))
    println(part2(template, insertionRules))
}
