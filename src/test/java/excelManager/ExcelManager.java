package excelManager;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Manage test data via excel file
 */
public class ExcelManager {

    private static Workbook workbook;
    private static Sheet sheet;
    private static FileInputStream fis;
    private static String workBookPath = System.getProperty("user.dir") + "/src/test/resources/data/sample.xlsx";

    /**
     * Opens the file input stream
     *
     * @author anuj gupta
     */
    private static void openInputStream() throws Exception {

        fis = new FileInputStream(workBookPath);

        if (workBookPath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(fis);
        } else {
            workbook = new HSSFWorkbook(fis);
        }
        workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
    }

    /**
     * Closes the file input stream
     *
     * @author anuj gupta
     */
    private static void closeInputStream() throws Exception {

        fis.close();
    }

    /**
     * Returns no. of columns in the excel sheet
     *
     * @param sheetName Name of excel sheet
     * @return Number of columns in excel
     * @author anuj gupta
     */
    private static Integer getColumnCount(String sheetName) {

        // Get sheet with the given name
        sheet = workbook.getSheet(sheetName);

        // Stores the last cell number of the first row in sheet
        return (int) sheet.getRow(0).getLastCellNum();
    }

    /**
     * Returns no. of rows in the excel sheet
     *
     * @param sheetName Name of excel sheet
     * @return Number of rows in excel
     * @author anuj gupta
     */
    private static Integer getRowCount(String sheetName) {

        // Get sheet with the given name
        sheet = workbook.getSheet(sheetName);
        return (sheet.getLastRowNum() + 1);
    }

    /**
     * Returns value of a particular cell in excel sheet
     *
     * @param sheetName    Name of excel sheet
     * @param rowNumber    Row no. from which data needs to be fetched
     * @param columnNumber Column no. from which data needs to be fetched
     * @return value of cell in string format
     * @author anuj gupta
     */
    private static String getCellData(String sheetName, int rowNumber, int columnNumber) throws Exception {

        String data = "";
        openInputStream();

        // Get sheet with the given name
        sheet = workbook.getSheet(sheetName);

        // Stores the cell at the given index
        Cell cell = sheet.getRow(rowNumber).getCell(columnNumber);

        if (cell == null)
            data = "";

            // Checks if cell value if of type boolean
        else if (cell.getCellType() == CellType.BOOLEAN)
            data = String.valueOf(cell.getBooleanCellValue());

            // Checks if cell value if of type numeric
        else if (cell.getCellType() == CellType.NUMERIC) {
            DataFormatter formatter = new DataFormatter();
            data = formatter.formatCellValue(cell);
        }

        // Checks if cell value if of type string
        else if (cell.getCellType() == CellType.STRING)
            data = cell.getStringCellValue();

        closeInputStream();
        return data;
    }

    /**
     * Returns data of entire row in the form of a HashMap
     *
     * @param sheetName name of the sheet whose data is being fetched
     * @param rowNumber number of the row from which data is being fetched
     * @return Data of the entire row in a Map
     * @author anuj gupta
     */
    public static Map<String, String> getRowAsMap(String sheetName, int rowNumber) throws Exception {

        // Stores data of each row
        Map<String, String> rowData = new HashMap<>();
        openInputStream();
        int columnCount = getColumnCount(sheetName);

        for (int i = 0; i < columnCount; i++)
            rowData.put(getCellData(sheetName, 0, i), getCellData(sheetName, rowNumber, i));
        closeInputStream();
        return rowData;
    }

    /**
     * Returns column as a map
     *
     * @param sheetName name of the sheet whose data is being fetched
     * @return Data of the entire column in a Map
     * @author anuj gupta
     */
    public static Map<String, String> getColumnsAsMap(String sheetName) throws Exception {

        Map<String, String> columnData = new HashMap<>();
        openInputStream();
        int rowCount = getRowCount(sheetName);

        for (int i = 0; i < rowCount; i++)
            columnData.put(getCellData(sheetName, i, 0), getCellData(sheetName, i, 1));

        closeInputStream();
        return columnData;
    }
}
