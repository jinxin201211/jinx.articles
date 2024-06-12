## if/else

### 使用条件表达式赋值

``` kotlin
fun main(args: Array<String>) {
...
  val healthStatus = if (healthPoints == 100) {
    "is in excellent condition!"
  } else if (healthPoints >= 90) {
    "has a few scratches."
  } else if (healthPoints >= 75) {
    if (isBlessed) {
      "has some minor wounds but is healing quite quickly!"
    } else {
      "has some minor wounds."
    } 
  } else if (healthPoints >= 15) {
    "looks pretty hurt."
  } else { 
    "is in awful condition!"
  } 
  // Player status
  println(name + " " + healthStatus)
} 
```

## range

``` kotlin
fun main(args: Array<String>) {
  for (i in 1 until 3) {
      println(i)
  }
  // 1
  // 2

  println(3 in 1 until 3)     // false
  println(3 in 3 downTo 1)    // true
  println(3 in 1..3)          // true
  println(3 !in 1..3)         // false
}
```

## when

``` kotlin
fun main(args: Array<String>) {
  val point = 100;
  val message = when (point) {
      100 -> "level0"
      in 90..99 -> "level1"
      in 80..89 -> "level2"
      in 70..79 -> "level3"
      in 60..69 -> "level4"
      else -> "level5"
  }
  println(message)            // level0
}
```

## string模板

``` kotlin
fun main(args: Array<String>) {
  val message = "Hello World!"
  println("\$message = $message")
  // $message = Hello World!
} 
```

## 默认值参

``` kotlin
private fun castFireball(numFireballs: Int = 2) { 
  println("A glass of Fireball springs into existence. (x$numFireballs)") 
} 
```

## 单表达式函数

``` kotlin
private fun castFireball(numFireballs: Int = 2) = println("A glass of Fireball springs into existence. (x$numFireballs)")
```

## 具名函数参数

如果不用具名函数值参，就必须按函数头的定义，严格按顺序传入值参。而有了具名
函数值参，传入值参时就可以不管函数头的参数顺序了。

``` kotlin
printPlayerStatus("NONE", true, "Madrigal", status)

printPlayerStatus(auraColor = "NONE",
                  isBlessed = true,
                  name = "Madrigal",
                  healthStatus = status)

printPlayerStatus(healthStatus = status,
                  auraColor = "NONE",
                  name = "Madrigal",
                  isBlessed = true)
```

## Unit

Unit 表示一个函数不返回任何东西，同时，也能兼容需要和一些类型打交道的泛型函数。

## Nothing

类似于 Unit，Nothing 类型的函数也不返回任何东西。但这是它们唯一相同的地方。在编译器看来，Nothing 就意味着函数不可能成功执行完成，它要么抛出异常，要么因某个原因再也返不回调用处。