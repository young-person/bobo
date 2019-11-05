package com.app.crawler.riches.producer;

import java.util.concurrent.CopyOnWriteArraySet;

import com.app.crawler.riches.BRiches;
import com.app.crawler.riches.RicheComputeAbstract;
import com.app.crawler.riches.RicheTarget;

public class Producer {
	
	private final CopyOnWriteArraySet<RicheTarget> RICHETARGET = new CopyOnWriteArraySet<RicheTarget>();

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
				RICHETARGET.add(t);
				return t;
			}
		});
	}
	
	public CopyOnWriteArraySet<RicheTarget> get() {
		return RICHETARGET;
	}
}
