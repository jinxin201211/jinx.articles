# $Nginx 简单双机热备$

```conf
upstream  testproxy  {
    server   127.0.0.1:8080;
    server   192.168.1.15:8080 backup;
}
```

只要在希望成为后备的服务器 ip 后面多添加一个 backup 参数，这台服务器就会成为备份服务器。
在平时不使用，nginx 不会给它转发任何请求。只有当其他节点全部无法连接的时候，nginx 才会启用这个节点。
一旦有可用的节点恢复服务，该节点则不再使用，又进入后备状态。

可以两台机子互为热备，平时各自负责各自的服务。在做上线更新的时候，关闭一台服务器的 tomcat 后，nginx 自动把流量切换到另外一台服务的后备机子上，从而实现无痛更新，保持服务的持续性，提高服务的可靠性。

关于这个参数的官档说明：
[Module ngx_http_upstream_module](http://wiki.nginx.org/NginxHttpUpstreamModule)
backup - (0.6.7 or later) only uses this server if the non-backup servers are all down or busy (cannot be used with the directive ip_hash)
