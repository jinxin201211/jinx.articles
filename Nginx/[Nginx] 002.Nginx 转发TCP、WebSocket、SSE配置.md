## <center>**[Nginx] 002.Nginx 转发TCP、WebSocket、SSE配置**</center>

[使用Nginx为TCP/WebSocket协议做反向代理和几个易踩的坑](https://blog.csdn.net/CharlesSimonyi/article/details/90122916)

### 转发TCP

``` conf
stream {
  proxy_timeout 30m;
  server {
      listen 8080;
      proxy_pass localhost:55328;
  }
}
```

---

### 转发WebSocket

一个是启用`HTTP 1.1`，因为`Nginx`对`HTTP`的反向代理，默认使用`HTTP 1.0`连接到后端，那样没法保持长连接，后端作出`HTTP`响应后，连接就被掐断了，所以启用`HTTP 1.1`以支持长连接。

`Upgrade` 和 `Connection`，为什么要让`Nginx`加这个请求头，对于`WebSocket`协议，客户端不是已经加了`Upgrade`和`Connection`请求头了吗？那是因为根据`HTTP`协议规范，`Upgrade`和`Connection`属于`hop-by-hop`请求头，`Nginx`作为中间的代理，按照规范不能直接转发`hop-by-hop` `header` ，所以需要我们手工强制设定。

``` conf
proxy_set_header Upgrade $http_upgrade;
proxy_set_header Connection "upgrade";
proxy_http_version 1.1;
```

---

### 转发SSE

``` conf
proxy_set_header Upgrade $http_upgrade;
proxy_set_header Connection "upgrade";
proxy_http_version 1.1;

proxy_set_header Connection;
chunked_transfer_encoding off;
proxy_cache off;
```
