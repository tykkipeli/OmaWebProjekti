package tikape.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            Connection conn = DriverManager.getConnection(dbUrl);
            PreparedStatement stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Kysymys ("
                    + "id SERIAL PRIMARY KEY,"
                    + "kurssi VARCHAR(255),"
                    + "aihe VARCHAR(255),"
                    + "kysymysteksti VARCHAR(510)"
                    + ")"); 
            stmt.executeUpdate();
            stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Vastaus ("
                    + "id SERIAL PRIMARY KEY,"
                    + "kysymysId INTEGER,"
                    + "vastausteksti VARCHAR(510),"
                    + "oikein BOOLEAN,"
                    + "FOREIGN KEY (kysymysId) REFERENCES Kysymys(id)"
                    + ")"); 
            stmt.executeUpdate();
            stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Kurssi ("
                    + "id SERIAL PRIMARY KEY,"
                    + "nimi VARCHAR(255)"
                    + ")"); 
            stmt.executeUpdate();
            stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Aihe ("
                    + "id SERIAL PRIMARY KEY,"
                    + "nimi VARCHAR(255),"
                    + "kurssi VARCHAR(255)"
                    + ")"); 
            stmt.executeUpdate();
            stmt.close();
            return conn;
        }
        return DriverManager.getConnection(databaseAddress);
    }
}