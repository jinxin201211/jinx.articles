## <center>**[Nginx] 002.Nginx 转发TCP、WebSocket、SSE配置**</center>

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
