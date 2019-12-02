package com.app.crawler.riches.producer;

import com.app.crawler.riches.BRichesBase;
import com.app.crawler.riches.pojo.HistoryBean;
import com.app.crawler.riches.pojo.RicheBean;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersistExcel extends BRichesBase implements Persist {
    @Override
    public void writeHistoryDataToFile(RicheBean bean, List<HistoryBean> datas) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        Workbook workbook = null;
        try {
            if (Objects.nonNull(datas) && datas.size() > 0) {
                datas.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
                File existFile = this.getExcelPath(bean, ".xlsx");
                existFile.delete();//先全部增量

                if (!existFile.exists()) {
                    inputStream = new FileInputStream(new File(new File(catXml.getDataPath()), "share.xlsx"));
                    workbook = WorkbookFactory.create(inputStream);
                    workbook.setSheetName(0, bean.getCode());
                    Sheet sheet = workbook.getSheetAt(0);
                    for (int i = 0; i < datas.size(); i++) {
                        sheet.createRow(i + 1);
                        Row row = sheet.getRow(i + 1);
                        HistoryBean historyBean = datas.get(i);
                        String prePrivce = i == (datas.size() - 1) ? "0" : datas.get(i + 1).getClosePrice();
                        this.writeValueToCell(row, historyBean, prePrivce);
                    }
                    outputStream = new FileOutputStream(this.getExcelPath(bean, ".xlsx"));
                    workbook.write(outputStream);
                    outputStream.flush();
                    return;
                }

                inputStream = new FileInputStream(existFile);
                workbook = WorkbookFactory.create(inputStream);
                workbook.setSheetName(0, bean.getCode());
                Sheet sheet = workbook.getSheetAt(0);
                int first = sheet.getFirstRowNum();
                Row preRow = sheet.getRow(first + 1);
                String time = preRow.getCell(0).getStringCellValue();
                if (time.equals(datas.get(0).getDate())) {
                    return;
                }
                sheet.shiftRows(first + 1, sheet.getLastRowNum(), datas.size());

                for (int i = 0; i < datas.size(); i++) {
                    Row row = sheet.createRow(i + 1 + first);
                    HistoryBean historyBean = datas.get(i);
                    String prePrivce = i == (datas.size() - 1) ? "0" : datas.get(i + 1).getClosePrice();
                    this.writeValueToCell(row, historyBean, prePrivce);
                }
                outputStream = new FileOutputStream(this.getExcelPath(bean, ".xlsx"));
                workbook.write(outputStream);
                outputStream.flush();
            }
        } finally {
            IOUtils.closeQuietly(workbook);
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(inputStream);
        }
    }

    @Override
    public List<HistoryBean> readHistoryFromFile(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        int lastNum = sheet.getLastRowNum();
        List<HistoryBean> result = new ArrayList<>();
        for (int k = 1; k <= lastNum; k++) {
            Row row = sheet.getRow(k);
            HistoryBean bean = new HistoryBean();
            bean.setDate(row.getCell(0).getStringCellValue());
            bean.setOpenPrice(row.getCell(1).getStringCellValue());
            bean.setClosePrice(row.getCell(2).getStringCellValue());
            bean.setMaxPrice(row.getCell(3).getStringCellValue());
            bean.setMinPrice(row.getCell(4).getStringCellValue());
            bean.setTotal(row.getCell(5).getStringCellValue());
            bean.setMoney(row.getCell(6).getStringCellValue());
            bean.setMa5(row.getCell(7).getStringCellValue());
            bean.setMa10(row.getCell(8).getStringCellValue());
            bean.setMa30(row.getCell(9).getStringCellValue());
            bean.setHand(row.getCell(10).getStringCellValue());
            bean.setRisePrice(row.getCell(11).getStringCellValue());
            bean.setRiseMoney(row.getCell(12).getStringCellValue());
            result.add(bean);
        }
        return result;
    }
}
