package com.robert.nettydemo.chat.util;

import com.robert.nettydemo.chat.attribute.Attributes;
import com.robert.nettydemo.chat.bean.Session;
import com.sun.istack.internal.Nullable;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {

    private static final Map<String, Channel> userIdChannelMap =
            new ConcurrentHashMap<String, Channel>();

    private static final Map<String, ChannelGroup> groupIdChannelMap = new ConcurrentHashMap<String, ChannelGroup>();

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
            Session session = getSession(channel);
            userIdChannelMap.remove(session.getUserId());
            channel.attr(Attributes.SESSION).set(null);
            System.out.println(session + " 退出登录！");
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

    /**
     * 缓存group信息
     *
     * @param groupId
     * @param group
     */
    public static void bindChannelGroup(String groupId, ChannelGroup group) {
        groupIdChannelMap.put(groupId, group);
    }

    /**
     * 获取groupChannel
     *
     * @param groupId
     * @return
     */
    public static ChannelGroup getChannelGroup(String groupId) {
        return groupIdChannelMap.get(groupId);
    }
}
