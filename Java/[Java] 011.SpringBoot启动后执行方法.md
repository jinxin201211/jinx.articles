# SpringBoot 启动后执行方法

转载自 [Springboot 启动后执行方法（4 种）](https://www.cnblogs.com/lizm166/p/16542073.html)

## 一、注解@PostConstruct

使用注解 `@PostConstruct` 是最常见的一种方式，存在的问题是如果执行的方法耗时过长，会导致项目在方法执行期间无法提供服务。

```java
@Component
public class StartInit {
//    @Autowired   可以注入 bean
//    ISysUserService userService;

    @PostConstruct
    public void init() throws InterruptedException {
        Thread.sleep(10 * 1000);//这里如果方法执行过长会导致项目一直无法提供服务
        System.out.println(123456);
    }
}
```

## 二、CommandLineRunner 接口

实现 `CommandLineRunner` 接口 然后在 run 方法里面调用需要调用的方法即可，好处是方法执行时，项目已经初始化完毕，是可以正常提供服务的。

同时该方法也可以接受参数，可以根据项目启动时：`java -jar demo.jar arg1 arg2 arg3` 传入的参数进行一些处理。

```java
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println(Arrays.toString(args));
    }
}
```

## 三、实现 ApplicationRunner 接口

实现 `ApplicationRunner` 接口和实现 `CommandLineRunner` 接口基本是一样的。

唯一的不同是启动时传参的格式， `CommandLineRunner` 对于参数格式没有任何限制， `ApplicationRunner` 接口参数格式必须是：`–key=value`

```java
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Set<String> optionNames = args.getOptionNames();
        for (String optionName : optionNames) {
            List<String> values = args.getOptionValues(optionName);
            System.out.println(values.toString());
        }
    }
}
```

## 四、实现 ApplicationListener

实现接口 `ApplicationListener` 方式和实现 `ApplicationRunner` ， `CommandLineRunner` 接口都不影响服务，都可以正常提供服务，注意监听的事件，通常是 `ApplicationStartedEvent` 或者 `ApplicationReadyEvent` ，其他的事件可能无法注入 bean。

```java
@Component
public class ApplicationListenerImpl implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("listener");
    }
}
```

## 五、四种方式的执行顺序

注解方式 `@PostConstruct` 始终最先执行，此时 Spring 还没有启动完成。

如果监听的是 `ApplicationStartedEvent` 事件，则一定会在 `CommandLineRunner` 和 `ApplicationRunner` 之前执行。

如果监听的是 `ApplicationReadyEvent` 事件，则一定会在 `CommandLineRunner` 和 `ApplicationRunner` 之后执行。

 `CommandLineRunner` 和 `ApplicationRunner` 默认是 `ApplicationRunner` 先执行，如果双方指定了 `@Order` 则按照 `@Order` 的大小顺序执行，大的先执行。
