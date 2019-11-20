package com.app.crawler.statistics;

import com.app.crawler.base.CrawlerDown;
import com.app.crawler.request.RestRequest;
import com.app.crawler.url.Linking;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 天气调度任务
 *
 * @author bobo
 */
public class WeatherRecordTask implements CrawlerDown {

    RestRequest restRequest = new RestRequest();

    public void initCityUrls() {
        String content = restRequest.doGet(Linking.WEATHERURL.getUrl());
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            content = new String(content.getBytes("UTF-8"), "UTF-8");

            Document document = Jsoup.parse(content);
            Elements links = document.select("div.lqcontentBoxheader a[href]");

            for (Element e : links) {
                String text = e.ownText();
                String href = e.attr("href");
                Map<String, String> m = new HashMap<String, String>();
                m.put("text", text);
                m.put("href", "http://www.weather.com.cn" + href);
                list.add(m);
            }
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    final int index = i;
                    final Map<String, String> uMap = list.get(index);
                    String url = uMap.get("href");
                    String cxt = restRequest.doGet(url);
                    paramContent(cxt, url);
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void paramContent(String content, String url) {
        if (null != content) {
            try {
                content = new String(content.getBytes("UTF-8"), "UTF-8");
                if (null != content) {
                    Document document = Jsoup.parse(content);
                    Element conMidtab = document.select("div.hanml div.conMidtab").get(0);

                    Elements conMidtab3 = conMidtab.select("div.conMidtab3 table");
                    for (int k = 0; k < conMidtab3.size(); k++) {
                        Element table = conMidtab3.get(k);
                        Elements tds = table.select("td.last");
                        Elements td_0 = table.select("tr td:eq(0)");
                        String texts = td_0.text();
                        String[] text = texts.split(" ");
                        if (text.length != tds.size()) {
                            LOGGER.error("数据项不匹配:{}", url);
                        } else {
                            for (int j = 0; j < tds.size(); j++) {
                                String mc = text[j];
                                Element td = tds.get(j);
                                Elements as = td.select("a");
                                if (null != as && as.size() == 1) {
                                    Element a = as.get(0);
                                    String href = a.attr("href");
                                    this.paramHtml(mc, href);
                                }
                            }
                        }

                    }
                }

            } catch (UnsupportedEncodingException e) {
                LOGGER.error("页面编码转换失败", e);
            }
        }
    }

    public void paramHtml(String mc, String href) throws UnsupportedEncodingException {
        String html = restRequest.doGet(href);//访问详情页面
        html = new String(html.getBytes("UTF-8"), "UTF-8");
        Document doc = Jsoup.parse(html);
        Elements uls = doc.select("ul.t.clearfix li");
        if (null != uls && uls.size() > 0) {
            for (Element u : uls) {
                try {
                    Element hs = u.select("h1").get(0);
                    String rq = hs.ownText();
                    Element weas = u.select("p.wea").get(0);
                    String status = weas.ownText();
                    String hightemperature = "";
                    if (null != u.select("p.tem span") && u.select("p.tem span").size() == 1) {
                        Element tems = u.select("p.tem span").get(0);
                        hightemperature = tems.ownText();
                    }

                    Element i = u.select("p.tem i").get(0);
                    String lowtemperature = i.ownText();
                    Element w = u.select("p.win i").get(0);
                    String wind = w.ownText();
                    String direction1 = "", direction2 = "";
                    if (null != u.select("p.win em span")) {
                        if (u.select("p.win em span").size() == 1) {
                            Element em1 = u.select("p.win em span").get(0);
                            direction1 = em1.attr("title");
                        } else if (u.select("p.win em span").size() == 2) {
                            Element em2 = u.select("p.win em span").get(1);
                            direction2 = em2.attr("title");
                        }
                    }
                    List<String> values = new ArrayList<>();
                    values.add(mc);
                    values.add(rq);
                    values.add(hightemperature);
                    values.add(lowtemperature);
                    values.add(status);
                    values.add(wind);
                    values.add(direction1);
                    values.add(direction2);
                    CrawlerDown.writeToTxt(String.join(SPACE, values), "/app/datas/weather.txt");
                    String sql = "insert into weather(cityname,date,high,low,status,wind,direction1,direction2) values('" + mc + "','" + rq + "','" + hightemperature + "','" + lowtemperature + "','" + status + "','" + wind + "','" + direction1 + "','" + direction2 + "')";
                    CrawlerDown.writeToTxt(sql, "/app/datas/weather.sql");
                } catch (Exception e) {
                    LOGGER.error("解析天气详情页获取数据失败页面地址:[{}]", href, e);
                }

            }
        }
    }

    @Override
    public void start() {
        if (!isRuning()) {
            this.initCityUrls();
        }
    }

    @Override
    public boolean stop() {
        return false;
    }

    @Override
    public boolean suspend() {
        return false;
    }

    @Override
    public boolean isRuning() {
        return false;
    }

    @Override
    public String executeTimeFormat() {
        return null;
    }
}
