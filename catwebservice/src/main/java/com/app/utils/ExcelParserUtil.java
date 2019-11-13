package com.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Excel文件解析
 */
public class ExcelParserUtil {

	public void parserFile(File file) {

	}

	private static final Logger logger = LoggerFactory.getLogger(ExcelParserUtil.class);

	private String filePath;
	private String sheetName;
	private int firstRow;
	private int lastRow;
	private File file;

	private int codeIndex = 0; // 报表库SQL位置
	private int nameIndex = 1;// 生产库SQL位置

	private Workbook workBook;
	private Sheet sheet;
	private Map<String, String> map;
	private List<String> unName;// 单位

	public static Logger getLogger() {
		return logger;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	public int getLastRow() {
		return lastRow;
	}

	public void setLastRow(int lastRow) {
		this.lastRow = lastRow;
	}

	public int getCodeIndex() {
		return codeIndex;
	}

	public void setCodeIndex(int codeIndex) {
		this.codeIndex = codeIndex;
	}

	public int getNameIndex() {
		return nameIndex;
	}

	public void setNameIndex(int nameIndex) {
		this.nameIndex = nameIndex;
	}

	public Workbook getWorkBook() {
		return workBook;
	}

	public void setWorkBook(Workbook workBook) {
		this.workBook = workBook;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public List<String> getUnName() {
		return unName;
	}

	public void setUnName(List<String> unName) {
		this.unName = unName;
	}

	public ExcelParserUtil(String filePath, String sheetName) {
		this.filePath = filePath;
		this.sheetName = sheetName;
		this.load();
	}

	public ExcelParserUtil(File file, String sheetName) {
		this.file = file;
		this.sheetName = sheetName;
		this.load();
	}

	private void load() {
		FileInputStream inStream = null;
		try {
			if (null == this.file) {
				inStream = new FileInputStream(new File(filePath));
			} else {
				inStream = new FileInputStream(this.file);
			}

			workBook = WorkbookFactory.create(inStream);
			sheet = workBook.getSheet(sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String getCellValue(Cell cell) throws Exception {
		String cellValue = "";
		DataFormatter formatter = new DataFormatter();
		if (cell != null) {
			FormulaEvaluator evaluator = workBook.getCreationHelper().createFormulaEvaluator();
			switch (cell.getCellType()) {
			case NUMERIC: // 数字
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = formatter.createFormat(cell).format(cell);
				} else {
					double value = cell.getNumericCellValue();
					if (0 == cell.getCellStyle().getDataFormat()) {// 默认格式
						cellValue = formatter.getDefaultFormat(cell).format(value);
						if (cellValue.length() >= 15) {
							cellValue = String.format("%.2f", value);
						}
					} else {// 自定义格式
						Format format = formatter.createFormat(cell);
						cellValue = format.format(value);
					}
				}
				break;
			case STRING: // 字符串
				cellValue = cell.getStringCellValue();
				break;
			case BOOLEAN: // boolean 类型
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case FORMULA: // 公式
				try {
					CellValue cellValueForMula = evaluator.evaluate(cell);
					switch (cellValueForMula.getCellType()) {
					case BOOLEAN:
						cellValue = String.valueOf(cellValueForMula.getBooleanValue());
						break;
					case NUMERIC:
						double value = cellValueForMula.getNumberValue();
						cellValue = formatter.createFormat(cell).format(value);
						if (cellValue.length() >= 15) {
							cellValue = String.format("%.2f", value);
						}
						break;
					case STRING:
						cellValue = cellValueForMula.getStringValue();
						break;
					case _NONE:
						cellValue = "";
						break;
					case ERROR:
						cellValue = "";
						break;
					case FORMULA:
						cellValue = "";
						break;
					default:
						break;
					}

				} catch (Exception e) {
					logger.info("excel公式解析异常：" + e.getMessage());
					cellValue = "";
				}
				break;
			case BLANK:
				cellValue = "";
				break;
			case ERROR:
				cellValue = "";
				break;
			default:
				cellValue = cell.toString().trim();
				break;
			}
		}
		return cellValue.trim();
	}

	/**
	 * 获取excel数据
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getSheetData() {
		List<Map<String, Object>> sheetData = new ArrayList<Map<String, Object>>();
		try {
			map = AllMergedRegion();
			for (int rowNum = firstRow; rowNum <= lastRow; rowNum++) {
				Row rower = sheet.getRow(rowNum);
				if (null != rower && rower.getZeroHeight() != true && rower.getFirstCellNum() >= 0) {
					Cell codeCell = rower.getCell(codeIndex);
					String codeValue = readCellValue(codeCell, rowNum, codeIndex);

					Cell nameCell = rower.getCell(nameIndex);
					String nameValue = readCellValue(nameCell, rowNum, nameIndex);

					Map<String, Object> detailedExcel = new HashMap<String, Object>();
					detailedExcel.put(codeValue, nameValue);
					System.out.println(codeValue + "-----------" + nameValue);
					sheetData.add(detailedExcel);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheetData;
	}

	/**
	 * 获取指定单元格的内容
	 * 
	 * @param cell
	 * @param rowNum
	 * @param colNum
	 * @return
	 * @throws Exception
	 */
	private String readCellValue(Cell cell, Integer rowNum, Integer colNum) throws Exception {
		String resultValue = "";
		String cellData = this.getCellValue(cell);
		if (!cellData.equals("")) { // 不为空
			resultValue = cellData;
		} else { // 为空，判断是否为合并单元格
			Unit unit = new Unit(rowNum, colNum);
			if (map.containsKey(unit.getvalue())) {
				String tempValue = map.get(unit.getvalue());
				if (!("".equals(tempValue))) {
					resultValue = tempValue.replaceAll(" ", "");
				}
			}
		}
		return resultValue;
	}

	/**
	 * 读取文件n*n矩阵
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> readFirstRowDatas() throws Exception {
		// 判断时，按照n*n矩阵分析（n是行和列的最小数，n的最小值为5，最大值为15）
		List<Map<String, Object>> views = new ArrayList<Map<String, Object>>();
		map = AllMergedRegion();
		int num = 0;
		for (int rowNum = firstRow; rowNum <= lastRow; rowNum++) {
			Row rower = sheet.getRow(rowNum);
			if (null != rower) {
				if (num == 0) {
					num = rower.getLastCellNum();
				}
				num = rower.getLastCellNum() < num ? num : rower.getLastCellNum(); // 获取列
			}
		}
		num = num < lastRow ? num : lastRow; // 比较行和列，取最小数
		if (num < 5) {
			num = 5;
		} else if (num > 15) {
			num = 15;
		}

		for (int rowNum = firstRow; rowNum < num; rowNum++) {
			Row rower = sheet.getRow(rowNum);
			if (null != rower && rower.getZeroHeight() != true && rower.getFirstCellNum() >= 0) {
				// 开始列
				int firstColumn = 0;
				for (int j = firstColumn; j < num; j++) {
					// 判断列隐藏
					if (!sheet.isColumnHidden(j)) {
						Cell cell = rower.getCell(j);
						String resultValue = readCellValue(cell, rowNum, j);
						Map<String, Object> m = new HashMap<String, Object>();

						m.put("value", resultValue);
						m.put("row", rowNum);
						m.put("col", j);

						views.add(m);
					}
				}
			}
		}
		return views;
	}

	private Map<String, String> AllMergedRegion() throws Exception {
		int sheetMergeCount = sheet.getNumMergedRegions();
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int firstRow = ca.getFirstRow();
			int lastColumn = ca.getLastColumn();
			int lastRow = ca.getLastRow();
			String cellValue = getCellValue(sheet.getRow(firstRow).getCell(firstColumn));
			for (int row = firstRow; row <= lastRow; row++) {
				for (int column = firstColumn; column <= lastColumn; column++) {
					Unit point = new Unit(row, column);
					map.put(point.getvalue(), cellValue);
				}
			}
		}
		return map;
	}

	/**
	 * 开始行
	 * 
	 * @return
	 */
	public Integer getStartRow() {
		return sheet.getFirstRowNum();
	}

	/**
	 * 结束行
	 * 
	 * @return
	 */
	public Integer getEndRow() {
		return sheet.getLastRowNum();
	}

	/**
	 * 获取行数
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer getCountRows() {
		return sheet.getLastRowNum();
	}

	/**
	 * 查询excel的sheet表名称（不包括隐藏的sheet表单） excel的sheet表名称
	 * 
	 * @param filePath 文件地址
	 * @return
	 */
	public static List<String> getSheetNames(String filePath) throws Exception {
		List<String> sheetNames = new ArrayList<String>();

		try {
			FileInputStream finput = new FileInputStream(filePath);
			Workbook wb = WorkbookFactory.create(finput);
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				Sheet sheet = wb.getSheetAt(i);
				if (!wb.isSheetHidden(i)) {
					sheetNames.add(sheet.getSheetName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("请选择正确的文件。");
		}
		return sheetNames;
	}

	public static List<String> getSheetNames(InputStream inStream) throws Exception {
		List<String> sheetNames = new ArrayList<String>();

		try {
			Workbook wb = WorkbookFactory.create(inStream);
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				Sheet sheet = wb.getSheetAt(i);
				if (!wb.isSheetHidden(i)) {
					sheetNames.add(sheet.getSheetName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("请选择正确的文件。");
		}
		return sheetNames;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public static class Unit {
		private int x;
		private int y;

		public Unit(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public void shiftPoint(int w, int h) {
			x += w;
			y += y;
		}

		public String getvalue() {
			return "(" + x + "," + y + ")";
		}

		public boolean pointEquals(Unit unit) {
			if (unit.x == x && unit.y == y)
				return true;
			return false;
		}
	}
}
