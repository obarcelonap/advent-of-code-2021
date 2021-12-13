fun main() {
    fun parse(testInput: List<String>): Pair<List<Pair<Int, Int>>, List<Pair<String, Int>>> {
        val positions = testInput.takeWhile { it.isNotBlank() }
            .map { it.split(",") }
            .map { (first, second) -> Pair(first.toInt(), second.toInt()) }

        val folds = testInput.dropWhile { !it.startsWith("fold") }
            .map { it.split("=") }
            .map { (first, second) -> Pair(first.replace("fold along", "").trim(), second.toInt()) }

        return Pair(positions, folds)
    }

    fun part1(positions: List<Pair<Int, Int>>, folds: List<Pair<String, Int>>): Int {
        fun markDots(
            input: List<Pair<Int, Int>>,
            paper: MutableList<MutableList<Boolean>> = mutableListOf()
        ): MutableList<MutableList<Boolean>> {
            if (input.isEmpty()) {
                return paper
            }
            val (x, y) = input.first()
            paper[y][x] = true
            return markDots(input.drop(1), paper)
        }

        fun MutableList<MutableList<Boolean>>.fold(direction: String, value: Int): MutableList<MutableList<Boolean>> {
            fun foldY(origami: MutableList<MutableList<Boolean>>, value: Int): MutableList<MutableList<Boolean>> {
                val side1 = origami.subList(0, value)
                val side2 = origami.subList(value, origami.size)

                println("fold y=$value")
                side1.print()
                println("--".repeat(side1.first().size))
                side2.print()
                println()

                return side1.zip(side2.reversed())
                    .map { (row1, row2) -> row1.zip(row2).map { (value1, value2) -> value1 || value2 }.toMutableList() }
                    .toMutableList()
            }

            fun foldX(origami: MutableList<MutableList<Boolean>>, value: Int): MutableList<MutableList<Boolean>> {
                fun MutableList<MutableList<Boolean>>.transpose(): MutableList<MutableList<Boolean>> {
                    return first().indices.map { idx -> map { it[idx] }.toMutableList() }.toMutableList()
                }
                return foldY(origami.transpose(), value).transpose()
            }

            return if (direction == "y") foldY(this, value)
            else foldX(this, value)
        }

        val maxX = positions.maxOf { (first) -> first }
        val maxY = positions.maxOf { (_, second) -> second }

        val origami = markDots(positions, MutableList(maxY + 1) { MutableList(maxX + 1) { false } })
        println("initial marks")
        origami.print()
        println()
        val foldedOrigami = folds.fold(origami) { foldingOrigami, (dir, value) -> foldingOrigami.fold(dir, value) }

        println("folded")
        foldedOrigami.print()

        return foldedOrigami.sumOf { row -> row.count { it } }
    }


    fun part2(positions: List<Pair<Int, Int>>, folds: List<Pair<String, Int>>): Int {
        return part1(positions, folds)
    }

    // test if implementation meets criteria from the description, like:
    val (testPositions, testFolds) = parse(readInput("Day13_test"))
    check(part1(testPositions, testFolds) == 17)

    val (positions, folds) = parse(readInput("Day13"))
    println(part1(positions, folds.take(1)))
    println(part2(positions, folds))
}

fun MutableList<MutableList<Boolean>>.print() {
    forEach { row -> println(row.map { if (it) '#' else '.' }.joinToString(" ")) }
}
