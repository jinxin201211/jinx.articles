# $使用 javassist 动态创建 Java 类$

需求背景：我们有一些数据需要第三方提供，第三方是通过 WebService 的方式通知我们可以拉取数据，WebService 服务确定了请求和返回的格式，只能通过 WebService 方法名来区分应该拉取哪张表，并且数据库表可能会有变化，所以需要 WebService 和数据库表名做成可配置的。

动态创建 WebService 类的方法：

```java
public class DynamicWebserviceGenerator {

    static int incr = 0;

    public Class<?> createDynamicClazz(String serviceName, List<WebServiceInfo> serviceMethods) throws Exception {
        ClassPool pool = ClassPool.getDefault();

        // 创建类
        CtClass cc = pool.makeClass("com.sgcc.md.gxh.yxws.service." + serviceName + (incr++)); //让每次重新注册webservice时生成的类名不一样

        // 继承ServiceMould的doClient方法
//        CtClass ccParent = pool.get("classpath:com.sgcc.md.gxh.yxws.service.ServiceMould");
        pool.insertClassPath(new ClassClassPath(ServiceMould.class));
        CtClass ccParent = pool.get(ServiceMould.class.getName());
        cc.setSuperclass(ccParent);

        ClassFile ccFile = cc.getClassFile();
        ConstPool constPool = ccFile.getConstPool();

        // 添加类注解
        AnnotationsAttribute bodyAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation bodyAnnot = new Annotation("javax.jws.WebService", constPool);
        bodyAnnot.addMemberValue("name", new StringMemberValue(serviceName, constPool));
        bodyAnnot.addMemberValue("serviceName", new StringMemberValue(serviceName, constPool));
        bodyAnnot.addMemberValue("targetNamespace", new StringMemberValue("http://md.aostar.com/" + serviceName, constPool));
        bodyAttr.addAnnotation(bodyAnnot);
        ccFile.addAttribute(bodyAttr);

        // 创建方法
        CtClass ccStringType = pool.get("java.lang.String");
        for (WebServiceInfo methodInfo : serviceMethods) {
            // 参数：  1：返回类型  2：方法名称  3：传入参数类型  4：所属类CtClass
            CtMethod ctMethod = new CtMethod(ccStringType, methodInfo.getMethodname(), new CtClass[]{ccStringType}, cc);
            ctMethod.setModifiers(Modifier.PUBLIC);
            StringBuilder body = new StringBuilder();
            body.append("{\n");
            // $1代表方法的参数
            body.append("    return doService($1, \"" + methodInfo.getTablename() + "\", " + (methodInfo.getIsdelaysyncs() == null ? 0 : methodInfo.getIsdelaysyncs()) + ", \"" + methodInfo.getServicename() + "\", \"" + methodInfo.getMethodname() + "\");");
            body.append("\n}");
            ctMethod.setBody(body.toString());
            cc.addMethod(ctMethod);

            // 添加方法注解
            AnnotationsAttribute methodAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
            Annotation methodAnnot = new Annotation("javax.jws.WebMethod", constPool);
            methodAnnot.addMemberValue("operationName", new StringMemberValue(methodInfo.getMethodname(), constPool));
            methodAttr.addAnnotation(methodAnnot);
            Annotation resultAnnot = new Annotation("javax.jws.WebResult", constPool);
            resultAnnot.addMemberValue("name", new StringMemberValue("result", constPool));
            methodAttr.addAnnotation(resultAnnot);
            ctMethod.getMethodInfo().addAttribute(methodAttr);

            // 添加参数注解
            ParameterAnnotationsAttribute parameterAtrribute = new ParameterAnnotationsAttribute(constPool, ParameterAnnotationsAttribute.visibleTag);
            Annotation paramAnnot = new Annotation("javax.jws.WebParam", constPool);
            paramAnnot.addMemberValue("name", new StringMemberValue("arg0", constPool));
            Annotation[][] paramArrays = new Annotation[1][1];
            paramArrays[0][0] = paramAnnot;
            parameterAtrribute.setAnnotations(paramArrays);
            ctMethod.getMethodInfo().addAttribute(parameterAtrribute);
        }

        return cc.toClass();
    }
}
```

WebService 基类：

```java
@Component
@WebService(name = "SeviceMould", targetNamespace = "http://aostar.md", serviceName = "SeviceName")
public class ServiceMould {
    @Resource
    SyncDataInfo syncDataInfo;
    @Resource
    WebServiceLogService webServiceLogService;

    // public方法会被cxf发布出去
    protected String doService(String data, String tableName, int isDelaySync, String serviceName, String methodName) {
        // SyncDataInfo syncDataInfo = new SyncDataInfo(data,tableName,isDelaySync);
        syncDataInfo.setDefultInfo();
        syncDataInfo.setType(1);
        syncDataInfo.setParamXML(data);
        syncDataInfo.setTableName(tableName);
        syncDataInfo.setIsDelaySync(isDelaySync);
        syncDataInfo.setServiceName(serviceName);
        syncDataInfo.setMethodName(methodName);

        ////计入日志
        webServiceLogService.log(syncDataInfo);
        try {
            //解析传入参数
            syncDataInfo.vailXMLParam();
            ////日志
            webServiceLogService.log(syncDataInfo);
            //执行数据库同步
            if (syncDataInfo.getIsDelaySync() == 0 && !StringUtils.isEmpty(syncDataInfo.tableName)) {
                syncDataInfo.syncData();
                ////改变日志
                webServiceLogService.log(syncDataInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //记录日志
            webServiceLogService.log(syncDataInfo);
        }
        //返回结果
        return syncDataInfo.getReturnXML();
    }
}
```
