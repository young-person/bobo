package com.app.crawler.video.sexforum;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Description: TODO
 * @date 2019年6月30日 下午11:12:37
 * @ClassName: DropBoxDown
 */
public class DropBoxDown extends ChineseTables {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.app.crawler.video.sexforum.ChineseTables#doFilter(java.util.List)
	 */
	@Override
	public void doFilter(List<Map<String, String>> datas) {
		for (Map<String, String> map : datas) {
			String url = map.get("url");
			String pageUrl = String.format(MainParse.DOMIAN, trimSplit(url));
			String content = restRequest.doGet(pageUrl);
			Document document = Jsoup.parse(content);

			Elements ts = document.select("div.t_fsz tr a");
			if (ts != null && ts.size() > 0) {
				String cxt = ts.text();
				int cindex = cxt.indexOf("【解压密码】");
				String password = null;
				if (cindex > -1) {
					password = cxt.substring(cindex, cindex + 6);
					map.put("content4", password);
				}

				for (int index = ts.size() - 1; index >= 0; index--) {
					Element e = ts.get(index);
					String name = e.text();
					String href = e.attr("href");
					String title = e.attr("title");
					if (isURL(name) && StringUtils.isBlank(title)) {
						map.put("content5", href);//第三方链接
						downMoreForum(href, map);
						
					}
				}

			}
		}

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

	private static final String regex = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
	private static final Pattern pattern = Pattern.compile(regex);

	private boolean isURL(String str) {
		return pattern.matcher(str).matches();
	}

	private void downMoreForum(String url, Map<String, String> map) {
		if (url.indexOf("xibupan") > -1) {// 可以直接下载

		} else if (url.indexOf("getlle") > -1) {
			String script = getSoltScript(url);
			map.put("soltcontent", script);
		}

	}

	private String getSoltScript(String url) {
		LOGGER.info("请求地址:【{}】", url);
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(new BasicCookieStore()).build();
		HttpGet get = new HttpGet(url);
		get.addHeader("Accept", "*/*");
		get.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		get.addHeader("Connection", "keep-alive");
		get.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");

		HttpResponse response;
		String html = null;
		try {
			response = httpClient.execute(get);
			String cx = EntityUtils.toString(response.getEntity());
			html = cx.trim().replace("<script>", "").replace("</script>", "").replace(
					"eval(y.replace(/\\b\\w+\\b/g, function(y){return x[f(y,z)-1]||(\"_\"+y)}));",
					"dropBoxDownScript = y.replace(/\\b\\w+\\b/g, function(y){return x[f(y,z)-1]||(\"_\"+y)});");

		} catch (IOException e) {
			LOGGER.error("【{}】下载失败,错误信息:【{}】", url, e);
		}
		return html;
	}

}
