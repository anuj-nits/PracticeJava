package database;

import LogManager.LogManager;

import java.sql.*;
import java.util.*;

public class MysqlManager {

    private static MysqlManager mySQL = null;

    // Stores object of connection interface
    private static Connection connection;

    // Stores result of query executed
    private static ResultSet resultSet;

    // An object used to get information about the types and properties of the columns in a ResultSet object
    private static ResultSetMetaData resultSetMetaData;

    // List to store resultSet
    private static List<String> columnValues;

    // String to store first value in resultSet
    private static String firstCellValue;

    // String to store query to be executed
    private static String query;

    // Object used for executing a static SQL statement and returning the results it produces
    private static Statement statement;

    /**
     * Singleton method to create only a single instance of the MySQL class
     *
     * @return Object of MySQL class
     */
    private static MysqlManager getInstance() {

        if (mySQL == null)
            mySQL = new MysqlManager();

        return mySQL;
    }

    /**
     * Establish database connection
     *
     * @return Connection object of the database(Validation or Functional)
     * @author Anuj Gupta
     */
    public static Connection createConnection() throws Exception {

        // Returns the Class object associated with the class or interface with the given string name
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Attempts to establish a connection to the given database URL
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Selenium", "root", "root");

        //Log.info("MySQL DB Connection successful");

        return connection;
    }

    /**
     * Closes the database connection
     *
     * @author Anuj Gupta
     */
    public static void closeConnection() throws Exception {

        // Checks if the connection is not already closed
        if (!connection.isClosed()) {
            connection.close();
            //Log.info("MySQL DB connection closed");
        }
    }

    /**
     * Returns count of rows in database table
     *
     * @param tableName Name of the database column
     * @return Count of rows in database table
     * @author Anuj Gupta
     */
    public static Integer getRowCount(String tableName) throws Exception {

        // Query to store count of rows in table 'tableName'
        String query = "select count(*) as rows from " + tableName;

        // Stores the result of executed query in a list
        List<String> columnValues = getQuery(query, "rows");

        // Stores the value of first row in resultSet
        String firstCellValue = columnValues.get(0);

        // Converts string to interger format and returns it
        return Integer.parseInt(firstCellValue);
    }

    /**
     * Returns count of rows in a database table where a particular column has a specific value condition
     *
     * @param tableName   Name of the database table
     * @param columnName  Name of the database column
     * @param columnValue Value of the column
     * @return Count of rows in a database table where a particular column has a specific value condition
     * @author Anuj Gupta
     */
    public static Integer getPartialRowCount(String tableName, String columnName, String columnValue) throws Exception {

        // Query to get number of rows which satisfy a particular condition
        String rowQuery = "SELECT count(*) as rows from " + tableName + " where `" + columnName + "` = '" + columnValue + "'";

        // Stores the result of executed query in a list
        List<String> rows = getQuery(rowQuery, "rows");

        // Stores the value of first row in resultSet
        String countString = rows.get(0);

        // Convert string to integer format
        return Integer.parseInt(countString);
    }

    /**
     * Executes the query passed as parameter and returns list of results containing value of column passed as another parameter
     *
     * @param query      Database query to be executed
     * @param columnName Name of the database column whose value is to be fetched
     * @return Result of the query executed as list of strings
     * @author Anuj Gupta
     */
    private static List<String> getQuery(String query, String columnName) throws Exception {

        // Initialize statement object
        statement = connection.createStatement();

        // Executes the query
        resultSet = statement.executeQuery(query);

        // Initialize arrayList
        columnValues = new ArrayList<>();

        // Stores data of each row in columnValues ArrayList until the resultSet list is
        // exhausted
        while (resultSet.next())
            columnValues.add(resultSet.getString(columnName));
        return columnValues;
    }

    /**
     * Checks if the runmode of the test under execution is set to Y in Database or not
     *
     * @param tableName Name of the database table
     * @return boolean
     * @author Anuj Gupta
     */
    public static boolean isExecutable(String tableName) throws Exception {

        // Variable to store string cell data
        firstCellValue = "";

        // Query to be executed
        query = "select * from  testsuite where testname = '" + tableName + "'";

        // Stores value of the runmode column
        columnValues = getQuery(query, "runmode");

        // Stores value of first row in resultSet
        firstCellValue = columnValues.get(0);

        // Returns true if value of runmode is Y, returns false otherwise
        return firstCellValue.equalsIgnoreCase("Y");
    }

