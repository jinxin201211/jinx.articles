class SwordJuggler {
}

fun main(args: Array<String>) {
    var swordsJuggling: Int? = null
    val isJugglingProficient = (1..3).shuffled().last() == 3
    if (isJugglingProficient) {
        swordsJuggling = 2
    }
    swordsJuggling = swordsJuggling!!.plus(1)
    println("You juggle $swordsJuggling swords!")
}