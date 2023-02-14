## ***NIO基础（non-blocking IO 非阻塞IO）***

---

### **三大组件**
 1. Channel & Buffer
 2. Selector

---

### **ByteBuffer结构**
- capacity
- position
- limit

`flip()将position重置，limit置为最后写入位置；`

`clear()重置position和limit；`

`compact()将未读的内容前移，position从剩余部分开始，limit重置`

---

### **flip,clear,rewind**
- rewind()方法将position置0，清除mark，它的作用在于为提取Buffer的有效数据做准备。
- clear()方法将position置0，清除mark，与rewind()方法不同的是，它还会将limit置为capacity的大小，这个方法用于“清空”缓冲区。
- flip()方法将position置0，清除mark，然后将limit置为position的大小，通常在读写转换时使用。

---

### **什么是粘包、半包**
在实际的网络开发中或者在面试中，最开始使用TCP协议时经常会碰上粘包和半包的情况，因此我们有必要了解一下什么是粘包，什么是半包，以及如何去解决。

> 粘包：顾名思义就是客户端和服务端之间发送的数据包粘在了一起，原本应该分多条发送的数据包粘在了一起发送。

> 半包：指的是一条数据包被分割成了多条发送。

粘包和半包发生的根本原因是在TCP协议中，只有流的概念，没有包的概念，消息发送到缓冲区之后是没有边界的说法的，因此就会发生粘包和半包。

解决方法：定长解码器，分隔符解码器，LTC解码器

---