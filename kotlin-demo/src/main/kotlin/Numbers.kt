class Numbers {
}

fun main() {
//    listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {
//        if (value == 3) return  // 局部返回到匿名函数的调用者——forEach 循环
//        print(value)
//    })
//    print(" done with anonymous function")

    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return  // 局部返回到 lambda 表达式
        print(it)
    }
    print(" done with lambda")
}