package com.app.crawler.riches.producer;

import java.util.concurrent.CopyOnWriteArraySet;

import com.alibaba.fastjson.JSONObject;
import com.app.crawler.riches.BRiches;
import com.app.crawler.riches.RicheComputeAbstract;
import com.app.crawler.riches.RicheTarget;
import com.bobo.base.BaseClass;

public class Producer extends BaseClass{
	
	private final CopyOnWriteArraySet<RicheTarget> RICHETARGET = new CopyOnWriteArraySet<RicheTarget>();

	/**
	 * 均衡值
	 */
	private final String hand = "0.8";
	
	public static interface CallBack<T>{
		T add(T t);
	}
	
	public void repeatCalculate(Integer limit,Integer num) {
		BRiches briches = new BRiches();
		RicheComputeAbstract richeCompute = new RicheComputeAbstract();
		richeCompute.setLimit(limit);
		richeCompute.setNum(num);
		briches.setRicheCompute(richeCompute);
		briches.calculate(new CallBack<RicheTarget>() {

			@Override
			public RicheTarget add(RicheTarget t) {
				if (hand.compareTo(t.getHand()) > 0 && !t.getStockName().contains("ST")) {
					RICHETARGET.add(t);
				}else if(hand.compareTo(t.getHand()) > 0 && t.getStockName().contains("ST")) {
					LOGGER.trace("ST系列：----->{}",JSONObject.toJSONString(t));
				}
				return t;
			}
		});
	}
	
	public CopyOnWriteArraySet<RicheTarget> get() {
		return RICHETARGET;
	}
}
