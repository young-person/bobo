package com.app.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Objects;
/**
 * excel 文件写入
 * @author user
 *
 */
public interface ExcelService{
	Logger LOGGER = LoggerFactory.getLogger(ExcelService.class);
	/**
	 * 获取excel 写入模板
	 * @return
	 */
	File getInPath();
	/**
	 * SheetName
	 * @return
	 */
	String getName();
	/**
	 * excel 输出
	 * @return
	 */
	File getOutFile();

	default <T> void writeDataToExcel(List<T> datas, CallBack<T> callBack) throws InvalidFormatException, IOException {
		if (Objects.isNull(datas) || !getInPath().exists() || !getOutFile().exists()) {
			LOGGER.error("写入excel模板数据不正确：【{}】",getInPath());
			return;
		}
		
		InputStream inputStream = null;
		OutputStream outputStream = null;
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
		outputStream = new FileOutputStream(getOutFile());
		workbook.write(outputStream);
	}

	interface CallBack<T> {
		void writeExcel(int index, T bean, Sheet sheet);
	}
}
