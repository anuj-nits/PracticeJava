package database;

import java.sql.*;

public class Mysql {

    public static void main(String[] args) throws Exception {

        mysql();
    }

    private static void mysql() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/Selenium", "root", "root");
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Authors");
        while (rs.next())
            System.out.println(rs.getInt(1) + "  " + rs.getString(2));
        c.close();
    }
}
