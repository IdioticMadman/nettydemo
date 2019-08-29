package com.robert.nettydemo.chat.util;

import com.robert.nettydemo.chat.attribute.Attributes;
import com.robert.nettydemo.chat.bean.Session;
import com.sun.istack.internal.Nullable;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {

    private static final Map<String, Channel> userIdChannelMap =
            new ConcurrentHashMap<String, Channel>();

    /**
     * 绑定会话
     *
     * @param session
     * @param channel
     */
    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }


    /**
     * 解除channel
     *
     * @param channel
     */
    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    /**
     * 获取用户对应的channel
     *
     * @param userId
     * @return
     */
    @Nullable
    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

    /**
     * 根据channel获取已绑定的session
     *
     * @param channel
     * @return
     */
    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    /**
     * 根据channel，判断是否有session属性
     *
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION)
                && channel.attr(Attributes.SESSION).get() != null;
    }


}
