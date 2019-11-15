package com.app.crawler.video.sexforum;

import java.util.List;
import java.util.Map;

/** 
 * @Description: TODO
 * @date 2019年6月30日 下午3:40:18 
 * @ClassName: ChineseOnline 
 */
public class ChineseOnline extends ChineseTables{


	/* (non-Javadoc)
	 * @see com.app.crawler.video.sexforum.ChineseTables#doFilter(java.util.List)
	 */
	@Override
	public void doFilter(List<Map<String, String>> datas) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getSql(List<String> keys, List<String> values) {
		StringBuilder builder = new StringBuilder("insert into crawlerurl (");
		builder.append(String.join(",",keys));
		builder.append(") values (");
		for(int index = 0; index < values.size(); index ++){
			values.set(index,"'"+values.get(index)+"'");
		}
		builder.append(String.join(",",values));
		builder.append(")");
		return builder.toString();
	}


}
