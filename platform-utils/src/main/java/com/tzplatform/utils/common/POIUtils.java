package com.tzplatform.utils.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 读取excel工具类
 * 
 * @author leijie
 *
 */
public class POIUtils {
	private final static String xls = "xls";
	private final static String xlsx = "xlsx";

	/**
	 * 读入excel文件，解析后返回
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static List<String[]> readExcel(CommonsMultipartFile file,Integer startrowRum) throws IOException {
		// 创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
		List<String[]> list = new ArrayList<String[]>();
		// 检查文件
		if (checkFile(file)) {
			// 获得Workbook工作薄对象
			Workbook workbook = getWorkBook(file);
			if (workbook != null) {
				for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
					// 获得当前sheet工作表
					Sheet sheet = workbook.getSheetAt(sheetNum);
					if (sheet == null) {
						continue;
					}
					// 获得当前sheet的开始行
					int firstRowNum = sheet.getFirstRowNum();
					//自定义从哪行开始读取数据
					if(startrowRum!=null){
						firstRowNum=startrowRum;
					}
					// 获得当前sheet的结束行
					int lastRowNum = sheet.getLastRowNum();
					// 循环除了第一行的所有行
					for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
						// 获得当前行
						Row row = sheet.getRow(rowNum);
						if (row == null) {
							continue;
						}
						// 获得当前行的开始列
						int firstCellNum = row.getFirstCellNum();
						// 获得当前行的列数
						int lastCellNum = row.getLastCellNum();
						String[] cells = new String[lastCellNum];
						// 循环当前行
						for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
							Cell cell = row.getCell(cellNum);
							cells[cellNum] = getCellValue(cell);
						}
						list.add(cells);
					}
				}
			}
		}
		return list;
	}
	/**
	 * 读入excel文件，解析后返回
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static List<String[]> readExcel(CommonsMultipartFile file,Integer startrowRum,String type) throws IOException {
		// 创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
		List<String[]> list = new ArrayList<String[]>();
		// 检查文件
		if (checkFile(file)) {
			// 获得Workbook工作薄对象
			Workbook workbook = getWorkBook(file);
			if (workbook != null) {
//				for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
					// 获得当前sheet工作表,只获取第一个sheet
					Sheet sheet = workbook.getSheetAt(0);
//					if (sheet == null) {
//						continue;
//					}
					Row row1 = sheet.getRow(1);
					int columnNum =0;
                   if(row1 ==null){
                	   list.add(new String[]{"modelError"});
					   return list;
					}
					if(row1 !=null){
						//获取总列数
						 columnNum=row1.getPhysicalNumberOfCells();
						//校验是否为正确模板
						 //部门导入
						if("4".equals(type) && 5!=columnNum){
							list.add(new String[]{"modelError"});
							return list;
						}
						//老师
						if("2".equals(type) && 9!=columnNum){
							list.add(new String[]{"modelError"});
							return list;
						}
						//学生
						if("1".equals(type) && 14!=columnNum){
							list.add(new String[]{"modelError"});
							return list;
						}
					}
					
					// 获得当前sheet的开始行
					int firstRowNum = sheet.getFirstRowNum();
					//自定义从哪行开始读取数据
					if(startrowRum!=null){
						firstRowNum=startrowRum;
					}
					// 获得当前sheet的结束行
					int lastRowNum = sheet.getLastRowNum();
					
					if(firstRowNum==lastRowNum){
						
					}
					
					// 循环除了第一行的所有行
					for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
						// 获得当前行
						Row row = sheet.getRow(rowNum);
						if (row == null) {
							continue;
						}
						// 获得当前行的开始列
						int firstCellNum = row.getFirstCellNum();
						// 获得当前行的列数
						int lastCellNum = row.getLastCellNum();//
						String[] cells = new String[columnNum];
						// 循环当前行
						for (int cellNum = firstCellNum; cellNum < columnNum; cellNum++) {
							Cell cell = row.getCell(cellNum);
							cells[cellNum] = getCellValue(cell);
						}
						list.add(cells);
//					}
				}
			}
		}
		return list;
	}
	/**
	 * 判断文件对象类型
	 * 
	 * @param file
	 * @return
	 */
	public static boolean checkFile(CommonsMultipartFile file) {
		boolean result = true;
		// 判断文件是否是excel文件
		if (!file.isEmpty()) {
			// 获得文件名
			String fileName = file.getOriginalFilename();
			if (!fileName.endsWith(xls) && !fileName.endsWith(xlsx)) {
				result = false;
			}
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * 获取工作簿
	 * 
	 * @param file
	 * @return
	 */
	public static Workbook getWorkBook(CommonsMultipartFile file) {
		// 获得文件名
		String fileName = file.getOriginalFilename();
		// 创建Workbook工作薄对象，表示整个excel
		Workbook workbook = null;
		InputStream is = null;
		try {
			// 获取excel文件的io流
			is = file.getInputStream();
			// 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
			if (fileName.endsWith(xls)) {
				// 2003
				workbook = new HSSFWorkbook(is);
			} else if (fileName.endsWith(xlsx)) {
				// 2007
				workbook = new XSSFWorkbook(is);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return workbook;
	}

	// 获取列值
	public static String getCellValue(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		// 把数字当成String来读，避免出现1读成1.0的情况
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		// 判断数据的类型
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // 数字
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING: // 字符串
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN: // Boolean
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			cellValue = String.valueOf(cell.getCellFormula());
			break;
		case Cell.CELL_TYPE_BLANK: // 空值
			cellValue = "";
			break;
		case Cell.CELL_TYPE_ERROR: // 故障
			cellValue = "非法字符";
			break;
		default:
			cellValue = "未知类型";
			break;
		}
		return cellValue;
	}
}
