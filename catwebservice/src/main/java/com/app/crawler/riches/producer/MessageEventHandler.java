package com.app.crawler.riches.producer;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bobo.base.BaseClass;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;

@Component
public class MessageEventHandler extends BaseClass{

	    @Autowired
	    private SocketIOServer socketIOServer;

	    //统计在线客户端数量
	    private static AtomicInteger onlineCount = new AtomicInteger(0);

	    /**
	     * connect事件，客户端发起连接时调用
	     * @param socketIOClient
	     */
	    @OnConnect
	    public void onConnect(SocketIOClient socketIOClient) {
	        if (socketIOClient != null) {
	            addOnlineCount();
	            LOGGER.info("netty-socketio connect..., client ip : {} ,online count : {} ", socketIOClient.getRemoteAddress(), getOnlineCount());
	        } else {
	        	LOGGER.error("nettty-socketio client is null...");
	        }
	    }

	    /**
	     * disconnect事件，客户端断开连接时调用
	     * @param socketIOClient
	     */
	    @OnDisconnect
	    public void onDisconnect(SocketIOClient socketIOClient) {
	        if (socketIOClient != null) {
	            subOnlineCount();
	            socketIOClient.disconnect();
	            LOGGER.info("netty-socketio disconnect..., client ip : {} ,online count : {} : ", socketIOClient.getRemoteAddress(), getOnlineCount());
	        } else {
	        	LOGGER.error("nettty-socketio client is null...");
	        }
	    }

	    /** @Title: pushMsg
	     * @Description: 全体消息推送
	     * @param type
	     *            前台根据类型接收消息，所以接收的消息类型不同，收到的通知就不同 推送的事件类型
	     * @param content
	     *            推送的内容
	     */
	    public void pushMsg( String type, Object content ) {
	        // 获取全部客户端
	        Collection<SocketIOClient> allClients = socketIOServer.getAllClients();
	        for(SocketIOClient socket : allClients) {
	            socket.sendEvent(type, content);
	        }
	    }


	    public static int getOnlineCount() {
	        return onlineCount.get();
	    }

	    public static void addOnlineCount() {
	        MessageEventHandler.onlineCount.getAndIncrement();
	    }

	    public static void subOnlineCount() {
	        MessageEventHandler.onlineCount.getAndDecrement();
	    }

}
