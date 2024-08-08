class Level2 : Level1() {
}

fun main() {
    val level2 = Level2();
    val level1 = Level1();
    val level0 = Level0();
    println(level2 is Level0)
    println(level2 is Level1)
    println(level2 is Level2)
    println(level1 is Level0)
    println(level1 is Level1)
    println(level1 is Level2)
    println(level0 is Level0)
    println(level0 is Level1)
    println(level0 is Level2)
}