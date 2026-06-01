package database;

import java.sql.*;

public class DBConnection {
    private static final String SERVER = "localhost";
    private static final String INSTANCE = "SQLEXPRESS";
    private static final int PORT = 1433;
    private static final String DATABASE = "OnlineExamSystem";
    private static final String USER = "sa";
    private static final String PASSWORD = "Admin123";

    /**
     * SQL Server Express (SQLEXPRESS) — TCP port 1433.
     * Pehle scripts/enable-sql-server.ps1 ko "Run as Administrator" se chalain.
     */
    private static final String URL = String.format(
            "jdbc:sqlserver://%s:%d;databaseName=%s;user=%s;password=%s;encrypt=false;trustServerCertificate=true;loginTimeout=15",
            SERVER, PORT, DATABASE, USER, PASSWORD);

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(URL);
                System.out.println("Database connected: " + DATABASE);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("SQL Server JDBC driver not found. Add mssql-jdbc-*.jar to lib/ and run with: java -cp \"bin;lib/*\" main.Main");
            e.printStackTrace();
            connection = null;
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            System.err.println();
            System.err.println("Fix (PowerShell as Administrator):");
            System.err.println("  cd \"" + System.getProperty("user.dir") + "\"");
            System.err.println("  .\\scripts\\enable-sql-server.ps1");
            System.err.println();
            System.err.println("Also check: SQL Server (SQLEXPRESS) service running, database '" + DATABASE + "' exists (run database.sql in SSMS).");
            e.printStackTrace();
            connection = null;
        }
        return connection;
    }

    public static boolean testConnection() {
        Connection c = getConnection();
        if (c == null) {
            return false;
        }
        try (Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT 1")) {
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Connection test failed: " + e.getMessage());
            return false;
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
