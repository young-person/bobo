package com.app.service.impl;

import com.app.service.ExcelService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @Description: TODO
 * @date 2019年11月12日 下午10:36:55
 * @ClassName: ExcelServiceImpl
 */
public class ExcelServiceImpl implements ExcelService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.app.service.ExcelService#getInPath()
	 */
	@Override
	public File getInPath() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.app.service.ExcelService#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.app.service.ExcelService#getOutFile()
	 */
	@Override
	public File getOutFile() {
		// TODO Auto-generated method stub
		return null;
	}

	public void readExcel(String filePath) throws IOException {
		File excel_file = new File(filePath);// 读取的文件路径
		FileInputStream input = new FileInputStream(excel_file); // 读取的文件路径
		XSSFWorkbook wb = new XSSFWorkbook(new BufferedInputStream(input));
		int sheet_numbers = wb.getNumberOfSheets();// 获取表的总数
		String[] sheetnames = new String[sheet_numbers];

		for (int i = 0; i < sheet_numbers; i++) {// 遍历所有表
			ArrayList<String[]> ls_a = new ArrayList<String[]>(); // 用来存储某个表 读取出来的数据
			XSSFSheet sheet = wb.getSheetAt(i); // 获取 某个表
			sheetnames[i] = sheet.getSheetName();// 获取表名，存入数组

			int rows_num = sheet.getLastRowNum();// 获取行数
			for (int rows = 0; rows < rows_num; rows++) {
				XSSFRow row = sheet.getRow(rows);// 取得某一行 对象

				if (row != null && !(row.equals(""))) {
					int columns_num = row.getLastCellNum();// 获取列数

					String[] s = new String[5];// 初始化数组长度
					for (int columns = 0; columns < columns_num; columns++) {
						XSSFCell cell = row.getCell(columns);
						if (cell != null) {
							switch (cell.getCellType()) {
							case STRING: // 字符串
								cell.getStringCellValue();
								break;
							case NUMERIC: // 数字
								cell.getNumericCellValue();
								break;
							case BLANK: // 空值
								s[columns] = " ";
								break;
							default:
								break;
							}
						}
					}
				}
			}
			input.close();
//        write_Excel( xls_write_Address, ls, sheetnames ,tips_cmd)  ;                
		}
		wb.close();
	}

	public static void init() throws InvalidFormatException, IOException {
		long startTime = System.currentTimeMillis();
		BufferedOutputStream outputStream = null;
		SXSSFWorkbook workbook = null;
		try {
			File file = new File("C:\\Users\\user\\Desktop\\users.xlsx");
			FileInputStream fs = new FileInputStream(file);
			XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(fs);
			workbook = new SXSSFWorkbook(xSSFWorkbook);
			// 获取第一个Sheet页
			SXSSFSheet sheet = workbook.getSheetAt(0);
			String name = sheet.getSheetName();
			
			XSSFSheet xssfSheet = workbook.getXSSFWorkbook().getSheetAt(0);
			XSSFRow row1 = xssfSheet.getRow(2);
			
			XSSFCell sXSSFCell = row1.getCell(0);
			
			for (int z = 3; z < 10000; z++) {
				
				SXSSFRow row = sheet.createRow(z);
				for (int j = 0; j < 10; j++) {
					row.createCell(j).setCellValue("你好：" + j);
				}
			}
			if (file.exists()) {
				System.err.println(1);
			}
			outputStream = new BufferedOutputStream(new FileOutputStream(file));
			workbook.write(outputStream);
			outputStream.flush();
			workbook.dispose();// 释放workbook所占用的所有windows资源
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (Objects.nonNull(workbook)) {
				workbook.close();
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);

	}

	public static void main(String[] args) throws InvalidFormatException, IOException {
		init();
	}

	@SuppressWarnings("resource")
	public static void main2(String[] args) throws FileNotFoundException, InvalidFormatException {
		long startTime = System.currentTimeMillis();
		String filePath = "C:\\Users\\user\\Desktop\\1.xlsx";
		SXSSFWorkbook sxssfWorkbook = null;
		BufferedOutputStream outputStream = null;
		try {
			// 这样表示SXSSFWorkbook只会保留100条数据在内存中，其它的数据都会写到磁盘里，这样的话占用的内存就会很少
			sxssfWorkbook = new SXSSFWorkbook(getXSSFWorkbook(filePath), 100);
			// 获取第一个Sheet页
			SXSSFSheet sheet = sxssfWorkbook.getSheetAt(0);
			for (int i = 0; i < 150; i++) {
				for (int z = 0; z < 10000; z++) {
					SXSSFRow row = sheet.createRow(i * 10000 + z);
					for (int j = 0; j < 10; j++) {
						row.createCell(j).setCellValue("你好：" + j);
					}
				}
			}
			outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
			sxssfWorkbook.write(outputStream);
			outputStream.flush();
			sxssfWorkbook.dispose();// 释放workbook所占用的所有windows资源
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		long endTime = System.currentTimeMillis();
	}

	/**
	 * 先创建一个XSSFWorkbook对象
	 * 
	 * @param filePath
	 * @return
	 */
	public static XSSFWorkbook getXSSFWorkbook(String filePath) {
		XSSFWorkbook workbook = null;
		BufferedOutputStream outputStream = null;
		try {
			File fileXlsxPath = new File(filePath);
			outputStream = new BufferedOutputStream(new FileOutputStream(fileXlsxPath));
			workbook = new XSSFWorkbook();
			workbook.createSheet("测试");
			workbook.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return workbook;
	}

}
