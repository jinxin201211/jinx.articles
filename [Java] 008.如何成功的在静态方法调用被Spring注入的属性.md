## <center>**如何成功的在静态方法调用被Spring注入的属性**</center>

程序中有个记录日志的静态方法，如下：

```java
public class AuditLogEventHolderUtil {
    public static void PK() {
        AuditLogEventHolder.get().getAuditDescInfo().setMessageTemplate("用户[${userName!''}]在时间[${(startTimeAsDate?string('yyyy-MM-dd HH:mm:ss'))!}]，通过IP[${ip!''}]，访问菜单[${commonAttribute.category.categoryName!''}]，操作类型[${commonAttribute.operationName!''}]，操作结果[${commonAttribute.operationStatus!''}],对象主键[" + UUIDUtil.getUuid() + "]<#if commonAttribute.detailMessage >，操作详情[${commonAttribute['detailMessage']!''}]</#if>。");
    }
}
```

现在需要把这个方法修改一下，需要根据当前环境是否开启日志来决定是否记录日志，如果改成这样：

```java
@Component
public class AuditLogEventHolderUtil {
    @Value("${acloud.audit.enabled}")
    public static Boolean enableAuditLogPK;

    public static void PK() {
        if (enableAuditLogPK) {
            AuditLogEventHolder.get().getAuditDescInfo().setMessageTemplate("用户[${userName!''}]在时间[${(startTimeAsDate?string('yyyy-MM-dd HH:mm:ss'))!}]，通过IP[${ip!''}]，访问菜单[${commonAttribute.category.categoryName!''}]，操作类型[${commonAttribute.operationName!''}]，操作结果[${commonAttribute.operationStatus!''}],对象主键[" + UUIDUtil.getUuid() + "]<#if commonAttribute.detailMessage >，操作详情[${commonAttribute['detailMessage']!''}]</#if>。");
        }
    }
}
```

程序能运行起来，但是enableAuditLogPK的值为null，因为Spring不支持注入静态变量。而enableAuditLogPK必须定义为静态变量，因为静态方法只能调用静态成员变量。

我们都知道Spring注入有三种方式，1.属性注入，2.set方法注入，3.构造器注入，既然直接用注解注入不行，那么就采用其他两种方式试一试。

- set方法注入：

```java
@Component
public class AuditLogEventHolderUtil {
    public static Boolean enableAuditLogPK;

    @Value("${acloud.audit.enabled}")
    public void setEnableAuditLogPK(boolean enabled) {
        AuditLogEventHolderUtil.enableAuditLogPK = enabled;
    }

    public static void PK() {
        if (enableAuditLogPK) {
            AuditLogEventHolder.get().getAuditDescInfo().setMessageTemplate("用户[${userName!''}]在时间[${(startTimeAsDate?string('yyyy-MM-dd HH:mm:ss'))!}]，通过IP[${ip!''}]，访问菜单[${commonAttribute.category.categoryName!''}]，操作类型[${commonAttribute.operationName!''}]，操作结果[${commonAttribute.operationStatus!''}],对象主键[" + UUIDUtil.getUuid() + "]<#if commonAttribute.detailMessage >，操作详情[${commonAttribute['detailMessage']!''}]</#if>。");
        }
    }
}
```

- 构造器注入

```java
@Component
public class AuditLogEventHolderUtil {
    public static Boolean enableAuditLogPK;

    public AuditLogEventHolderUtil(@Value("${acloud.audit.enabled}") boolean enabled) {
        enableAuditLogPK = enabled;
    }

    public static void PK() {
        if (enableAuditLogPK) {
            AuditLogEventHolder.get().getAuditDescInfo().setMessageTemplate("用户[${userName!''}]在时间[${(startTimeAsDate?string('yyyy-MM-dd HH:mm:ss'))!}]，通过IP[${ip!''}]，访问菜单[${commonAttribute.category.categoryName!''}]，操作类型[${commonAttribute.operationName!''}]，操作结果[${commonAttribute.operationStatus!''}],对象主键[" + UUIDUtil.getUuid() + "]<#if commonAttribute.detailMessage >，操作详情[${commonAttribute['detailMessage']!''}]</#if>。");
        }
    }
}
```