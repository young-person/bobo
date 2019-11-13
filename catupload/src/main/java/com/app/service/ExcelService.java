package com.app.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * excel 文件写入
 * 
 * @author user
 *
 */
public interface ExcelService {
	Logger LOGGER = LoggerFactory.getLogger(ExcelService.class);

	/**
	 * 获取excel 写入模板
	 * 
	 * @return
	 */
	File getInPath();

	/**
	 * SheetName
	 * 
	 * @return
	 */
	String getName();

	/**
	 * excel 输出
	 * 
	 * @return
	 */
	File getOutFile();

	/**
	 * 将数据写入excel
	 * 
	 * @param <T>
	 * @param datas
	 * @param callBack
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	default <T> void writeDataToExcel(List<T> datas, CallBack<T> callBack) {
		if (Objects.isNull(datas) || !getInPath().exists()) {
			LOGGER.error("写入excel模板数据不正确：【{}】", getInPath());
			return;
		}

		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(getInPath());
			Workbook workbook = WorkbookFactory.create(inputStream);
			if (StringUtils.isNotBlank(getName())) {
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
	default <T> void readDataToExcel(CallBack<T> callBack) {
		if (!getInPath().exists()) {
			LOGGER.error("读取excel模板数据不正确：【{}】", getInPath());
			return;
		}
		InputStream inputStream = null;
		try {
			File file = getInPath();
			inputStream = new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(inputStream);

			Sheet sheet = workbook.getSheetAt(0);
			int lastNum = sheet.getLastRowNum();
			for (int k = 1; k <= lastNum; k++) {
				callBack.writeExcel(k, null, sheet);
			}
		} catch (IOException e) {
			LOGGER.error("写入数据到excel失败", e);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}

	}

	interface CallBack<T> {
		void writeExcel(int index, T bean, Sheet sheet);
	}
}
