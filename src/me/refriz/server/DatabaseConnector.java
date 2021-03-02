package me.refriz.server;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import me.refriz.Inferris;

import java.sql.*;

public class DatabaseConnector {

    private static final MysqlDataSource source = new MysqlDataSource();

    static {
        source.setUrl("jdbc:mysql://localhost/inferris?useSSL=false&allowPublicKeyRetrieval=true");
        source.setUser(Inferris.getFileConfig().getString("database_user"));
        source.setPassword(Inferris.getFileConfig().getString("database_password"));
    }

    public static Connection getConnection() throws SQLException {
        return source.getConnection();
    }

}