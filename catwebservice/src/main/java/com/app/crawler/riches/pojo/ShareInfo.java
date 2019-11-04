package com.app.crawler.riches.pojo;

/** 
 * @Description: TODO
 * @date 2019年11月3日 下午5:05:01 
 * @ClassName: ShareInfo 股票每天信息 写入excel数据表
 */
public class ShareInfo {
	
	/**
	 * 日期
	 */
    private String date;
    
	/**
	 * 换手率
	 */
	private String hand;
	
	/**
	 * 涨跌幅度比例
	 */
	private String risePrice;

	
	/**
	 * 开盘价
	 */
	private String openPrice;
	/**
	 * 收盘价
	 */
    private String closePrice;
    
    /**
          * 昨日收盘价
     */
    
    private String prevClose;
    
	/**
	 * 今日最高价
	 */
    private String maxPrice;
	/**
	 * 今日最低价
	 */
    private String minPrice;
	/**
	 * 成交总量
	 */
    private String total;
	/**
	 * 总金额
	 */
    private String money;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHand() {
		return hand;
	}
	public void setHand(String hand) {
		this.hand = hand;
	}
	public String getRisePrice() {
		return risePrice;
	}
	public void setRisePrice(String risePrice) {
		this.risePrice = risePrice;
	}
	public String getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(String openPrice) {
		this.openPrice = openPrice;
	}
	public String getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(String closePrice) {
		this.closePrice = closePrice;
	}
	public String getPrevClose() {
		return prevClose;
	}
	public void setPrevClose(String prevClose) {
		this.prevClose = prevClose;
	}
	public String getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((closePrice == null) ? 0 : closePrice.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((hand == null) ? 0 : hand.hashCode());
		result = prime * result + ((maxPrice == null) ? 0 : maxPrice.hashCode());
		result = prime * result + ((minPrice == null) ? 0 : minPrice.hashCode());
		result = prime * result + ((money == null) ? 0 : money.hashCode());
		result = prime * result + ((openPrice == null) ? 0 : openPrice.hashCode());
		result = prime * result + ((prevClose == null) ? 0 : prevClose.hashCode());
		result = prime * result + ((risePrice == null) ? 0 : risePrice.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShareInfo other = (ShareInfo) obj;
		if (closePrice == null) {
			if (other.closePrice != null)
				return false;
		} else if (!closePrice.equals(other.closePrice))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (hand == null) {
			if (other.hand != null)
				return false;
		} else if (!hand.equals(other.hand))
			return false;
		if (maxPrice == null) {
			if (other.maxPrice != null)
				return false;
		} else if (!maxPrice.equals(other.maxPrice))
			return false;
		if (minPrice == null) {
			if (other.minPrice != null)
				return false;
		} else if (!minPrice.equals(other.minPrice))
			return false;
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
			return false;
		if (openPrice == null) {
			if (other.openPrice != null)
				return false;
		} else if (!openPrice.equals(other.openPrice))
			return false;
		if (prevClose == null) {
			if (other.prevClose != null)
				return false;
		} else if (!prevClose.equals(other.prevClose))
			return false;
		if (risePrice == null) {
			if (other.risePrice != null)
				return false;
		} else if (!risePrice.equals(other.risePrice))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ShareInfo [date=" + date + ", hand=" + hand + ", risePrice=" + risePrice + ", openPrice=" + openPrice
				+ ", closePrice=" + closePrice + ", prevClose=" + prevClose + ", maxPrice=" + maxPrice + ", minPrice="
				+ minPrice + ", total=" + total + ", money=" + money + "]";
	}
    

}
