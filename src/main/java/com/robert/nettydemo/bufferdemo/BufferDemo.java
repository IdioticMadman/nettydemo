package com.robert.nettydemo.bufferdemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class BufferDemo {

    public static void main(String[] args) {
        //--writeIndex--readerIndex--capacity
        //ByteBuf byteBuf
        //markReaderIndex()-resetReaderIndex() markWriterIndex()-resetWriteIndex()  标记位置，以及重置位置

        //getBytes()-readBytes()  setBytes()-writeBytes()  get和set不会影响读写Index，read和write会影响指针位置

        //Netty 的 ByteBuf 是通过引用计数的方式管理的。release()-retain()  标识byteBuf的引用次数，retain+1 release-1

        //slice()- duplicate()- copy()
        //slice 对原有的ByteBuf进行切片，将readableBytes作为maxCapacity
        //duplicate() 复制一份，完全一样。但是底层数据没有做copy，写入数据会影响原有的ByteBuf
        //copy() 复制一份，连同数据

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("allocate ByteBuf(9,100) ", buffer);


        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(1, 2, 3, 4)", buffer);

        buffer.writeInt(12);
        print("writeInt(12)", buffer);

        buffer.writeBytes(new byte[]{5});
        print("writeBytes(5)", buffer);

        //超过capacity 将要进行扩容
        buffer.writeBytes(new byte[]{6});
        print("writeBytes(6)", buffer);

        // get 方法不改变读写指针
        System.out.println("getByte(3) return: " + buffer.getByte(3));
        System.out.println("getShort(3) return: " + buffer.getShort(3));
        System.out.println("getInt(3) return: " + buffer.getInt(3));
        System.out.println();
        print("getByte()", buffer);


        // set 方法不改变读写指针
        buffer.setByte(buffer.readableBytes() + 1, 0);
        print("setByte()", buffer);

        // read 方法改变读指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);
    }

    private static void print(String action, ByteBuf byteBuf) {

        System.out.println("after ================ " + action + " ===============");
        System.out.println("capacity(): " + byteBuf.capacity());
        System.out.println("maxCapacity(): " + byteBuf.maxCapacity());
        System.out.println("isReadable(): " + byteBuf.isReadable());
        System.out.println("readerIndex(): " + byteBuf.readerIndex());
        System.out.println("readableBytes(): " + byteBuf.readableBytes());
        System.out.println("isWritable(): " + byteBuf.isWritable());
        System.out.println("writerIndex(): " + byteBuf.writerIndex());
        System.out.println("writableBytes(): " + byteBuf.writableBytes());
        System.out.println("maxWritableBytes(): " + byteBuf.maxWritableBytes());
        System.out.println();
    }

}
