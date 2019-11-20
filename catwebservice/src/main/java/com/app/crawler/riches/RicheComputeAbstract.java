package com.app.crawler.riches;

import com.app.crawler.riches.pojo.RShareInfo;
import com.app.crawler.riches.pojo.ShareInfo;

import java.util.*;

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
			if (limit <= num){
				limit = 7;
				num = 30;
			}
			Collections.sort(datas, new Comparator<ShareInfo>() {
				public int compare(ShareInfo o1, ShareInfo o2) {
					return o1.getDate().compareTo(o2.getDate());
				};
			});

			List<ShareInfo> tmpList = datas.subList(datas.size() - num, datas.size());
			float l = this.getRippleValue(tmpList, limit);
			target.setL(l);
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
		float min = 0f;
		float max = 0f;
		String day1 = null;
		String day2 = null;

		boolean flag = true;
		for(ShareInfo bean : datas) {
			if (flag) {
				min = Float.valueOf(bean.getClosePrice());
				max = Float.valueOf(bean.getClosePrice());
				flag = false;
			}
			if (Float.valueOf(bean.getClosePrice()) < min) {
				min = Float.valueOf(bean.getClosePrice());
				day1 = bean.getDate();
			}
			if (Float.valueOf(bean.getClosePrice()) > max) {
				max = Float.valueOf(bean.getClosePrice());
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
	private float getRippleValue(List<ShareInfo> datas,int n) {
		float l = 0f;
		int m = 1;
		int rn = 0;
		boolean[] b1 = new boolean[datas.size()];
		boolean[] b2 = new boolean[datas.size()];
		boolean[] b3 = new boolean[datas.size()];
		boolean[] b4 = new boolean[datas.size()];
		boolean[] b5 = new boolean[datas.size()];

		for(int k = datas.size() -1; k > 0 ; k --) {
			RShareInfo bean1 = this.conveRShareInfo(datas.get(k));
			if(m > n) {
				break;
			}
			m ++;

			if (bean1.getRisePrice() >= 0) {
				rn += 1;
			}else {
				rn -= 1;
			}
			b1[k] = bean1.getRisePrice() <= 5f;
			b2[k] = bean1.getRisePrice() >= 5f && bean1.getRisePrice() <= 7f;
			b3[k] = bean1.getRisePrice() >= 7f;

			List<Integer> results = new ArrayList<Integer>();
			for(int j = 0; j < datas.size(); j++) {
				if ((datas.size() - j - 1) > n) {
					RShareInfo bean2 = this.conveRShareInfo(datas.get(j));
					int r = 0;
					if (bean2.getClosePrice() > bean1.getClosePrice()) {
						r = 1;
					}
					results.add(r);
				}
			}
			l += this.getTrendValue(results);//如果不等于1就说明有有大于之前的数据
		}


		return l / n;
	}

	private RShareInfo conveRShareInfo(ShareInfo shareInfo) {

		RShareInfo r = new RShareInfo(shareInfo.getDate(),
				Float.valueOf(shareInfo.getHand()).floatValue(),
				Float.valueOf(shareInfo.getRisePrice()).floatValue(),
				Float.valueOf(shareInfo.getOpenPrice()).floatValue(),
				Float.valueOf(shareInfo.getClosePrice()).floatValue(),
				Float.valueOf(shareInfo.getPrevClose()).floatValue(),
				Float.valueOf(shareInfo.getMaxPrice()).floatValue(),
				Float.valueOf(shareInfo.getMinPrice()).floatValue(),
				Integer.valueOf(shareInfo.getTotal()).intValue(),
				Double.valueOf(shareInfo.getMoney()).intValue());

		return r;
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
		if (Objects.isNull(results))
			return 0f;
		for(Integer v : results) {
			sum += v;
		}
		return (float)sum/results.size();
	}

}
