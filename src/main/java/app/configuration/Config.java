package app.configuration;

import app.repository.PatientRepositoryImpl;
import app.service.PatientServiceImpl;
import app.service.inputport.PatientService;
import app.service.outputport.PatientRepository;
import app.userinterface.UserInterface;
import app.view.PatientView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Config {

    // Configuracion de la base de datos
    private static final String URL =
            "jdbc:mysql://localhost:3306/salud360";

    private static final String USER = "root";

    private static final String PASSWORD = "";

    // Metodo para establecer la conexion con MySQL
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Configuracion de la interfaz de usuario
    public static UserInterface createUserInterface() {

        PatientRepository patientRepository =
                new PatientRepositoryImpl();

        PatientService patientService =
                new PatientServiceImpl(patientRepository);

        PatientView patientView =
                new PatientView(patientService);

        return new UserInterface(patientView);
    }
}