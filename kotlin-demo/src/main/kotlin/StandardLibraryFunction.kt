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
}

fun nameIsLong(name: String) = name.length >= 20

fun playerCreateMessage(nameTooLong: Boolean): String {
    return if (nameTooLong) {
        "Name is too long. Please choose another name."
    } else {
        "Welcome, adventurer"
    }
}