package com.robert.nettydemo.chat.attribute;

import com.robert.nettydemo.chat.bean.Session;
import io.netty.util.AttributeKey;

public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
