# Java 中的 System.out.println 为何会影响内存可见性

本文主要说明 System.out.println 为啥会影响内存的可见性？

先看示例代码：

```java
public class SoutTest {
    private static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println("come in");
            while (flag) {
            }
            System.out.println("flag is false, t1 stopped");
        }, "t1").start();

        TimeUnit.MILLISECONDS.sleep(2000L);

        flag = false;
        System.out.println("flag changed to false");
    }
}
```

这段代码中，t1 线程会一直循环，直到 flag 为 false，而主线程会在 2 秒后改变 flag 的值为 false，但是实际上 t1 线程并不会停止，`flag is false, t1 stopped`这段话不会打印出来，因为 t1 线程感知不到主线程中 flag 的变化。

接下来稍微改一下这段代码，在 t1 线程的 while 循环中加入 System.out.println，如下：

```java
public class SoutTest {
    private static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println("come in");
            while (flag) {
                System.out.println("x");
            }
            System.out.println("flag is false, t1 stopped");
        }, "t1").start();

        TimeUnit.MILLISECONDS.sleep(2000L);

        flag = false;
        System.out.println("flag changed to false");
    }
}
```

再次运行，可以看到，2 秒后，t1 线程停止了循环，打印出了`flag is false, t1 stopped`。

说明 while 循环里的`System.out.println`会影响内存可见性，让 t1 线程感知到了主线程中 flag 的变化。可是这是为什么？

原因就在`System.out.println`方法内部，这是`println`的源码：

```java
public void println(String x) {
    synchronized (this) {
        print(x);
        newLine();
    }
}
```

原来`println`有一个上锁的操作。

使用了 `synchronized` 上锁会做以下操作：

1. 获得同步锁；
2. 清空工作内存；
3. 从主内存拷贝对象副本到工作内存；
4. 执行代码（计算或者输出等）；
5. 刷新主内存数据；
6. 释放同步锁。
