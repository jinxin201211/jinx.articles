## <center>**Java 并发包 CountDownLatch、CyclicBarrier 和 Semaphore 的使用**</center>

[Java 并发包 CountDownLatch、CyclicBarrier、Semaphore 原理解析](https://baijiahao.baidu.com/s?id=1667358787034051395&wfr=spider&for=pc)

### 一、 CountDownLatch

`CountDownLatch`可以理解为是同步计数器，作用是允许一个或多个线程等待其他线程执行完成之后才继续执行，比如打 dota、LoL 或者王者荣耀时，创建了一个五人房，只有当五个玩家都准备了之后，游戏才能正式开始，否则游戏主线程会一直等待着直到玩家全部准备。在玩家没准备之前，游戏主线程会一直处于等待状态。如果把 CountDownLatch 比做此场景都话，相当于开始定义了匹配游戏需要 5 个线程，只有当 5 个线程都准备完成了之后，主线程才会开始进行匹配操作。

`CountDownLatch`案例如下：

```java
    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        new Thread(() -> {
            for (int i = 0; i < 12; i++) {
                new Thread(() -> {
                    try {
                        Thread.sleep(new Random().nextInt(9999) + 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    long count = countDownLatch.getCount();
                    countDownLatch.countDown();
                    if (count != 0) {
                        log.debug("线程： " + Thread.currentThread().getName() + " 组队准备，还需等待 " + countDownLatch.getCount() + " 人准备");
                    } else {
                        log.debug("线程： " + Thread.currentThread().getName() + " 组队准备，房间已满不可进入");
                    }
                }).start();
            }
        }).start();


        new Thread(() -> {
            try {
                log.debug("游戏房间等待玩家加入...");
                countDownLatch.await();
                log.debug("游戏房间已锁定...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        log.debug("等待玩家准备中...");
        countDownLatch.await();
        log.debug("游戏准备中...");
    }
```

执行结果如下：

![CountDownLatch](/imgs/concurrent/coundDownLatch.png)

本案例中有两个线程都调用了`countDownLatch.await()`方法，则这两个线程都会被阻塞，直到条件达成。当 5 个线程调用`countDown`方法之后，达到了计数器的要求，则后续再执行`countDown`方法的效果就无效了，因为`CountDownLatch`仅一次有效。

### 二、 CyclicBarrier

`CyclicBarrier`可以理解为一个循环同步屏障，定义一个同步屏障之后，当一组线程都全部达到同步屏障之前都会被阻塞，直到最后一个线程达到了同步屏障之后才会被打开，其他线程才可继续执行。

还是以 dota、LoL 和王者荣耀为例，当第一个玩家准备了之后，还需要等待其他 4 个玩家都准备，游戏才可继续，否则准备的玩家会被一直处于等待状态，只有当最后一个玩家准备了之后，游戏才会继续执行。

`CyclicBarrier`案例如下：

```java
    public static void main(String[] args) throws Exception {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        for (int i = 0; i < 12; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(new Random().nextInt(9999) + 1000);
                    log.info("线程： " + Thread.currentThread().getName() + " 组队准备，当前 " + (cyclicBarrier.getNumberWaiting() + 1) + " 人已进入");
                    cyclicBarrier.await();
                    log.info("线程： " + Thread.currentThread().getName() + " 开始组队游戏");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
```

执行结果如下：

![CyclicBarrier](/imgs/concurrent/cyclicBarrier.png)

本案例中定义了达到同步屏障的线程为 5 个，每当一个线程调用了`cyclicBarrier.await()`方法之后表示该线程已达到屏障，此时当前线程会被阻塞，只有当最后一个线程调用了 await 方法之后，被阻塞的其他线程才会被唤醒继续执行。

另外`CyclicBarrier`是循环同步屏障，同步屏障打开之后立马会继续计数，等待下一组线程达到同步屏障。而`CountDownLatch`仅单次有效。

### 三、 Semaphore

`Semaphore`字面意思是信号量，实际可以看作是一个限流器，初始化`Semaphore`时就定义好了最大通行证数量，每次调用时调用方法来消耗，业务执行完毕则释放通行证，如果通行证消耗完，再获取通行证时就需要阻塞线程直到有通行证可以获取。

比如银行柜台的窗口，一共有 5 个窗口可以使用，当窗口都被占用时，后面来的人就需要排队等候，直到有窗口用户办理完业务离开之后后面的人才可继续争取。模拟代码如下：

```java
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        log.debug("初始化 {} 个银行柜台窗口", 5);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    log.debug("用户 {} 排队等待", Thread.currentThread().getName());
                    semaphore.acquire(1);
                    log.debug("用户 {} 占用窗口，开始办理业务", Thread.currentThread().getName());
                    Thread.sleep(new Random().nextInt(9999) + 5000);
                    semaphore.release();
                    log.debug("用户 {} 离开窗口", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
```

执行结果如下：

![Semaphore](/imgs/concurrent/semaphore.png)

可以看出前 5 个线程可以直接占用窗口，但是后 5 个线程需要等待前面的线程离开了窗口之后才可占用窗口。

`Semaphore`调用`acquire`方法获取许可证，可以同时获取多个，但是也需要对应的释放多个，否则会造成其他线程获取不到许可证的情况。一旦许可证被消耗完，那么线程就需要被阻塞，直到许可证被释放才可继续执行。

另外`Semaphore`还具有公平模式和非公平模式两种用法，公平模式则遵循 FIFO 原则先排队的线程先拿到许可证；非公平模式则自行争取。
