class MapTest(var firstname: String, var lastname: String, var age: Int) {
}

fun main() {
    val patronGold = mutableMapOf("Eli" to 10.5, "Mordoc" to 8.0, "Sophie" to 5.5);
    patronGold += "Mordocx" to 20.2;
    patronGold += "Mordocx" to 10.2;
//    println(patronGold.get("Mordocx")?.times(1.0))
//    println(patronGold["Mordocx"])
//    println(patronGold.getOrDefault("mordocxxx", 123.0))

//    println(patronGold["mordocxxx"])
//    println(patronGold.getOrElse("mordocxxx", { patronGold += "mordocxxx" to 123.0 }));
//    println(patronGold["mordocxxx"])
//
//    println(patronGold["mordocxxxx"])
//    println(patronGold.getOrPut("mordocxxxx", { 123.0 }));
//    println(patronGold["mordocxxxx"])

    var mapTest = MapTest("Tom", "Cruise", 48);
    println("${mapTest.firstname} ${mapTest.lastname}:\t${mapTest.age}");
}