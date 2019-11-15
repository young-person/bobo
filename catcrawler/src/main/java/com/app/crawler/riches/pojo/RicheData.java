package com.app.crawler.riches.pojo;

import java.util.List;

/** 
 * @Description: TODO
 * @date 2019年11月3日 下午5:21:20 
 * @ClassName: RicheData 股票数据接口
 */
public interface RicheData {

	List<ShareBean> loadShareBeanDatas(String path);
}
