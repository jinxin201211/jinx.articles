### 在SpringBoot中，实现从数据库中读取配置，并且不重启程序动态更新配置

[在Spring-boot中，为@Value注解添加从数据库读取properties支持](https://www.shuzhiduo.com/A/l1dypylA5e/)

#### 1.问题背景：

程序中原本是使用nacos作为配置中心，是可以动态更新配置的。但是由于某些原因，在程序运行时，我们并不能操作nacos，也不能修改配置文件重新启动程序，所以就要自己实现这个功能。

#### 2.解决方案：

因为不能改nacos，也不能改配置文件，所以就将程序运行种可能会修改的配置放到数据库中。一种方法是在使用的地方全部改成从数据库去取值，但是这样每次都要读数据库，实际的值并不是每次都会变。改进一下就是程序启动时一次将数据库中的所有配置读取出来放进一个全局对象中，使用这个全局对象，配置修改后更新这个全局对象，但是这样代码修改还是比较大，因为之前是用@Value注解来读取配置。所以需要寻找一种将数据库中的属性解析到@Value的方式。

```java
    @Bean
    public BusinessClient businessClient (@Value("${baseUrl}") String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return retrofit.create(BusinessClient .class);
    }
```

`@Value`是Spring的bean `PropertySourcesPlaceholder`实现的，Spring boot已经在初始化时帮我们自动实例化了该bean。`PropertySourcesPlaceholder`在解析属性时，都是从`ConfigurableEnvironment`中进行寻找的。当`ConfigurableEnvironment`没有存在的属性时，${}写法的@Value就无法解析了。因此，需要通过特殊的处理，将存储在数据库中的属性注入到`ConfigurableEnvironment`中。本文定义了一个LoadFromDatabasePropertyConfig类实现该功能，其代码如下：

```java
    @Configuration
    @Slf4j
    public class LoadFromDatabasePropertyConfig {
        @Autowired
        private ConfigurableEnvironment env;
        @Autowired
        private SysPropertyResourceMapper propertyResourceMapper;
        @PostConstruct
        public void initializeDatabasePropertySourceUsage() {
            MutablePropertySources propertySources = env.getPropertySources();
            try {
                Map<String, Object> propertyMap = propertyResourceMapper.selectAll().stream()
                        .collect(Collectors.toMap(SysPropertyResource::getPropertyName, SysPropertyResource::getPropertyValue));
                Properties properties = new Properties();
                properties.putAll(propertyMap);
                PropertiesPropertySource dbPropertySource = new PropertiesPropertySource("dbPropertySource", properties);
                Pattern p = Pattern.compile("^applicationConfig.*");
                String name = null;
                boolean flag = false;
                for (PropertySource<?> source : propertySources) {
                    if (p.matcher(source.getName()).matches()) {
                        name = source.getName();
                        flag = true;
                        log.info("Find propertySources ".concat(name));
                        break;
                    }
                }
                log.info("=========================================================================");
                if(flag) {
                    propertySources.addBefore(name, dbPropertySource);
                } else {
                    propertySources.addFirst(dbPropertySource);
                }
            } catch (Exception e) {
                log.error("Error during database properties setup", e);
                throw new RuntimeException(e);
            }
        }
    }
```
上述代码的具体思路是将数据库中的所有需要的属性读出，通过Properties类转换为Spring可用的`PropertiesPropertySource`，并取名为dbPropertySource。随后利用正则匹配，从已有的所有属性中找到名称以applicationConfig开头的属性（该属性即是所有配置在文件中的property所解析成的对象），并将dbPropertySource存储在其之前。这样当文件和数据库中同时存在key相等的属性时，会优先使用数据库中存储的value。
需要注意的是，上述方案提供的属性解析，必须在数据库相关的bean都实例化完成后才可进行。且为了保证bean在实例化时，数据库属性已经被加入到`ConfigurableEnvironment`中去了，必须添加`@DependsOn`注解。上面的BusinessClient的实例化就需更新成：

```java
    @Bean
    @DependsOn("loadFromDatabasePropertyConfig")
    public BusinessClient businessClient (@Value("${baseUrl}") String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return retrofit.create(BusinessClient .class);
    }
```

至此，就实现了`@Value`从数据库加载配置值，接下来就要解决如何在程序运行时更新@Value的值。这时候就需要用到`@RefreshScope`注解，刷新时一种方式是使用`spring-boot-starter-actuator`提供的HTTP接口`actuator/refresh`来刷新配置值，但是这种方法不会主动去更新dbPropertySource中的属性，所以我们就需要自己重新读取数据库去更新dbPropertySource，然后再刷新@Value，具体代码如下：

```java
@RestController
@RequestMapping("/customConfig")
public class SysReloadConfigController {
    @Resource
    private org.springframework.cloud.context.scope.refresh.RefreshScope scope;

    @PostMapping("/resload")
    public HttpResult reloadConfig() throws Exception {
        reloadConfig();//LoadFromDatabasePropertyConfig.initializeDatabasePropertySourceUsage单独抽出的公共方法
        scope.refreshAll();
        return HttpResult.success("刷新成功");
    }
}
```

以上就是整体原因的简单分析及解决方案。