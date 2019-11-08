package com.app.crawler.riches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.app.crawler.riches.pojo.ShareInfo;

/** 
 * @Description: TODO
 * @date 2019年11月2日 上午11:11:25 
 * @ClassName: RicheCompute30 
 */
public class RicheComputeAbstract implements RicheCompute{

	protected int num = 30;
	
	private int limit = 7;
	
	
	public int getLimit() {
		return limit;
	}


	public void setLimit(int limit) {
		this.limit = limit;
	}


	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}


	/* (non-Javadoc)
	 * @see com.app.crawler.riches.RicheCompute#compute(com.app.crawler.riches.pojo.RicheMsg)
	 */
	@Override
	public RicheTarget compute(List<ShareInfo> datas,String name) {
		RicheTarget target = new RicheTarget();
		if (datas.size() > num) {
			Collections.sort(datas, new Comparator<ShareInfo>() {
				public int compare(ShareInfo o1, ShareInfo o2) {
					return o1.getDate().compareTo(o2.getDate());
				};
			});
			List<ShareInfo> tmpList = datas.subList(datas.size() - num, datas.size());
			float l = this.getRippleValue(name, tmpList, limit);
			ShareInfo shareInfo = datas.get(datas.size() - 1 );
			target.setL(l);
			target.setHand(shareInfo.getHand());
			target.setStockName(name);
		}
		return target;
	}
	

	/**
	 * 根据 斜率来划分 范围
	 * 
	 * 斜率正值越大 则当日涨幅数越大
	 * 
	 * -0.1*360 - 0.1*360
	 * 
	 * 斜率负值越大则当日跌幅数越大
	 * 
	 * 设置 斜率区间值 且 连续斜率总和大于一定值这 出现峰回路转 之势
	 */

	private void compute(List<ShareInfo> datas) {
		String min = null;
		String max = null;
		String day1 = null;
		String day2 = null;
		
		boolean flag = true;
		for(ShareInfo bean : datas) {
			if (flag) {
				min = bean.getClosePrice();
				max = bean.getClosePrice();
				flag = false;
			}
			if (bean.getClosePrice().compareTo(min) < 0) {
				min = bean.getClosePrice();
				day1 = bean.getDate();
			}
			if (bean.getClosePrice().compareTo(max) > 0) {
				max = bean.getClosePrice();
				day2 = bean.getDate();
			}
		}
		//斜率  计算波动浮动
		float k = (Float.valueOf(max) - Float.valueOf(min)) / Float.valueOf(day2) - Float.valueOf(day1);
		
		if (k > 0) {
			
		}else {
			
		}
	}

	/**
	 * 如果小 给 0等于给 0  大于给1 
	 *  @param datas
	 *  @param n 最近n天是否低于或者有低于之前数据
	 *  @return
	 *  @Description: getRippleValue
	 *  @date 2019年11月2日 下午1:42:51
	 */
	private float getRippleValue(String name, List<ShareInfo> datas,int n) {
		float l = 0f;
		int m = 1;
		for(int k = datas.size() -1; k > 0 ; k --) {
			ShareInfo bean1 = datas.get(k);
			if(m > n) {
				break;
			}
			m ++;
			List<Integer> results = new ArrayList<Integer>(datas.size()- n);
			for(int j = 0; j < datas.size(); j++) {
				if ((datas.size() - j - 1) > n) {
					ShareInfo bean2 = datas.get(j);
					int r = 0;
					if (bean2.getClosePrice().compareTo(bean1.getClosePrice()) > 0) {
						r = 1;
					}else if(bean2.getClosePrice().compareTo(bean1.getClosePrice()) == 0) {
						r = 0;
					}
					results.add(r);
				}
			}
			l += this.getTrendValue(results);
		}

		return l / n;
	}
	/**
	 * 0 - 1 值越大 则持续下跌
	 *  @param results
	 *  @return
	 *  @Description: getTrend
	 *  @date 2019年11月2日 下午5:16:18
	 */
	private float getTrendValue(List<Integer> results) {
		int sum = 0;
		for(Integer v : results) {
			sum += v;
		}
		return (float)sum  /results.size();
	}

}
