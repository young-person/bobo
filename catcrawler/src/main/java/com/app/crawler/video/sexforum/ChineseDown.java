package com.app.crawler.video.sexforum;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.app.crawler.request.RestRequest;

/**
 * @Description: TODO
 * @date 2019年6月30日 下午3:34:05
 * @ClassName: ChineseDown
 */
public class ChineseDown extends ChineseTables {

	private RestRequest request = new RestRequest();

	@Override
	public void doFilter(List<Map<String, String>> datas) {
		for (Map<String, String> map : datas) {
			String url = map.get("url");
			String pageUrl = String.format(MainParse.DOMIAN, trimSplit(url));
			String content = request.doGet(pageUrl);
			if (Objects.isNull(content)) {
				return;
			}
			Document document = Jsoup.parse(content);

			Elements ts = document.select("span[id*=attach_]");

			Elements strongs = document.select("table.t_table strong font");
			Element mvDetail = null;
			for (Element e : strongs) {
				if ("#ffffff".equals(e.attr("color"))) {
					mvDetail = e;
					break;
				}
			}
			String detail = null;
			if (null != mvDetail) {
				detail = mvDetail.text();
				detail = detail.substring(detail.indexOf("【影片大小】："), detail.indexOf("【影片格式】"));
			}
			if (null != ts && ts.size() > 0) {
				saveAndDown(ts, map, detail);
			} else {
				ts = document.select("p.attnm");
				saveAndDown(ts, map, detail);
			}
		}

	}

	@Override
	public String getSql(List<String> keys, List<String> values) {
		return null;
	}

	private void saveAndDown(Elements ts, Map<String, String> map, String detail) {

		if (null != ts && ts.size() > 0) {
			Element element = ts.first();
			Elements torrents = element.select("a");
			if (torrents != null && torrents.size() > 0) {
				Element torrent = torrents.first();
				String href = torrent.attr("href");
				String fileName = torrent.text();
				if (Objects.nonNull(href)) {
					String torrentHref = String.format(MainParse.DOMIAN, trimSplit(href));
					map.put("torrent", torrentHref);
					map.put("fileName", fileName);
					map.put("detail", detail);
					downTorrent(torrentHref, fileName);
				}
			}
		}

	}

	private void downTorrent(String url, final String fileName) {
		FileOutputStream fos = null;
		try {
			byte[] buffer = request.doGetByte(url);
			File file = new File(folder + "torrent" + File.separator + fileName);
			fos = new FileOutputStream(file);
			fos.write(buffer, 0, buffer.length);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
