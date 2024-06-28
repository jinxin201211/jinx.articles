class StandardLibraryFunction {
}

fun main() {
    println(listOf<Int>(1, 2, 3).first().let { it * it })
    "Polarcubis, Supreme Master of NyetHack"
        .run(::nameIsLong)
        .run(::playerCreateMessage)
        .run(::println)
    "Polarcubis"
        .run(::nameIsLong)
        .run(::playerCreateMessage)
        .run(::println)

    var queryList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l');
    (0..queryList.size).forEach { i -> println(queryList.get(i)) }
}

fun nameIsLong(name: String) = name.length >= 20

fun playerCreateMessage(nameTooLong: Boolean): String {
    return if (nameTooLong) "Name is too long. Please choose another name." else "Welcome, adventurer"
}