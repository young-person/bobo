package com.app.crawler.riches;

import com.app.crawler.base.RCache;
import com.app.crawler.riches.pojo.HistoryBean;
import com.app.crawler.riches.pojo.RicheBean;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class BRichesBase {


    /**
     * Excel 数据模板路径地址
     */
    protected String dataPath = RCache.CAT_CACHE.get("dataPath").getValue();

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
        File file = new File(dataPath);

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
