package pro.diamondworld.insanemobs.databasemanager;

import java.sql.*;
import java.text.SimpleDateFormat;

public class DatabaseManager {
    private Connection connection;

    public boolean open() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:C:\\sqlite\\bosses.db"
            );

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void insertBossData(int id, String playerName, double damage) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String time = sdf.format(timestamp);

        String query = String.format(
                "INSERT INTO bosses(id, date, player, damage) " +
                "VALUES ('%s', '%s', '%s', '%s')",
                String.valueOf(id), time, playerName, String.valueOf(damage));

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int getLastBossId() {
        String query = "SELECT * FROM bosses " +
                "ORDER BY id DESC " +
                "LIMIT 1";

        try {
            Statement statement = connection.createStatement();
            ResultSet rs =  statement.executeQuery(query);
            rs.next();


            return rs.getInt("id");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public void close() {
        try {
            connection.close();

            System.out.println("[Database] disconnected.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
