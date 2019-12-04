package com.app.ftp.impl;

import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FTPCommandListener implements ProtocolCommandListener {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void protocolCommandSent(ProtocolCommandEvent event) {
		logger.info(" > "+event.getMessage());
	}

	@Override
	public void protocolReplyReceived(ProtocolCommandEvent event) {
		logger.info(" < "+event.getMessage());
	}

}
