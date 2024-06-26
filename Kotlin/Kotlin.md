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

## 匿名函数与函数类型

---
- 匿名类型：
``` kotlin
fun main(args: Array<String>) {
  val greetingFunction: () -> String = {
    val currentYear = 2018
    "Welcome to SimVillage, Mayor! (copyright $currentYear)"
  }
  println(greetingFunction())
}
```
---

> 和具名函数不一样，除了极少数的情况外，匿名函数不需要 return 关键字来返回数据。<br>
没有 return 关键字，为了返回数据，匿名函数会隐式或自动返回函数体最后一行语句的结果。<br>
之所以不能用 return 关键字，是因为编译器不知道返回数据究竟是来自调用匿名函数的函数，还是匿名函数本身。

> 定义只有一个参数的匿名函数时，可以使用 it 关键字来表示参数名。

## !!.操作符

`!!.` 操作符也能用来在可空类型上调用函数。但我要给你个警告：相比安全调用操作符，`!!.` 操作符太激进，一般应该避免使用。视觉上看，代码中的 `!!.` 操作符也给人语气很重的感觉，因为它真的很危险。如果你用了 `!!.`，就是在向编译器宣布：“万一我使唤干活的东西不存在，我要求你立刻抛出空指针异常！！”

## Kotlin内置的先决条件函数

| 函 数 | 描 述 |
| -- | --|
| checkNotNull | 如果参数值为 `null`，则抛出 `IllegalStateException` 异常，否则返回非 `null` 值 |
| require | 如果参数值为 `false`，则抛出 `IllegalArgumentException` 异常 |
| requireNotNull | 如果参数值为 `null`，则抛出 `IllegalStateException` 异常，否则返回非 `null` 值 |
| error | 如果参数值为 `null`，则抛出 `IllegalStateException` 异常并输出错误信息，否则返回非 `null` 值 |
| assert | 如果参数值为 `false`，则抛出 `AssertionError` 异常，并打上断言编译器标记 |

## 标准库函数

![apply,let,run,with,also](/imgs/kotlin/a97360c74831491d82bba23deb2ca958.jfif)

| 函 数 | 是否传receiver值给lambda | 是否有相关作用域 | 返 回 |
| --- | --- | --- | --- |
| let | 是 | 否 | lambda结果 |
| apply | 否 | 是 | 接收者对象 |
| run | 否 | 是 | lambda结果 |
| with | 否 | 是 | lambda结果 |
| also | 是 | 否 | 接收者对象 |
| takeIf | 是 | 否 | 可空类型接收者对象 |
| takeUnless | 是 | 否 | 可空类型接收者对象 |

> run 函数的另一版本（不常用）无需接收者，不传递接收者值参，没有作用域限制，返回 lambda 结果值。

> 不能以 hello.with {..}这样的方式调用 with 函数，正确的调用方式像这样：with ("hello"){..}，这里，第一个值参就是接收者，第二个是 lambda 表达式。鉴于其独特性，建议尽量避免使用它。