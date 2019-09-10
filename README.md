# launch

A test project to understand the basics of Apache POI, CSV, Extent Reporting, Json, Log4j, MySql, Property file, Rest Assured & Selenium in Java.

## Apache POI
```java
getColumnCount()       // given the sheetname, it returns the count of column of any excel sheet
getRowCount()          // given the sheetname, it returns the count of rows of any excel sheet
getCellData()          // given the sheetname, row number & column number it returns the value of a particular excel cell
getRowAsMap()          // given the sheetname & row number it returns the complete excel row returned as a hashMap
getColumnAsMap()       // given the sheetname it returns a hashMap with first column as key and second column as its value
```

## CSV
```java
writeToCSV()           // It uses openCSV to write data into the csv file, each cell at a time
readFromCSV()          // It uses openCSV to read data from the csv file, each cell at a time
```

## Extent Report
```java
startTest()            // It makes the extent ready to capture the test results
pass()                 // Capture pass results
fail()                 // Capture failed resutls & capture screenshot for the screen
captureScreenshot()    // private method to be called from within the pass() or fail() methods
endTest()              // It flushes the test results in the html file created at the location: /src/test/resources/report/
```
### Json
```java
readFromJson()         // used jackson api to read values from json file and return a java map
writeToJson()          // used jackson api and write a java map to json file
replaceJsonVariable()  // replaces dynamic variables in json file with actual values provided as parameter
```

### Log4j
```java
setHTMLLogs()          // configures how html logs should be displayed and where the logs should be stored
setUserLogs()          // configures how text logs should be displayed and where the logs should be stored
setConsoleLogs()       // configures how the console logs should be displayed
info()                 // configures pattern of info type logs
error()                // configures pattern of error type logs
debug()                // configures pattern of debug type logs
```

### MySql
```java
getRowCount()          // get row count of the table provided
getColumnCount()       // get column count of the table provided
getPartialRowCount()   // get number of rows which satisfy a particular condition
updateCellData()       // udpates a cell value
getRowData()           // fetches row as a map
getMultipleRowData()   // fetches multiple rows as a map of maps which satisfy a particular condition
getTableData()         // fetches a table as a map of map
tableToObject()        // converts the table into a 2d array object
```
### Property file
```java
getProperty()          // loads property file and return required value
```
### Rest Assured
``` java
callApi()             // It can handle post, get, patch, put & delete requests. It requires the method's argument to be same as the json file name. If there are variables in json file, create an excel sheet with sheet name same as the api name and provide all json values into it. The payload, status code & request type is to be provided from the excel sheet
```
