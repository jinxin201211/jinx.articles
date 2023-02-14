### <center>**在SpringBoot中，实现动态更新定时任务的执行周期**</center>

[解决 @RefreshScope 导致定时任务注解 @Scheduled 失效](https://blog.csdn.net/u012410733/article/details/125985361)

首先`@Scheduled`的cron表达式可以从配置中读取

```java
@Component
public class TaskSchedule {
    @Scheduled(cron = "${scheduled.testTask}")
    public void testTask() {
        System.out.println("从配置中读取cron表达式");
    }
}
```

由于nacos可以动态修改配置值，所以尝试给TaskSchedule加上`@RefreshScope`注解，实际上光是这样并不能生效，虽然scheduled.testTask的值会发生改变，但是定时任务并不会重新加载。

使用`RefreshScopeRefreshedEvent`从config配置服务器成功获取并覆盖值。在这个事件监听方法里面，只需要手动再从Spring容器中获取一次当前Bean即可，因为这样便可以迫使当前Bean重新加载，从而重新初始化定时任务。所以实现需要再实现`ApplicationListener<RefreshScopeRefreshedEvent>`，就能解决定时任务重新加载的问题。

``` java
@Component
@RefreshScope
public class TaskSchedule implements ApplicationListener<RefreshScopeRefreshedEvent> {
    @Scheduled(cron = "${scheduled.testTask}")
    public void testTask() {
        System.out.println("从配置中读取cron表达式");
    }

    @Override
    public void onApplicationEvent(RefreshScopeRefreshedEvent event) {
    }
}
```

以上就是整体原因的简单分析及解决方案。