//region sample Start
class Constructors {
    constructor(i: Int) {
        println("Constructor $i")
    }
    init {
        println("Init block")
    }
}
//endregion sample End

fun main() {
    Constructors(1)
}