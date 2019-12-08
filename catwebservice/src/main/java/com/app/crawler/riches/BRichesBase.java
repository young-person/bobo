package com.app.crawler.riches;

import com.alibaba.fastjson.JSON;
import com.app.config.CatXml;
import com.app.crawler.request.RestRequest;
import com.app.crawler.riches.pojo.HistoryBean;
import com.app.crawler.riches.pojo.RicheBean;
import com.app.crawler.riches.pojo.RicheResult;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BRichesBase {
    /**
     * 实时数据
     */
    protected String onlineUrl = "https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random=0.24647713895668777&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&stockCode=%s&codeType=%s&type=shsz";

    /**
     * 平安证券
     */
    protected String stockAppUrl = "https://m.stock.pingan.com/h5quote/quote/getReportData";

    /**
     * 日线
     */
    protected String dayUrl = "https://m.stock.pingan.com/h5quote/quote/getDayDataBigInt?random=0.5947266470674488&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&stockCode=%s&codeType=%s&period=day&day=%s";

    /**
     * 跳转到平安证券详情页面
     */
    protected String detailUrl = "https://m.stock.pingan.com/html/h5security/quote/detail.html?stock_code=%s&code_type=%s&type=shsz";

//    protected String detailUrl ="http://stockpage.10jqka.com.cn/%s/#xwgg";

    /**
     * 东方财富今日数据
     */
    protected String dfUrl = "http://25.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112402546766279164878_1573279192452&pn=1&pz=5000&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:6,m:0+t:13,m:0+t:80,m:1+t:2,m:1+t:23&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f26,f22,f11,f62,f128,f136,f115,f152&_=1573279192548";

    /**
     * 所有指标数据项
     */
    protected String allUrl = "http://25.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112402546766279164878_1573279192452&pn=1&pz=5000&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:6,m:0+t:13,m:0+t:80,m:1+t:2,m:1+t:23&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13,f14,f15,f16,f17,f18,f19,f20,f21,f22,f23,f24,f25,f26,f27,f28,f29,f30,f31,f32,f33,f34,f35,f36,f37,f38,f39,f40,f41,f42,f43,f44,f45,f46,f47,f48,f49,f50,f51,f52,f53,f54,f55,f56,f57,f58,f59,f60,f61,f62,f63,f64,f65,f66,f67,f68,f69,f70,f71,f72,f73,f74,f75,f76,f77,f78,f79,f80,f81,f82,f83,f84,f85,f86,f87,f88,f89,f90,f91,f92,f93,f94,f95,f96,f97,f98,f99,f100,f101,f102,f103,f104,f105,f106,f107,f108,f109,f110,f111,f112,f113,f114,f115,f116,f117,f118,f119,f120,f121,f122,f123,f124,f125,f126,f127,f128,f129&_=1573279192548";
    /**
     * 完整的所有指标数据项URL
     */
//    protected String allUrl = "http://25.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112402546766279164878_1573279192452&pn=1&pz=5000&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:6,m:0+t:13,m:0+t:80,m:1+t:2,m:1+t:23&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13,f14,f15,f16,f17,f18,f19,f20,f21,f22,f23,f24,f25,f26,f27,f28,f29,f30,f31,f32,f33,f34,f35,f36,f37,f38,f39,f40,f41,f42,f43,f44,f45,f46,f47,f48,f49,f50,f51,f52,f53,f54,f55,f56,f57,f58,f59,f60,f61,f62,f63,f64,f65,f66,f67,f68,f69,f70,f71,f72,f73,f74,f75,f76,f77,f78,f79,f80,f81,f82,f83,f84,f85,f86,f87,f88,f89,f90,f91,f92,f93,f94,f95,f96,f97,f98,f99,f100,f101,f102,f103,f104,f105,f106,f107,f108,f109,f110,f111,f112,f113,f114,f115,f116,f117,f118,f119,f120,f121,f122,f123,f124,f125,f126,f127,f128,f129,f130,f131,f132,f133,f134,f135,f136,f137,f138,f139,f140,f141,f142,f143,f144,f145,f146,f147,f148,f149,f150,f151,f152,f153,f154,f155,f156,f157,f158,f159,f160,f161,f162,f163,f164,f165,f166,f167,f168,f169,f170,f171,f172,f173,f174,f175,f176,f177,f178,f179,f180,f181,f182,f183,f184,f185,f186,f187,f188,f189,f190,f191,f192,f193,f194,f195,f196,f197,f198,f199,f200,f201,f202,f203,f204,f205,f206,f207,f208,f209,f210,f211,f212,f213,f214,f215,f216,f217,f218,f219,f220,f221,f222,f223,f224,f225,f226,f227,f228,f229,f230,f231,f232,f233,f234,f235,f236,f237,f238,f239,f240,f241,f242,f243,f244,f245,f246,f247,f248,f249,f250,f251,f252,f253,f254,f255,f256,f257,f258,f259,f260,f261,f262,f263,f264,f265,f266,f267,f268,f269,f270,f271,f272,f273,f274,f275,f276,f277,f278,f279,f280,f281,f282,f283,f284,f285,f286,f287,f288,f289,f290,f291,f292,f293&_=1573279192548";

    /**
     * arg[0] 股票代码 arg[1] 年份 arg[2] 季度 网易股票历史数据
     */
    protected String wyUrl = "http://quotes.money.163.com/trade/lsjysj_%s.html?year=%s&season=%s";

    /**
     * 同花顺详情
     */
    protected String stockPageUrl = "http://stockpage.10jqka.com.cn/%s/";

    /**
     *股票对应的类型
     */
    protected static Map<String,String> codeTypeMap = new ConcurrentHashMap<>();

    static {
        RestRequest restRequest = new RestRequest();
        String content = restRequest.doGet("https://m.stock.pingan.com/h5quote/quote/getReportData?descOrAsc=desc&thirdAccount=&tokenId=&signature=&columnId=hand&count=5000&type=shsz&chnl=&marketType=shsz&random=0.35715587574136154&requestId=&rdm=&begin=0&key=&timestamp=");

        RicheResult handResult = JSON.parseObject(content, RicheResult.class);
        for(RicheBean r : handResult.getResults()){
            codeTypeMap.put(r.getCode(),r.getCodeType());
        }
    }

    protected CatXml catXml = new CatXml();

    /**
     * 股票类型
     */
    protected final Map<String, String> typeName = new HashMap<String, String>() {
        private static final long serialVersionUID = 6893069965305064089L;

        {
            put("4621", "创业");
            put("4614", "深证");
            put("4353", "沪A");
            put("4609", "深A");
        }
    };

    protected File getExcelPath(RicheBean bean, String type) {

        StringBuilder builder = new StringBuilder();
        builder.append(bean.getCode());
        builder.append("_");

        char p = '*';
        for (int index = 0; index < bean.getStockName().length(); index++) {
            if (p != bean.getStockName().charAt(index)) {
                builder.append(bean.getStockName().charAt(index));
            }
        }

        builder.append(type);
        File file = null;
        try {
            file = ResourceUtils.getFile(catXml.getDataPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File f1 = new File(file, this.typeName.get(bean.getCodeType()));
        if (!f1.exists()) {
            f1.mkdirs();
        }
        File f2 = new File(f1, builder.toString());
        return f2;
    }

    protected void writeValueToCell(Row row, HistoryBean historyBean, String prePrivce) {
        row.createCell(0).setCellValue(historyBean.getDate());
        row.createCell(1).setCellValue(historyBean.getHand());
        row.createCell(2).setCellValue(historyBean.getRisePrice());
        row.createCell(3).setCellValue(historyBean.getOpenPrice());
        row.createCell(4).setCellValue(historyBean.getClosePrice());
        row.createCell(5).setCellValue(prePrivce);
        row.createCell(6).setCellValue(historyBean.getMaxPrice());
        row.createCell(7).setCellValue(historyBean.getMinPrice());
        row.createCell(8).setCellValue(historyBean.getTotal());
        row.createCell(9).setCellValue(historyBean.getMoney());
        row.createCell(10).setCellValue(historyBean.getRiseMoney());
    }
}
