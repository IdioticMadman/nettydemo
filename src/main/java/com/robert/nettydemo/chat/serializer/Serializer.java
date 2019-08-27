package com.robert.nettydemo.chat.serializer;

public interface Serializer {

    /**
     * 默认序列化对象
     */
    Serializer DEFAULT = new JsonSerializer();
    /**
     * 序列化算法
     *
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * Java对象转成二进制
     *
     * @param object
     * @return
     */
    byte[] serialize(Object object);


    /**
     * 二进制对象转换成Java对象
     *
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
