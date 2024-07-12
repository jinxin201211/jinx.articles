class TownSquare : Room("Town Square") {
    override val dangerLevel = super.dangerLevel - 3;

    override fun load() = "The villagers really and cheer as you enter!";
}

fun main() {
    val townSquare = TownSquare();
    println(townSquare.load())
    println(townSquare.description())

    var room: Room = TownSquare();
    println(room.load())
    println(room.description())
}