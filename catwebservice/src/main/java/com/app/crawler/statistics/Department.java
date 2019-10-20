package com.app.crawler.statistics;

import com.app.crawler.CrawlerDown;
import com.app.crawler.pojo.CNode;
import com.app.crawler.url.Linking;
import com.app.utils.HttpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * @Description: TODO
 * @date 2019年6月25日 下午1:21:54
 * @ClassName: Department
 */
public class Department implements CrawlerDown {


    private final String MAIN_URL = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017/";


    /**
     * 区划代码
     *
     * @Description: statisticsDistrictsCode
     * @date 2019年6月26日 上午11:53:58
     */
    public void statisticsDistrictsCode() {

        String content = HttpUtil.doGetRequest(Linking.DEPARTMENT.getUrl(), "gb2312");
        Document bs = Jsoup.parse(content);

        Elements tr_as = bs.select("tr.provincetr a");

        List<String> urls = new ArrayList<>();
        List<String> names = new ArrayList<>();
        // 全国省
        for (Element a : tr_as) {
            String href = a.attr("href");
            String url = href.split("\\.")[0];
            String txt = a.text();
            names.add(txt);
            urls.add(url);
        }
        continueLoad(urls, names);
    }

    /**
     * @Description: continueLoad
     * @date 2019年6月25日 下午1:31:08
     */
    private void continueLoad(List<String> urls, List<String> names) {

        CNode cNode = new CNode();
        cNode.setCode("10");
        cNode.setName("中国");
        List<String> cUrls = new ArrayList<>(urls.size());
        List<String> pCodes = new ArrayList<>();

        for (int index = 0; index < urls.size(); index++) {
            String url = urls.get(index);
            url = MAIN_URL + url + ".html";
            cUrls.add(index, url);
            CNode cNode2 = new CNode();
            cNode2.setCode(urls.get(index));
            cNode2.setName(names.get(index));
            cNode2.setpNode(cNode);
            cNode.getChildren().add(cNode2);

            pCodes.add(urls.get(index));
            writeToChinaTxt(cNode2, cNode);
        }

        while (true) {

            int clen = cUrls.size();
            List<String> newUrls = new ArrayList<>();
            List<String> allCodes = new ArrayList<>();

            for (int index = 0; index < clen; index++) {
                String url = cUrls.get(index);
                LOGGER.info("请求地址：【{}】", url);
                String content = HttpUtil.doGetRequest(url, "gb2312");
                if (StringUtils.isBlank(content)) {
                    continue;
                }

                Document bs = Jsoup.parse(content);
                Elements tables = bs.select("table[class*=table]");
                if (null != tables && tables.size() > 0) {
                    Element table = tables.first();
                    Elements trs = table.select("tr[class*=tr]");
                    int end = url.lastIndexOf("/");

                    String pCode = pCodes.get(index);// 父节点代码

                    String newUrl = url.substring(0, end + 1);

                    CNode node1 = depthFirst(cNode, pCode);// 获取父节点信息
                    depthFirst(node1);

                    for (Element tr : trs) {
                        CNode node = parseTr(tr, newUrls, newUrl, node1, allCodes);
                        node1.getChildren().add(node);
                    }

                    if (cUrls.size() == (index + 1)) {
                        cUrls = newUrls;
                        newUrls = new ArrayList<>();
                        clen = cUrls.size();
                        index = -1;
                        pCodes = allCodes;
                        allCodes = new ArrayList<>();
                    }
                }

            }
            if (newUrls.size() == 0) {
                break;
            }
        }

    }

    public CNode depthFirst(CNode cNode) {
        CNode tmp = cNode.getpNode();
        while (tmp != null) {

            if (cNode.getpNodes().size() == 0) {
                cNode.getpNodes().add(tmp);
            } else {
                boolean sure = true;
                for (CNode n : cNode.getpNodes()) {
                    if (n.getCode().equals(tmp.getCode())) {
                        sure = false;
                    }
                }
                if (sure) {
                    cNode.getpNodes().add(tmp);
                }
            }
            Collections.sort(cNode.getpNodes(), new Comparator<CNode>() {

                @Override
                public int compare(CNode o1, CNode o2) {
                    return o1.getCode().compareToIgnoreCase(o2.getCode());
                }

            });
            tmp = tmp.getpNode();
        }
        return cNode;
    }

    public CNode depthFirst(CNode cNode, String code) {
        List<CNode> childs = cNode.getChildren();

        CNode pNode = null;
        while (childs != null && childs.size() > 0) {

            List<CNode> allTempChilds = new ArrayList<>();

            boolean sure = false;
            for (CNode node : childs) {

                if (code.equals(node.getCode())) {
                    pNode = node;
                    sure = true;
                    break;
                }
                List<CNode> oneTempChilds = node.getChildren();
                if (oneTempChilds != null && oneTempChilds.size() > 0) {
                    for (CNode n : oneTempChilds) {
                        allTempChilds.add(n);
                    }
                }
            }
            if (sure) {
                break;
            }
            childs = allTempChilds;
        }
        return pNode;
    }

    private CNode parseTr(Element tr, List<String> cols, String newUrl, CNode pNode, List<String> allCodes) {
        Elements tds = tr.select("td");
        CNode node = new CNode();
        if (CollectionUtils.isNotEmpty(tds)) {
            Element as1 = null, as2 = null, as3 = null;
            switch (tds.size()) {
                case 2:
                    as1 = tds.get(0);
                    as2 = tds.get(1);
                    break;
                case 3:
                    as1 = tds.get(0);
                    as2 = tds.get(1);
                    as3 = tds.get(2);
                    break;
                default:
                    break;
            }
            if (as1 != null && as2 != null) {
                Elements aa = as1.select("a");
                if (null != aa && aa.size() > 0) {
                    Element a1 = aa.first();
                    if (a1 != null) {
                        String href = a1.attr("href");
                        newUrl = newUrl + href;
                        cols.add(newUrl);
                        allCodes.add(as1.text());
                    }
                }

                node.setpNode(pNode);
                node.setCode(as1.text());
                if (null != as3 && StringUtils.isNotBlank(as3.text())) {
                    node.setName(as3.text());
                } else {
                    node.setName(as2.text());
                }
                writeToChinaTxt(node, pNode);
            }
        }
        return node;
    }

    private void writeToChinaTxt(CNode node, CNode pNode) {
        List<String> datas = new ArrayList<>();
        datas.add(node.getCode());
        datas.add(node.getName());
        datas.add(pNode.getCode());
        datas.add(pNode.getName());
        String str = String.join(SPACE, datas);
        CrawlerDown.writeToTxt(str, "/app/datas/china.txt");

        String sql = "insert into china (name,code,parentcode) values ('" + node.getName() + "','" + node.getCode() + "','" + pNode.getCode() + "')";
        CrawlerDown.writeToTxt(sql, "/app/datas/china.sql");
    }

    @Override
    public void start() {
        if (!isRuning()) {
            statisticsDistrictsCode();
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
