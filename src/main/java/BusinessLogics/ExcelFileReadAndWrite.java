package BusinessLogics;

import BaseTest.BaseClass;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class ExcelFileReadAndWrite extends BaseClass {

    public static class ExcelReader {
        private final Workbook workbook;

        public ExcelReader(String excelFilePath) throws IOException, InvalidFormatException {
            try (FileInputStream file = new FileInputStream(new File(excelFilePath))) {
                this.workbook = new XSSFWorkbook(file);
            }
        }

        public String getCellData(String sheetName, int rowIndex, int colIndex) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " does not exist");
            }
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                return ""; // Return empty string if row doesn't exist
            }
            Cell cell = row.getCell(colIndex);
            if (cell == null) {
                return ""; // Return empty string if cell is null
            }
            return cell.toString().trim(); // Return cell value as a string
        }

        public int getRowCount(int sheetIndex) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            return sheet.getPhysicalNumberOfRows();
        }

        public int getColumnCount(int sheetIndex) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            Row row = sheet.getRow(0);
            return row != null ? row.getPhysicalNumberOfCells() : 0;
        }

        public void close() throws IOException {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    public static void saveDynamicDataToExcel(String filePath, String sheetName, String headerName, String value) {
        Workbook workbook;
        Sheet sheet;

        try {
            File file = new File(filePath);
            if (file.exists()) {
                // Load the existing workbook
                FileInputStream fileInputStream = new FileInputStream(file);
                workbook = new XSSFWorkbook(fileInputStream);
                sheet = workbook.getSheet(sheetName);

                // Create the sheet if it doesn't exist
                if (sheet == null) {
                    sheet = workbook.createSheet(sheetName);
                }
                fileInputStream.close();
            } else {
                // Create a new workbook and sheet if file doesn't exist
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet(sheetName);
            }

            // Check if the first row exists, else create it
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                headerRow = sheet.createRow(0);
            }

            // Find the header index to append the value in the correct column
            int headerCellIndex = -1;
            for (Cell cell : headerRow) {
                if (cell.getStringCellValue().equals(headerName)) {
                    headerCellIndex = cell.getColumnIndex();
                    break;
                }
            }

            if (headerCellIndex == -1) {
                // If header does not exist, create it and get the column index
                headerCellIndex = headerRow.getPhysicalNumberOfCells();
                headerRow.createCell(headerCellIndex).setCellValue(headerName);
            }

            // Check if the second row exists (this is where the data will be added), if not create it
            Row dataRow = sheet.getRow(1);
            if (dataRow == null) {
                dataRow = sheet.createRow(1);
            }

            // Add the value under the header column
            dataRow.createCell(headerCellIndex).setCellValue(value);

            // Write the updated workbook back to the file
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                workbook.write(fileOutputStream);
            }

            workbook.close();
            System.out.println("Data updated in Excel file successfully: " + filePath);

        } catch (IOException e) {
            System.err.println("Error working with Excel file: " + e.getMessage());
        }
    }
}
