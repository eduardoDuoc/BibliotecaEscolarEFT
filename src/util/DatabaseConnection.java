package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    private final String URL =
            "jdbc:mysql://localhost:3306/biblioteca";

    private final String USER = "root";
    private final String PASSWORD = "casa12345";

    private DatabaseConnection() {

        try {

            connection = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("Conexión exitosa a la base de datos.");

        } catch (SQLException e) {

            System.out.println(
                    "Error de conexión: " + e.getMessage()
            );
        }
    }

    public static synchronized DatabaseConnection getInstance() {

        if (instance == null) {
            instance = new DatabaseConnection();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }


    public Connection crearConexionTransaccional()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }

}