
package app.persistence.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static DataBaseConnection instance;
    private final Connection connection;

    private static final String URL =
            "jdbc:mysql://localhost:3306/salud360";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    private DataBaseConnection() {

        try {
            connection = DriverManager.getConnection(
                    URL, USER, PASSWORD);

            System.out.println(
                    "Conexion exitosa a Salud360");

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al conectar con MySQL: "
                            + e.getMessage(), e);
        }
    }

    public static synchronized DataBaseConnection getInstance() {

        if (instance == null) {
            instance = new DataBaseConnection();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
