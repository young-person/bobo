package com.app.crawler.loader;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.app.crawler.loader.TextToDb.InDbFilter;
import com.app.crawler.loader.TextToDb.TextFilter;
import com.mysql.cj.jdbc.Driver;

/** 
 * @Description: TODO
 * @date 2019年7月9日 下午12:25:22 
 * @ClassName: ChinaText 
 */
public class ChinaText {

	
	public static void main(String[] args) {
		try {
			Driver driver = new Driver();
			DataSource dataSource = new SimpleDriverDataSource(driver,"jdbc:mysql://localhost:3307/datas?useSSL=false&serverTimezone=GMT%2B8","root","199345");
			TextToDb toDb = new TextToDb(dataSource);
			List<Map<String, Object>> datas = new ArrayList<>();
			toDb.readTextLine("C:\\china.txt", new TextFilter() {
				@Override
				public void parseLineText(String text) {
					String[] s = text.split("	");
					Map<String, Object> map = new HashMap<>();
					map.put("name", s[1]);
					map.put("code", s[0]);
					map.put("parentcode", s[2]);
					datas.add(map);
				}
			});
			String sql = "insert into china(name,code,parentcode) values (?,?,?);";
			
			for(Map<String, Object> map:datas) {
				toDb.writeToTxt((String)map.get("name")+","+(String)map.get("code")+","+(String)map.get("parentcode"), "C:\\china1.txt");
			}
//			toDb.datasToDb(datas, sql, new InDbFilter() {
//				@Override
//				public void parseTextToDB(PreparedStatement ps, int i, List<Map<String, Object>> datas) {
//					try {
//						ps.setString(1, (String)datas.get(i).get("name"));
//						ps.setString(2, (String)datas.get(i).get("code"));
//						ps.setString(3, (String)datas.get(i).get("parentcode"));
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						System.err.println(datas.get(i).toString());
//						e.printStackTrace();
//					}
//				}
//			});		

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
