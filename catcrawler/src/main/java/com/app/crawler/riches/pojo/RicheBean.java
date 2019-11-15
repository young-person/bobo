package com.app.crawler.riches.pojo;

import java.io.Serializable;

/** 
 * @Description: TODO 每一股的当前详情情况
 * @date 2019年11月1日 下午10:29:46 
 * @ClassName: RicheBean 
 */
public class RicheBean implements Serializable{

	private static final long serialVersionUID = -2591538990263384052L;
	private String code;
	/**
	 * 股票类型
	 */
	private String codeType;
	private String stockName;
	private String newPrice;
	/**
	 * 涨跌幅度比例
	 */
	private String risePrice;
	/**
	 * 换手率
	 */
	private String hand;
	private String volumeRatio;
	private String totalAmount;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(String newPrice) {
		this.newPrice = newPrice;
	}
	public String getRisePrice() {
		return risePrice;
	}
	public void setRisePrice(String risePrice) {
		this.risePrice = risePrice;
	}
	public String getHand() {
		return hand;
	}
	public void setHand(String hand) {
		this.hand = hand;
	}
	public String getVolumeRatio() {
		return volumeRatio;
	}
	public void setVolumeRatio(String volumeRatio) {
		this.volumeRatio = volumeRatio;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	@Override
	public String toString() {
		return "RicheBean [code=" + code + ", codeType=" + codeType + ", stockName=" + stockName + ", newPrice="
				+ newPrice + ", risePrice=" + risePrice + ", hand=" + hand + ", volumeRatio=" + volumeRatio
				+ ", totalAmount=" + totalAmount + "]";
	}
	
}
