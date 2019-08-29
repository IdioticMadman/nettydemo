package com.robert.nettydemo.chat.protocol.serializer;

import com.alibaba.fastjson.JSON;

public class JsonSerializer implements Serializer {
    public byte getSerializerAlgorithm() {
        return SerializeAlgorithm.JSON;
    }

    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
