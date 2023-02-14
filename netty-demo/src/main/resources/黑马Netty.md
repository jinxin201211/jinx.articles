## NIO基础（non-blocking IO 非阻塞IO）
### 三大组件
 1. Channel & Buffer
 2. Selector

### ByteBuffer结构
- capacity
- position
- limit

`flip()将position重置，limit置为最后写入位置；clear()重置position和limit；compact()将未读的内容前移，position从剩余部分开始，limit重置`

### flip,clear,rewind
- rewind()方法将position置0，清除mark，它的作用在于为提取Buffer的有效数据做准备。
- clear()方法将position置0，清除mark，与rewind()方法不同的是，它还会将limit置为capacity的大小，这个方法用于“清空”缓冲区。
- flip()方法将position置0，清除mark，然后将limit置为position的大小，通常在读写转换时使用。
