package com.app.crawler.riches;

import java.io.Serializable;

/** 
 * @Description: TODO
 * @date 2019年11月2日 下午8:00:16 
 * @ClassName: RicheTarget 
 */
public class RicheTarget implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 650682697475506235L;

	private float l;
	
	private String hand;

	public String getHand() {
		return hand;
	}

	public void setHand(String hand) {
		this.hand = hand;
	}

	public float getL() {
		return l;
	}

	public void setL(float l) {
		this.l = l;
	}
	
}
