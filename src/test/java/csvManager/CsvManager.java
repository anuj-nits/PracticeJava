package csvManager;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import logger.Log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class CsvManager {

    private static String filePath = System.getProperty("user.dir") + "/src/test/resources/data/sample.csv";

    /**
     * Writes data to csv file
     *
     * @param data     Data to be entered in csv file in a particular cell
     * @param row      Row number in which data needs to be entered
     * @param col      Column number in which data needs to be entered
     * @author anuj gupta
     */
    public static void writeToCSV(String data, int row, int col) throws Exception {

        File inputFile = new File(filePath);
        List<String[]> csvBody;

        // Read existing file
        try (CSVReader reader = new CSVReader(new FileReader(inputFile))) {
            csvBody = reader.readAll();

            // get CSV row column  and replace with by using row and column
            csvBody.get(row)[col] = data;
        }
        // Write to CSV file which is open
        try (CSVWriter writer = new CSVWriter(new FileWriter(inputFile), ',', CSVWriter.NO_QUOTE_CHARACTER)) {
            writer.writeAll(csvBody);
            writer.flush();
        }
        Log.info("Wrote data " + data + " to csv file at path: " + filePath + " at row: " + row + " and column: " + col);
    }

    /**
     * Writes data to csv file
     *
     * @param row      Row number in which data needs to be entered
     * @param col      Column number in which data needs to be entered
     * @author anuj gupta
     */
    public static String readFromCSV(int row, int col) throws Exception {

        File inputFile = new File(filePath);
        String data;

        // Read existing file
        try (CSVReader reader = new CSVReader(new FileReader(inputFile))) {
            List<String[]> csvBody = reader.readAll();

            // get CSV row column  and replace with by using row and column
            data = csvBody.get(row)[col];
        }
        return data;
    }
}
