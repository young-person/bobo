package com.app.crawler.video.sexforum;

import com.app.crawler.CFilter;
import com.app.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.List;
import java.util.Map;

/** 
 * @Description: TODO
 * @date 2019年6月30日 下午3:34:05 
 * @ClassName: ChineseDown 
 */
public class ChineseDown extends ChineseTables{


	@Override
	public void doFilter(List<Map<String, String>> datas) {
		for(Map<String, String> map: datas) {
			String url = map.get("url");
			String pageUrl = String.format(MainParse.DOMIAN,trimSplit(url));
			String content = HttpUtil.doGetRequest(pageUrl);
			Document document = Jsoup.parse(content);
			
			Elements ts = document.select("span[id*=attach_]");
			
			Elements strongs =  document.select("table.t_table strong font");
			Element mvDetail = null;
			for(Element e : strongs) {
				if ("#ffffff".equals(e.attr("color"))) {
					mvDetail = e;
					break;
				}
			}
			String detail = null;
			if (null!=mvDetail) {
				detail = mvDetail.text();
				detail = detail.substring(detail.indexOf("【影片大小】："), detail.indexOf("【影片格式】"));
			}
			if (null!=ts&& ts.size()>0) {
				saveAndDown( ts,map, detail);
			}else {
				ts = document.select("p.attnm");
				saveAndDown( ts,map,detail);
			}
		}
		
	}

	@Override
	public String getSql(List<String> keys, List<String> values) {
		return null;
	}

	private void saveAndDown(Elements ts,Map<String, String> map,String detail) {
		
		if (null!=ts&& ts.size()>0) {
			Element element = ts.first();
			Elements torrents = element.select("a");
			if (torrents!=null&&torrents.size()>0) {
				Element torrent = torrents.first();
				String href = torrent.attr("href");
				String fileName = torrent.text();
				if (StringUtils.isNotBlank(href)) {
					String torrentHref = String.format(MainParse.DOMIAN, trimSplit(href));
					map.put("torrent", torrentHref);
					map.put("fileName", fileName);
					map.put("detail", detail);
					downTorrent(torrentHref,fileName);
				}
			}
		}

	}
	
	private void downTorrent(String url,final String fileName) {
		HttpUtil.doGetByUrlSsl(url, new CFilter<InputStream>() {
			
			@Override
			public void excute(InputStream is) {
				try {
					File file = new File(folder+"torrent"+File.separator+fileName);
					FileOutputStream fos = new FileOutputStream(file);
					byte[] buffer = new byte[4096];
					int len = -1;
					while ((len = is.read(buffer)) != -1) {
						fos.write(buffer, 0, len);
					}
					fos.close();
					is.close();
				} catch (FileNotFoundException e) {
					LOGGER.error("文件：【{}】创建异常：【{}】",fileName,e);
				} catch (IOException e) {
					LOGGER.error("请求地址：【{}】异常：【{}】",url,e);
				}

			}
		});
	}
}
