package com.app.crawler.riches;

import java.util.List;

import com.app.crawler.riches.pojo.HistoryBean;
import com.app.crawler.riches.pojo.RicheMsg;

/** 
 * @Description: TODO
 * @date 2019年11月2日 上午10:51:01 
 * @ClassName: RicheCompute 
 */
public interface RicheCompute {
	/**
	 * 数据计算策略
	 *  @param datas
	 *  @Description: compute
	 *  @date 2019年11月2日 上午10:51:55
	 */
	RicheTarget compute(RicheMsg<List<HistoryBean>> datas,String name);
}