    /**
     * Calculates number of columns in a database table
     *
     * @param tableName Name of the database table
     * @return Number of columns in a database table
     * @author Anuj Gupta
     */
    public static Integer getColumnCount(String tableName) throws Exception {

        // Stores list of all the connected databases
        List<String> databaseName = getQuery("SELECT DATABASE()", "Database()");

        // Query to retrieve count of columns in table 'tableName' and database
        // 'DatabaseName'
        query = "select count(*) from information_schema.columns where table_name = '" + tableName + "' and table_schema = '"
                + databaseName.get(0) + "'";

        // Stores resultSet of the executed query
        columnValues = getQuery(query, "count(*)");

        // Stores value of first row in resultSet
        firstCellValue = columnValues.get(0);

        // Parse data in interger form and then returns it
        return Integer.parseInt(firstCellValue);
    }

    /**
     * Stores table data in a Hashmap and converts into a 2D array
     *
     * @param tableName Name of the database table
     * @return 2D Array containing database column names and its value in its respective dimensions
     * @author Anuj Gupta
     */
    public static Object[][] tableToObject(String tableName) throws Exception {

        HashMap<String, String> data;

        // Fetch total number of rows in database table and stores in a variable
        int rows = getRowCount(tableName);

        // Fetch total number of columns in the database table and stores in a variable
        int cols = getColumnCount(tableName);

        // 2D object array with first dimension's size being total number of rows in
        // database table and second dimension is constant 1
        Object[][] myData = new Object[rows][1];

        // Stores data of entire table
        ResultSet resultSet = statement.executeQuery("select * from " + tableName);

        // Stores metadata of the result set of "rs"
        ResultSetMetaData md = resultSet.getMetaData();

        for (int i = 0; i < rows; i++) {
            resultSet.next();

            data = new HashMap<>();

            for (int j = 0; j < cols; j++) {
                // Stores column name and its corresponding value in a hash table
                data.put(md.getColumnName(j + 1), resultSet.getString(j + 1));
                myData[i][0] = data;
            }
        }
        return myData;
    }

    /**
     * Sets the cell data as given cell value
     *
     * @param tableName         Contains name of the table in which the value needs to be updated
     * @param columnToBeUpdated Contains name of the column for which the data needs to be updated
     * @param valueToBeSet      Contains value that needs to be set
     * @param columnValue       Contains column value that will identify the row for which the value needs to be updated
     * @author Anuj Gupta
     */
    public static void updateCellData(String tableName, String columnName, String columnValue, String columnToBeUpdated, String valueToBeSet) throws Exception {

        // Creates statement
        statement = connection.createStatement();

        // Query to update the data
        query = "update " + tableName + " set `" + columnToBeUpdated + "` = '" + valueToBeSet + "' where `" + columnName + "` = '" + columnValue
                + "'";

        // Executes the above query
        statement.executeUpdate(query);

        //Log.info(columnToBeUpdated + " updated to " + valueToBeSet);
    }

    /**
     * Gets cell data
     *
     * @param tableName        Contains name of table
     * @param columnName       Name of the column identifier
     * @param columnValue      Contains column value to identify row in a table
     * @param resultColumnName Name of the column of which value needs to be fetched
     * @return Value of the cell
     * @author Anuj Gupta
     */
    public static String getCellData(String tableName, String resultColumnName, String columnName, String columnValue) throws Exception {

        // Creates statement
        statement = connection.createStatement();
        String value;
        // Query to select the data
        query = "select * from " + tableName + " where `" + columnName + "` = '" + columnValue + "'";

        // Executes query, and stores the first value from the result-list
        value = Objects.requireNonNull(getQuery(query, resultColumnName)).get(0);

        // Returns the value
        return value;
    }

