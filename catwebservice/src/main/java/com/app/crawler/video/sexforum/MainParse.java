package com.app.crawler.video.sexforum;

import com.app.crawler.base.CFilter;
import com.app.crawler.base.CrawlerDown;
import com.app.crawler.pojo.CNode;
import com.app.crawler.request.RestRequest;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @date 2019年6月29日 下午4:21:15
 * @ClassName: MainParse
 */
public class MainParse implements CrawlerDown {

    public static final String DOMIAN = "https://www.dd18ll.space/%s";

    private static final String LOGIN_URL = "https://www.dd18ll.info/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes";

    RestRequest restRequest = new RestRequest();
    private void parseHome() {
//        this.login();

        String content = restRequest.doGet(String.format(DOMIAN, "portal.html"));

        Document document = Jsoup.parse(content);

        Elements ts = document.select("div.site-guide");

        if (null != ts && ts.size() > 0) {

            List<CNode> list = new ArrayList<>();

            Element element = ts.first();
            Elements hrefs = element.select("a");
            for (Element e : hrefs) {
                String href = e.attr("href");
                if (StringUtils.isNotBlank(href)) {
                    String text = e.text();
                    CNode node = new CNode();
                    node.setUrl(String.format(DOMIAN, trimSplit(href)));
                    node.setName(text);
                    list.add(node);
                }
            }
            simpleGetForum(list);
        } else {
            LOGGER.error("程序需要进行更新！");
        }
    }


    private void simpleGetForum(List<CNode> list) {
        startGetUrls(list, e -> {
            if (e.getUrl().equals(String.format(DOMIAN, "forum.html"))) {
                Forum forum = new Forum();
                forum.parseAllTable(e.getUrl());
            }

        });
    }

    private void startGetUrls(List<CNode> list, CFilter<CNode> filter) {
        if (null != list) {
            for (CNode node : list) {
                if (null != filter) {
                    filter.excute(node);
                }
            }
        }
    }

//    private HttpResult login() {
//        Map<String, String> headParamsMap = new HashMap<String, String>();
//        Map<String, String> formMap = new HashMap<String, String>();
//
//        formMap.put("username", "fhaiwo");
//        formMap.put("password", DigestUtils.md5Hex("czb199345"));
//        formMap.put("fingerprint", "3942835952");
//        formMap.put("referer", "portal.html");
//        formMap.put("quickforward", "yes");
//        formMap.put("handlekey", "ls");
//        formMap.put("sectouchpoint", "0");
//
//        return HttpUtil.doPostGetgetHttpResult(LOGIN_URL, headParamsMap, formMap);
//    }

    /**
     * 删除以/开头的字符串
     *
     * @param url
     * @return
     * @Description: trimSplit
     * @date 2019年6月29日 下午11:27:44
     */
    private String trimSplit(String url) {

        if (url != null && url.startsWith("/")) {
            return url.substring(1);
        }

        return url;
    }

    @Override
    public void start() {
        if (!isRuning()){
            this.parseHome();
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
