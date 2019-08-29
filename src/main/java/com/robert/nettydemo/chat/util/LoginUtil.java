package com.robert.nettydemo.chat.util;


import com.robert.nettydemo.chat.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

public class LoginUtil {

    /**
     * 标记channel已经登录
     *
     * @param channel
     */
    public static void markLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    /**
     * channel 客户是否已登录
     *
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> attribute = channel.attr(Attributes.LOGIN);
        return attribute.get() != null;
    }
}
