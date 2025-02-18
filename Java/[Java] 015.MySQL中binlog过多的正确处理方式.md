# MySQL中binlog过多的正确处理方式

## 前言

MySQL读写量很大，导致了binlog日志量也很大，本来服务器专门用来做数据库服务器的，空间也是分到了2个T，最近发现服务器空间越来越小，于是排查到了是MySQL的数据路径下产生了太多太多的binlog文件，于是总结了此篇文章，正确处理正式环境binlog文件过多导致服务器空间不够的方式。

## 一、先看看binlog是怎么吞噬服务器空间的。

系统上线五个月，产生了4443个binlog文件，每个文件大多是1G大小。恐怖如斯。


## 二、备份binlog文件

首先这是生产环境，不能随便执行什么删除或者修改的操作，在这里也提醒一下大家，生产环境的东西，一定要谨慎操作，一旦误操作，后果可能不堪设想。

所以我们先进行备份。

系统总共产生了1.7T的binlog文件，不能直接下载（自己电脑也装不下），所以就先打成压缩包，在进行传输备份。

命令：`tar -zcvf mysql.tar.gz mysql/mysql-bin.000*`

mysql/mysql-bin.000*是数据库数据根路径下文件名以mysql-bin.000开头的文件

mysql.tar.gz 是压缩包的名称


## 三、binlog过期时间

命令：`show variables like 'expire_logs_days';`

0：永不过期

10：保留最近10天的binlog文件


## 四、设置自动清理binlog

修改配置文件，在[mysqld]标签下增加内容：

`expire_logs_days=10`

`max_binlog_size=1024M`

注：重启数据库生效

## 五、手动清理binlog

1、登录到MySQL服务中


2、执行命令

`purge binary logs to 'mysql-bin.000388';`

意为清理掉mysql-bin.000388之前的binlog文件

`purge binary logs before '2023-03-12 23:59:59';`

意为清理掉指定时间之前的binlog内容

> **注意：** 如果binlog文件占满磁盘导致MySQL重启失败，并且磁盘无法扩充，此时无法通过mysql命令操作，可以手动将binlog文件删除，此时需要注意同时删除.index文件中相应的文件名。
