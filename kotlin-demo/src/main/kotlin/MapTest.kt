class MapTest {
}

fun main() {
    val patronGold = mutableMapOf<String, Double>("Eli" to 10.5, "Mordoc" to 8.0, "Sophie" to 5.5);
    patronGold += "Mordocx" to 20.2;
    patronGold += "Mordocx" to 10.2;
//    println(patronGold.get("Mordocx")?.times(1.0))
//    println(patronGold["Mordocx"])
//    println(patronGold.getOrDefault("mordocxxx", 123.0))

    println(patronGold["mordocxxx"])
    println(patronGold.getOrElse("mordocxxx", { patronGold += "mordocxxx" to 123.0 }));
    println(patronGold["mordocxxx"])

    println(patronGold["mordocxxxx"])
    println(patronGold.getOrPut("mordocxxxx", { 123.0 }));
    println(patronGold["mordocxxxx"])
}