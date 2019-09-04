package com.app.crawler.video.sexforum;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.app.crawler.video.StartDown;
import com.app.utils.HttpUtil;
import com.bobo.base.BaseClass;
import com.mysql.cj.jdbc.Driver;

/** 
 * @Description: TODO
 * @date 2019年6月30日 下午3:26:04 
 * @ClassName: ChineseTables 
 */
public abstract class ChineseTables extends BaseClass implements StartDown{

	/* (non-Javadoc)
	 * @see com.app.crawler.video.StartDown#startMode()
	 */
	
	private String url = null;
	
	@Override
	public void startMode() {
		String pageUrl = this.url;
		String content = HttpUtil.doGetRequest(pageUrl);
		
		Document document = Jsoup.parse(content);
		this.appendContentByDocument(document);
		
		while (true) {
			Elements pages = document.select("div .bm.bw0.pgs.cl");
			if (null != pages) {
				Element element = pages.first();
				Elements next = element.select("a.nxt");
				if (null != next) {
					Element nxtElement = next.first();
					String url = nxtElement.attr("href");
					if (StringUtils.isNoneBlank(url)) {
						String nextUrl = String.format(MainParse.DOMIAN,  trimSplit(url));
						content = HttpUtil.doGetRequest(nextUrl);
						document = Jsoup.parse(content);
						appendContentByDocument(document);
					}else {
						break;
					}
				}
			}
		}
		
	}

	private void appendContentByDocument(Document document) {
		Elements ts = document.select("tbody[id*=normalthread_]");
		List<Map<String, String>> datas =  new ArrayList<>();
		
		if (null != ts) {
			for(Element cxt: ts) {
				Elements tdNew = cxt.select("th");
				if (null != tdNew) {
					Element one = tdNew.first();
					Elements types = one.select("em");
					Elements real = one.select("a.s.xst");
					if (types != null && null!=real) {
						Element type = types.first();
						String typeName ="",href = "",title = "";
						if (null!=type) {
							typeName = type.text();
						}
						Element content = real.first();
						if (null !=content) {
							href = content.attr("href");
							title = content.text();
						}

						Map<String, String> map = new HashMap<>();
						map.put("url", href);
						map.put("typeName", typeName);
						map.put("content", title);
						datas.add(map);
					}
				}
			}
			doFilter(datas);
			insertToDb(datas);
		}
	}
	
	private void insertToDb(List<Map<String, String>> datas) {
		if (CollectionUtils.isNotEmpty(datas)) {
			try {
				Driver driver = new Driver();
				DataSource dataSource = new SimpleDriverDataSource(driver,"jdbc:mysql://localhost:3307/datas?useSSL=false&serverTimezone=GMT%2B8","root","199345");
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
				
				String sql = "insert into crawlerurl(url,typeName,content,content1,content2,content3,content4,soltcontent,content5) values (?,?,?,?,?,?,?,?,?)";
				
				jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, datas.get(i).get("url"));
						ps.setString(2, datas.get(i).get("typeName"));
						ps.setString(3, datas.get(i).get("content"));
						ps.setString(4, datas.get(i).get("torrent"));
						ps.setString(5, datas.get(i).get("fileName"));
						ps.setString(6, datas.get(i).get("detail"));
						ps.setString(7, datas.get(i).get("content4"));
						ps.setString(8, datas.get(i).get("soltcontent"));
						ps.setString(9, datas.get(i).get("content5"));
					}

					@Override
					public int getBatchSize() {
						return datas.size();
					}
				});
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public abstract void doFilter(List<Map<String, String>> datas);
}
