package io.excelUtils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public Object[][] getTestData(String filePath, String sheetName) throws IOException {

		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		XSSFSheet sheet = workbook.getSheet(sheetName);
		int rowsCount = sheet.getPhysicalNumberOfRows();
		System.out.println(rowsCount);
		int colCount = sheet.getRow(0).getLastCellNum();
		System.out.println(colCount);

		Object[][] data = new Object[rowsCount - 1][colCount]; // rowsCount-1 is because 1st row is header in excel
		for (int i = 1; i < rowsCount; i++) {

			XSSFRow row = sheet.getRow(i);
			for (int j = 0; j < colCount; j++) {

				XSSFCell cell = row.getCell(j);
				if (cell != null) {
					cell.setCellType(CellType.STRING); // Convert all to string
					data[i - 1][j] = cell.getStringCellValue();
				} else {
					data[i - 1][j] = "";
				}
			}

		}
		workbook.close();
		return data;
	}
}
