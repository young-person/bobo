package com.app.crawler.video.sexforum;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.app.utils.HttpUtil;
import com.app.utils.HttpUtil.HttpResult;

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
			String content = HttpUtil.doGetRequest(pageUrl);
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

	public static HttpResult doGetRequest(String url, CookieStore cookieStore) {
		LOGGER.info("请求地址:【{}】", url);
		HttpResult result = new HttpResult();
		String charset = "UTF-8";
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		HttpGet get = new HttpGet(url);
		get.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		get.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
		get.addHeader("Accept-Encoding", "gzip, deflate");
		get.addHeader("Connection", "keep-alive");
		get.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");

		HttpResponse response;
		try {
			response = httpClient.execute(get);
			int statusCode = response.getStatusLine().getStatusCode();
			Header[] headers = response.getAllHeaders();
			if (statusCode == HttpStatus.SC_OK) {
				String content = EntityUtils.toString(response.getEntity(), charset);
				result.setCookies(cookieStore.getCookies());
				result.setDocument(Jsoup.parse(content));
				result.setCookieStore(cookieStore);
			}
		} catch (IOException e) {
			LOGGER.error("【{}】下载失败,错误信息:【{}】", url, e);
		}
		return result;
	}
}
