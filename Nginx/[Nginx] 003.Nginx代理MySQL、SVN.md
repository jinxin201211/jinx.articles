# Nginx代理MySQL、SVN

## 代理MySQL

``` conf
stream {
    server {
      listen 23306;
      proxy_pass 11.16.2.90:13306;
    }
}
```

---

## 代理SVN

``` conf
    server {
      listen 81;
      server_name localhost;

      location / {
        proxy_pass http://11.16.2.90:81/;
      }
    }
```