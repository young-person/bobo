package com.app.crawler.riches.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;

@Component
public class DataEventHandler extends AbstractEventHandler {
    @Autowired
    private MessageEventHandler messageEventHandler;
	@Override
	protected Object reloadKeyData(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MessageEventHandler getMessageEventHandler() {
		return messageEventHandler;
	}
    @OnEvent(value = "refreshData")
    public void initData(SocketIOClient socketIOClient, Object data) {
        socketIOClient.sendEvent("", "");
    }
}
