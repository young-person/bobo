package com.app.crawler.video.sexforum;

import com.app.crawler.CrawlerDown;
import com.app.crawler.video.StartDown;
import com.app.utils.HttpUtil;
import com.bobo.base.BaseClass;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.app.crawler.CrawlerDown.SPACE;

/** 
 * @Description: TODO
 * @date 2019年6月30日 下午3:26:04 
 * @ClassName: ChineseTables 
 */
public abstract class ChineseTables extends BaseClass implements StartDown{

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

			for(Map<String,String> map : datas){
				List<String> keys = new ArrayList<>(map.size());

				for(String s : map.keySet()){
					keys.add(s);
				}
				keys.sort(String.CASE_INSENSITIVE_ORDER);

				List<String> values = new ArrayList<>(keys.size());
				for(String s: keys){
					String value = map.get(s);
					values.add(value);
				}
				String str = String.join(SPACE,values);
				CrawlerDown.writeToTxt(str, "/app/datas/videos.txt");
				StringBuilder builder = new StringBuilder("insert into crawlerurl (");
				builder.append(String.join(",",keys));
				builder.append(") values (");
				for(int index = 0; index < values.size(); index ++){
					values.set(index,"'"+values.get(index)+"'");
				}
				builder.append(String.join(",",values));
				builder.append(")");
				CrawlerDown.writeToTxt(builder.toString(), "/app/datas/videos.sql");
			}
		
		}
		
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public abstract void doFilter(List<Map<String, String>> datas);
}
