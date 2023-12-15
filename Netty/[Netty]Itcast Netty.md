# **_NIO 基础（non-blocking IO 非阻塞 IO）_**

---

## **三大组件**

1. Channel & Buffer
2. Selector

---

## **ByteBuffer 结构**

- capacity
- position
- limit

`flip()将position重置，limit置为最后写入位置；`

`clear()重置position和limit；`

`compact()将未读的内容前移，position从剩余部分开始，limit重置`

---

## **flip,clear,rewind**

- rewind()方法将 position 置 0，清除 mark，它的作用在于为提取 Buffer 的有效数据做准备。
- clear()方法将 position 置 0，清除 mark，与 rewind()方法不同的是，它还会将 limit 置为 capacity 的大小，这个方法用于“清空”缓冲区。
- flip()方法将 position 置 0，清除 mark，然后将 limit 置为 position 的大小，通常在读写转换时使用。

---

## **什么是粘包、半包**

在实际的网络开发中或者在面试中，最开始使用 TCP 协议时经常会碰上粘包和半包的情况，因此我们有必要了解一下什么是粘包，什么是半包，以及如何去解决。

> 粘包：顾名思义就是客户端和服务端之间发送的数据包粘在了一起，原本应该分多条发送的数据包粘在了一起发送。
> 半包：指的是一条数据包被分割成了多条发送。

粘包和半包发生的根本原因是在 TCP 协议中，只有流的概念，没有包的概念，消息发送到缓冲区之后是没有边界的说法的，因此就会发生粘包和半包。

解决方法：定长解码器，分隔符解码器，LTC 解码器

---

FileChannel 只能工作在阻塞模式下，不能跟 Selector 一起用。

---

客户端关闭时会向服务器发一个 read 事件，导致服务器异常

---

## **多路复用**

单线程可以配合 Selector 完成对多个 Channel 可读写事件的监控，这称之为多路复用。

- 多路复用仅针对网络 IO，普通文件 IO 没法利用多路复用
- 如果不用 Selector 的非阻塞模式，线程大部分时间都在做无用功，而 Selector 能够保证
  - 有可连接事件时才去连接
  - 有可读事件时才去读取
  - 有可写事件时才去写入
    - 限于网络传输能力，Channel 未必时时可写，一旦 Channel 可写，会触发 Selector 的可写事件

---
