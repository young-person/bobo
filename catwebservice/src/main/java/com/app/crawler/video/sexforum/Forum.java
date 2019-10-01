package com.app.crawler.video.sexforum;

import com.app.crawler.pojo.CNode;
import com.app.crawler.video.StartDown;
import com.app.utils.HttpUtil;
import com.bobo.base.BaseClass;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/** 
 * @Description: TODO
 * @date 2019年6月29日 下午10:59:24 
 * @ClassName: Forum 
 */
public class Forum extends BaseClass{
	
	public void parseAllTable(String url) {
		
		String content = HttpUtil.doGetRequest(url);
		
		Document document = Jsoup.parse(content);
		
		Elements ts = document.select("table.fl_tb dl");
		List<CNode> list = new ArrayList<>();
		for(Element element:ts) {
			
			Elements as1 = element.select("a");
			
			if (as1!=null && as1.size() > 0) {
				Element a = as1.first();
				String href = a.attr("href");
				String text = a.text();
				CNode node = new CNode();
				node.setUrl(String.format(MainParse.DOMIAN, trimSplit(href)));
				node.setName(text);
				list.add(node);
			}
			
		}
		doGetSimpleUrl(list);
	}
	
	
	private void doGetSimpleUrl(List<CNode> list) {
		for(CNode node:list) {
			if ("网盘下载区 | Disk Download".equals(node.getName())) {
//				ChineseTables hand = new DropBoxDown();
//				tacticsPaserDom(node,hand);
			}
			else if("华人网友自拍区 | Self-Shooting Video".equals(node.getName())) {
				ChineseTables hand = new ChineseOnline();
				tacticsPaserDom(node,hand);
			}
		}
		
	}
	
	private void tacticsPaserDom(CNode node,StartDown hand) {
		String content = HttpUtil.doGetRequest(node.getUrl());
		Document document = Jsoup.parse(content);
		Elements ts = document.select("ul.ttp.bm.cl li");
		
		for(Element element : ts) {
			try {
				String text = element.text();
				Element e = element.select("a").first();
				String url = e.attr("href");
				hand.setUrl(String.format(MainParse.DOMIAN, trimSplit(url)));
				hand.startMode();

			} catch (Exception e) {
				LOGGER.error("不是指定格式解析");
			}
		}
	}
	
}
