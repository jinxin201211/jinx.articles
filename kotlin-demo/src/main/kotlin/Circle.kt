class Circle : Shape() {
    fun fill() {
        println("Circle.fill()")
    }

    override fun draw() {
        super.draw()
        println("Circle.draw()")
    }
}