package com.app.service;

import com.app.crawler.base.BaseClass;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.util.List;
import java.util.Objects;

/**
 * excel 文件写入
 * 
 * @author user
 *
 */
public abstract class ExcelService extends BaseClass {

	/**
	 * 获取excel 写入模板
	 * 
	 * @return
	 */
	public abstract File getInPath();

	/**
	 * SheetName
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * excel 输出
	 * 
	 * @return
	 */
	public abstract File getOutFile();

	/**
	 * 将数据写入excel
	 * 
	 * @param <T>
	 * @param datas
	 * @param callBack
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public <T> void writeDataToExcel(List<T> datas, CallBack<T> callBack) {
		if (Objects.isNull(datas) || !getInPath().exists()) {
			LOGGER.error("写入excel模板数据不正确：【{}】", getInPath());
			return;
		}

		InputStream inputStream = null;
		OutputStream outputStream = null;
		Workbook workbook = null;
		try {
			inputStream = new FileInputStream(getInPath());
			workbook = WorkbookFactory.create(inputStream);
			if (Objects.nonNull(getName())) {
				workbook.setSheetName(0, getName());
			}
			Sheet sheet = workbook.getSheetAt(0);
			for (int index = 0; index < datas.size(); index++) {
				T bean = datas.get(index);
				callBack.writeExcel(index, bean, sheet);
			}
			if (Objects.isNull(getOutFile())) {
				return;
			}
			outputStream = new FileOutputStream(getOutFile());
			workbook.write(outputStream);
		} catch (IOException e) {
			IOUtils.closeQuietly(workbook);
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(inputStream);
		}

	}

	/**
	 * 读出excel得数据
	 * 
	 * @param <T>
	 * @param callBack
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public  <T> void readDataToExcel(CallBack<T> callBack) {
		if (!getInPath().exists()) {
			LOGGER.error("读取excel模板数据不正确：【{}】", getInPath());
			return;
		}
		InputStream inputStream = null;
		Workbook workbook = null;
		try {
			File file = getInPath();
			inputStream = new FileInputStream(file);
			workbook = WorkbookFactory.create(inputStream);

			Sheet sheet = workbook.getSheetAt(0);
			int lastNum = sheet.getLastRowNum();
			for (int k = 1; k <= lastNum; k++) {
				callBack.writeExcel(k, null, sheet);
			}
		} catch (IOException e) {
			LOGGER.error("写入数据到excel失败", e);
		} finally {
			IOUtils.closeQuietly(workbook);
			IOUtils.closeQuietly(inputStream);
		}

	}

	
	public interface CallBack<T> {
		void writeExcel(int index, T bean, Sheet sheet);
	}
	
	
	
}
