package com.xuecheng.framework.export;

import com.xuecheng.framework.utils.CheckUtils;
import jxl.Workbook;
import jxl.WorkbookSettings;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel读取类。可以读取各种老格式的Excel文件。这个类会先调用POI解析，如果失败调用JXL解析，再失败会调用JCOM解析。
 * 
 * <p>
 * JCOM会调用ActiveX调用Excel
 * API解析。JCOM要求将jcom.dll放到JDK的bin目录下，且要求系统中安装2007或更新的版本的Excel。
 * 
 * @author chenbo
 *
 */
public class Excel2TableImporter {
	/**
	 * 读取第一个sheet的数据
	 */
	public static List<List<String>> importFromExcel(File file)
			throws IOException {
		return importFromExcel(file, 0);
	}

	/**
	 * 
	 * @param sheetIndex
	 *            从0开始计数
	 */
	public static List<List<String>> importFromExcel(File file, int sheetIndex)
			throws IOException {
		if (file == null || !file.exists() || !file.canRead()) {
			return null;
		}

		List<List<String>> data = null;

		Sheet sheet = null;
		InputStream is = null;

		try {
			is = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(is);
			try {
				sheet = wb.getSheetAt(sheetIndex);
				data = readByPoi(sheet);
			} catch (IllegalArgumentException ex) {
				return null;
			}
		} catch (Exception e) {
			is.close();
			is = new FileInputStream(file);
			try {
				HSSFWorkbook wb = new HSSFWorkbook(is);
				try {
					sheet = wb.getSheetAt(sheetIndex);
					data = readByPoi(sheet);
				} catch (IllegalArgumentException ex) {
					return null;
				}
			} catch (Exception ex) {
				try {
					data = readByJxl(file, sheetIndex);
				} catch (Exception ex1) {
					try {
						data = readByJcom(file, sheetIndex);
					} catch (Exception e1) {
						throw new IOException(e1);
					}
				}
			} finally {
				is.close();
			}
		}

		for (int i = data.size() - 1; i >= 0; i--) {
			if (data.get(i) == null) {
				data.remove(i);
				continue;
			}

			boolean blankLine = true;
			for (String s : data.get(i)) {
				if (s != null) {
					blankLine = false;
					break;
				}
			}
			if (blankLine) {
				data.remove(i);
			} else {
				break;
			}
		}

		return data;
	}

	private static List<List<String>> readByPoi(Sheet sheet) throws IOException {
		if (sheet == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<List<String>> data = new ArrayList<List<String>>();
		for (int r = sheet.getFirstRowNum(); r <= sheet.getLastRowNum(); r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				data.add(null);
				continue;
			}

			boolean blankLine = true;
			List<String> cols = new ArrayList<String>();
			// c = 0 ???支持从第一列开始，如有其他需求请修改。
			for (int c = 0; c < row.getLastCellNum(); c++) {
				Cell cell = row.getCell(c);
				if (cell == null) 
				{
					cols.add("-1");
				} 
				else 
				{
					switch (cell.getCellType()) 
					{
					case HSSFCell.CELL_TYPE_STRING:
						String tmpv=cell.getStringCellValue();
						if(CheckUtils.isNotEmpty(tmpv))
							cols.add("-1");
						else 
							cols.add(tmpv);
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						String tmpbool="" + cell.getBooleanCellValue();
						if(CheckUtils.isNotEmpty(tmpbool))
							cols.add(tmpbool);
						else {
							cols.add("-1");
						}
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						String ss = cell.getCellStyle().getDataFormatString();
						// do not know why DATE can not be judged by
						// isCellDateFormatted
						if (HSSFDateUtil.isCellDateFormatted(cell)
								|| HSSFDateUtil
										.isCellInternalDateFormatted(cell)
								|| ss != null && ss.contains("yy")) {
							cols.add(sdf.format(cell.getDateCellValue()));
						} else {
							cell.setCellType(Cell.CELL_TYPE_STRING);

							String v_ = cell.getStringCellValue();
							// if(v_.endsWith(".0")){
							// v_ = v_.substring(0, v_.indexOf(".0"));
							// }
							if(CheckUtils.isNotEmpty(v_))
								cols.add(v_);
							else
								cols.add("-1");
						}
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						try {
							double numb=cell.getNumericCellValue();
							cols.add("" + numb);
						} catch (Exception e) {
							String gongshi=cell.getCellFormula();//String str=cell.getStringCellValue();
							cols.add(gongshi);
						}
						break;
					case HSSFCell.CELL_TYPE_BLANK:
						cols.add("-1");
						break;
					case HSSFCell.CELL_TYPE_ERROR:
						cols.add("-1");
						break;
					}
				}

				if (CheckUtils.isNotEmpty(cols.get(cols.size() - 1))) {
					blankLine = false;
				}
			}

			if (!blankLine) {
				data.add(cols);
			} else {
				data.add(null);
			}
		}

		return data;
	}

