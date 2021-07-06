package me.aziah.server;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SQLHandler {

    public static void action(String syntax) {
        try {
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(syntax);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(String table, String set, String value, String column, String columnValue) {
        try {
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `" + table + "` SET `" + set + "` = '" + value + "' WHERE `" + column + "` = '" + columnValue + "'");
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
