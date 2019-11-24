package com.app.crawler.riches.producer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.crawler.riches.BRiches;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DataEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataEventHandler.class);

    @Autowired
    private SocketIOServer socketIOServer;
    public final String typeName = "refreshData";

    //统计在线客户端数量
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    BRiches briches = new BRiches();

    @OnEvent(value = typeName)
    public void initData(SocketIOClient socketIOClient, AckRequest request, Object data) {
        if (Objects.isNull(data)) {
            socketIOClient.sendEvent(typeName, BRiches.get());
        } else {
            JSONObject obj = JSONObject.parseObject(JSON.toJSONString(data));
            int num = obj.getInteger("num");
            int limit = obj.getInteger("limit");
            briches.calculate(num, limit);
        }

    }

    /**
     * connect事件，客户端发起连接时调用
     *
     * @param socketIOClient
     */
    @OnConnect
    public void onConnect(SocketIOClient socketIOClient) {
        if (socketIOClient != null) {
            addOnlineCount();
            socketIOClient.sendEvent(typeName, BRiches.get());
            LOGGER.info("客服端sessionID：{}已经连接, client ip : {} ,online count : {} ", socketIOClient.getSessionId(), socketIOClient.getRemoteAddress(), getOnlineCount());
        }
    }

    /**
     * disconnect事件，客户端断开连接时调用
     *
     * @param socketIOClient
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient socketIOClient) {
        if (socketIOClient != null) {
            subOnlineCount();
            socketIOClient.disconnect();
            LOGGER.info("客服端sessionID：{}已经连接, client ip : {} ,online count : {} : ", socketIOClient.getSessionId(), socketIOClient.getRemoteAddress(), getOnlineCount());
        }
    }

    /**
     * @param type    前台根据类型接收消息，所以接收的消息类型不同，收到的通知就不同 推送的事件类型
     * @param content 推送的内容
     * @Title: pushMsg
     * @Description: 全体消息推送
     */
    public void pushMsg(String type, Object content) {
        // 获取全部客户端
        Collection<SocketIOClient> allClients = socketIOServer.getAllClients();
        for (SocketIOClient socket : allClients) {
            socket.sendEvent(type, content);
        }
    }

    public void pushMsg() {
        // 获取全部客户端
        Collection<SocketIOClient> allClients = socketIOServer.getAllClients();
        for (SocketIOClient socket : allClients) {
            socket.sendEvent(typeName, BRiches.get());
        }
    }

    public static int getOnlineCount() {
        return onlineCount.get();
    }

    public static void addOnlineCount() {
        DataEventHandler.onlineCount.getAndIncrement();
    }

    public static void subOnlineCount() {
        DataEventHandler.onlineCount.getAndDecrement();
    }
}