	public static List<String> readByColumn(File file,int columnIndex,int index){
		List<String> list = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		POIFSFileSystem fs;
		try {
			fs = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook wb;
			wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(index);
			int lastRowNum = sheet.getLastRowNum();
			for (int i = 0; i < lastRowNum; i++) {
				HSSFCell cell = sheet.getRow(i).getCell(columnIndex);
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_STRING:
					list.add(cell.getStringCellValue());
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					list.add("" + cell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					String ss = cell.getCellStyle().getDataFormatString();
					// do not know why DATE can not be judged by
					// isCellDateFormatted
					if (HSSFDateUtil.isCellDateFormatted(cell)
							|| HSSFDateUtil
									.isCellInternalDateFormatted(cell)
							|| ss != null && ss.contains("yy")) {
						list.add(sdf.format(cell.getDateCellValue()));
					} else {
						cell.setCellType(Cell.CELL_TYPE_STRING);

						String v_ = cell.getStringCellValue();
						// if(v_.endsWith(".0")){
						// v_ = v_.substring(0, v_.indexOf(".0"));
						// }
						list.add(v_);
					}
					break;
				case HSSFCell.CELL_TYPE_FORMULA:
					try {
						list.add("" + cell.getNumericCellValue());
					} catch (Exception e) {
						list.add(cell.getStringCellValue());
					}
					break;
				case HSSFCell.CELL_TYPE_BLANK:
					list.add(null);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//得到最后一行的行号
		return list;
	}

	// 读取Excel97以前的文件
	private static List<List<String>> readByJxl(File file, int sheetIndex)
			throws Exception {
		WorkbookSettings workbookSettings = new WorkbookSettings();
		workbookSettings.setEncoding("GBK"); // 解决中文乱码，或GBK
		Workbook book = Workbook.getWorkbook(file, workbookSettings);
		jxl.Sheet sheet = null;
		try {
			sheet = book.getSheet(sheetIndex);
		} catch (IndexOutOfBoundsException e) {
			// do nothing
		}

		if (sheet == null) {
			return null;
		}

		List<List<String>> data = new ArrayList<List<String>>();
		try {
			for (int r = 0; r < sheet.getRows(); r++) {
				boolean blankLine = true;
				List<String> cols = new ArrayList<String>();
				jxl.Cell[] cells = sheet.getRow(r);
				// c = 0 ???支持从第一列开始，如有其他需求请修改。
				for (int c = 0; c < cells.length; c++) {
					jxl.Cell cell = cells[c];
					String value = cell.getContents();
					cols.add(value);

					if (CheckUtils.isNotEmpty(value)) {
						blankLine = false;
					}
				}

				if (!blankLine) {
					data.add(cols);
				} else {
					data.add(null);
				}
			}
		} finally {
			book.close();
		}

		return data;
	}

	// 读取Excel97以前的文件
	private static List<List<String>> readByJcom(File file, int sheetIndex)
			throws Exception {
		// ReleaseManager rm = new ReleaseManager();
		// ExcelApplication excel = new ExcelApplication(rm);
		// excel.Visible(false);
		// ExcelWorkbooks xlBooks = excel.Workbooks();
		// ExcelWorkbook xlBook = xlBooks.Open(file.getAbsolutePath());
		// ExcelWorksheets xlSheets = xlBook.Worksheets();
		// ExcelWorksheet sheet = xlSheets.Item(sheetIndex + 1);
		// if (sheet == null) {
		// return null;
		// }
		//
		// IDispatch row = (IDispatch)sheet.get("Rows");
		// int rowSize = Integer.parseInt(row.get("Count").toString());
		// // IDispatch col = (IDispatch)sheet.get("Columns");
		// int colSize = 30; // Integer.parseInt(col.get("Count").toString());
		// ExcelRange xlRange = sheet.Cells();
		//
		// int blankLineCount = 0;
		//
		// List<List<String>> data = new ArrayList<List<String>>();
		// try {
		// int max_cols = 0;
		// for (int r = 1 ; r <= rowSize ; r++) {
		// boolean blankLine = true;
		// List<String> cols = new ArrayList<String>();
		// //c = 0 ???支持从第一列开始，如有其他需求请修改。
		// if ( r > 5) {
		// colSize = max_cols;
		// }
		// for (int c = 1 ; c <= colSize ; c++) {
		// Object value = xlRange.Item(r, c).Value();
		// cols.add(value == null ? "" : value.toString());
		//
		// if (StringUtils.isNotBlank(value == null ? "" : value.toString())) {
		// blankLine = false;
		// if (c > max_cols) {
		// max_cols = c;
		// }
		// }
		// }
		//
		// if (!blankLine) {
		// data.add(cols);
		// blankLineCount = 0;
		// } else {
		// data.add(null);
		// blankLineCount++;
		//
		// if (blankLineCount == 10) {
		// break;
		// }
		// }
		// }
		// } finally {
		// xlBook.Close(false, null, false);
		// xlBooks.Close();
		// excel.Quit();
		// rm.release();
		// }
		//
		// return data;

		// do not support excel97 because it reply on jcom which cannot deploy
		// on glassfish
		throw new RuntimeException(
				"DO NOT support older version of Excel (97 and older)");
	}
}
