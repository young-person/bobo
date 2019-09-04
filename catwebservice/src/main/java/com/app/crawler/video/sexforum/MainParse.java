package com.app.crawler.video.sexforum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.app.crawler.CFilter;
import com.app.crawler.pojo.CNode;
import com.app.utils.HttpUtil;
import com.app.utils.HttpUtil.HttpResult;
import com.bobo.base.BaseClass;

/** 
 * @Description: TODO
 * @date 2019年6月29日 下午4:21:15 
 * @ClassName: MainParse 
 */
public class MainParse extends BaseClass{

	public static final String DOMIAN = "https://www.dd18ll.space/%s";
	
	private static final String LOGIN_URL = "https://www.dd18ll.info/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes";
	
	
	public static void main(String[] args) {
		MainParse mainParse = new MainParse();
	
		try {
			mainParse.parseHome();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void parseHome() {
		
		String main_url = "portal.html";
		String content = HttpUtil.doGetRequest(String.format(DOMIAN, main_url));
		
		Document document = Jsoup.parse(content);
		
		Elements ts = document.select("div.site-guide");
		
		if (null!=ts && ts.size() > 0) {
			
			List<CNode> list = new ArrayList<>();
			
			Element element = ts.first();
			Elements hrefs = element.select("a");
			for(Element e: hrefs) {
				String href = e.attr("href");
				if (StringUtils.isNotBlank(href)) {
					String text = e.text();
					CNode node = new CNode();
					node.setUrl(String.format(DOMIAN,trimSplit(href)));
					node.setName(text);
					list.add(node);
				}
			}
			simpleGetForum(list);
		}else {
			LOGGER.error("程序需要进行更新！");
		}
	}
	
	
	private void simpleGetForum(List<CNode> list) {
		startGetUrls(list, new CFilter<CNode>() {
			@Override
			public void excute(CNode e) {
				
				if (e.getUrl().equals(String.format(DOMIAN, "forum.html"))) {
					Forum forum = new Forum();
					forum.parseAllTable(e.getUrl());
				}
				
			}});
	}
	
	private void startGetUrls(List<CNode> list,CFilter<CNode> filter) {
		if (null!=list) {
			
			for(CNode node: list) {
				if (null!=filter) {
					filter.excute(node);
				}
			}
			
		}
	}
	public HttpResult login() {
		Map<String, String> headParamsMap = new HashMap<String, String>();
		Map<String, String> formMap = new HashMap<String, String>();
		
		formMap.put("username", "冯虹爱我");
		formMap.put("password", DigestUtils.md5Hex("czb199345"));
		formMap.put("fingerprint", "3942835952");
		formMap.put("referer", "portal.html");
		formMap.put("quickforward", "yes");
		formMap.put("handlekey", "ls");
		formMap.put("sectouchpoint", "0");
		
		return HttpUtil.doPostGetgetHttpResult(LOGIN_URL, headParamsMap, formMap);
	}
	
}
