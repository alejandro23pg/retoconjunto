package aplicacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
    private static Connection connection;

    static {
        String url = "jdbc:mysql://localhost:3307/GestorPeliculas";
        String user = "root";
        String password = System.getenv("MYSQL_ROOT_PASSWORD");
        try {
            connection = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() { return connection; }
}
