package com.app.crawler.riches.producer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.crawler.base.BaseClass;
import com.app.crawler.riches.BRiches;
import com.app.crawler.riches.RicheComputeAbstract;
import com.app.crawler.riches.RicheTarget;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

public class Producer extends BaseClass{

	private final CopyOnWriteArraySet<RicheTarget> RICHETARGET = new CopyOnWriteArraySet<RicheTarget>();

	/**
	 * 均衡值
	 */
	private final float avg = 0.5f;

	private final float hand = 0.5f;

	public Producer(){
	}

	public static interface CallBack<T>{
		T add(T t);
	}
	public void repeatCalculate(Integer limit,Integer num) {
		if (Objects.isNull(limit) || limit == 0)
			limit= 30;
		if (Objects.isNull(num) || num == 0)
			num = 7;

		BRiches briches = new BRiches();
		if (briches.isRuning()){
			LOGGER.info("正在运行直接返回当前数据");
			return;
		}
		RicheComputeAbstract richeCompute = new RicheComputeAbstract();
		richeCompute.setLimit(limit);
		richeCompute.setNum(num);
		briches.setRicheCompute(richeCompute);

		try {
			briches.calculate(t -> {
				try {
					if (Float.valueOf(t.getHand()) >= hand && !t.getStockName().contains("ST") && t.getL() > avg) {
						RICHETARGET.add(t);
					}else if(Float.valueOf(t.getHand()) >= hand && t.getStockName().contains("ST")) {
						LOGGER.trace("ST系列：----->{}",JSONObject.toJSONString(t));
					}
				}catch (Exception e){
					LOGGER.error("错误原因:",JSON.toJSONString(t),e);
				}
				return t;
			});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public CopyOnWriteArraySet<RicheTarget> get() {
		return RICHETARGET;
	}
}