    /**
     * Gets data of the row in which the column contains value as provided in parameter
     *
     * @param tableName   Contains name of the table
     * @param columnName  Name of the column is which columnValue is present
     * @param columnValue Contains value of column to get desired row
     * @return Hashmap which contains row data(key = column name, value = column value)
     * @author Anuj Gupta
     */
    public static HashMap<String, String> getRowData(String tableName, String columnName, String columnValue) throws Exception {

        HashMap<String, String> results = null;

        // Fetch total number of columns in the database table and stores in a variable
        int cols = getColumnCount(tableName);

        // Creates a Statement object for sending SQL statements to the database
        statement = connection.createStatement();

        // If column name exists then get data of the row in which the column contains
        // given column value
        if (!columnName.equals("")) {

            // Creates a Statement object for sending SQL statements to the database
            query = "SELECT * from " + tableName + " where `" + columnName + "` = '" + columnValue + "'";
            resultSet = statement.executeQuery(query);

            // Stores metadata of the result set
            resultSetMetaData = resultSet.getMetaData();

            // Variable to store list row values
            results = new HashMap<>();

            resultSet.next();

            // Loops over number of columns in database table
            for (int j = 0; j < cols; j++) {

                // While result set has data, add each value to 'values' list
                results.put(resultSetMetaData.getColumnName(j + 1), resultSet.getString(j + 1));
            }
        } else {
            LogManager.error("There is no column with value " + columnValue + " in database");
        }
        return results;
    }

    /**
     * Reads a database table and stores data in each row in a separate HashMap with column name as Key and cell data as its value. It returns only
     * those rows where a particular column has a specific value
     *
     * @param tableName   Name of the database table
     * @param columnName  Name of the column in database table
     * @param columnValue Value of the column
     * @return Only those rows of database as a list of Map where a particular column has a specific value
     * @author Anuj Gupta
     */
    public static List<Map<String, String>> getMultipleRowData(String tableName, String columnName, String columnValue) throws Exception {

        HashMap<String, String> results;
        List<Map<String, String>> resultList = null;

        // Fetch total number of columns in the database table and stores in a variable
        int cols = getColumnCount(tableName);

        int rows = getPartialRowCount(tableName, columnName, columnValue);

        // Creates a Statement object for sending SQL statements to the database
        statement = connection.createStatement();

        // If column name exists then get data of the row in which the column contains
        // given column value
        if (!columnName.equals("")) {

            // Creates a Statement object for sending SQL statements to the database
            query = "SELECT * from " + tableName + " where `" + columnName + "` = '" + columnValue + "'";
            resultSet = statement.executeQuery(query);

            // Stores metadata of the result set
            resultSetMetaData = resultSet.getMetaData();

            // Variable to store list row values
            resultList = new ArrayList<>();

            // Loop over number of rows
            for (int i = 0; i < rows; i++) {

                results = new HashMap<>();
                resultSet.next();
                // Loops over number of columns in database table
                for (int j = 0; j < cols; j++) {

                    // While result set has data, add each value to 'values' list
                    results.put(resultSetMetaData.getColumnName(j + 1), resultSet.getString(j + 1));
                }
                resultList.add(results);
            }
        } else {
            LogManager.error("There is no column with value " + columnValue + " in database");
        }
        return resultList;
    }

    /**
     * Reads a database table and stores data in each row in a separate HashMap with column name as Key and cell data as its value
     *
     * @param tableName Name of the database Table
     * @return All the rows of database as a list of Map
     */
    public static List<Map<String, String>> getTableData(String tableName) throws Exception {

        HashMap<String, String> results;
        List<Map<String, String>> resultList;

        // Fetch total number of columns in the database table and stores in a variable
        int cols = getColumnCount(tableName);

        // Stores total number of rows in the database table
        int rows = getRowCount(tableName);

        // Creates a Statement object for sending SQL statements to the database
        statement = connection.createStatement();

        // Creates a Statement object for sending SQL statements to the database
        query = "SELECT * from " + tableName;
        resultSet = statement.executeQuery(query);

        // Stores metadata of the result set
        resultSetMetaData = resultSet.getMetaData();

        // Variable to store list row values
        resultList = new ArrayList<>();

        // Loop over number of rows
        for (int i = 0; i < rows; i++) {

            results = new HashMap<>();
            resultSet.next();
            // Loops over number of columns in database table
            for (int j = 0; j < cols; j++) {

                // While result set has data, add each value to 'values' list
                results.put(resultSetMetaData.getColumnName(j + 1), resultSet.getString(j + 1));
            }
            resultList.add(results);
        }

        return resultList;
    }
}