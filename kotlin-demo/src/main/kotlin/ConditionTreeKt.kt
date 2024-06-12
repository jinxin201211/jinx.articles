class ConditionTreeKt {
    fun level(point: Int): String {
        return when (point) {
            100 -> "level0"
            in 90..99 -> "level1"
            in 80..89 -> "level2"
            in 70..79 -> "level3"
            in 60..69 -> "level4"
            else -> "level5"
        }
    }

    fun castFireball(numFireballs: Int = 2) = println("A glass of Fireball springs into existence. (x$numFireballs)")
}

fun main() {
    val point = 100;
    val message = if (point == 100) "gorgeous" else "excellent";
    println(message)

    println("\$message = $message ${message + message}")

    for (i in 1 until 3) {
        println(i)
    }

    println(3 in 1 until 3)
    println(3 in 3 downTo 1)
    println(3 in 1..3)
    println(3 !in 1..3)

    var conditionTreeKt = ConditionTreeKt();
    for (i in 1..100) {
        println(i.toString() + "\t" + conditionTreeKt.level(i))
    }
    println(conditionTreeKt.castFireball(3))
}