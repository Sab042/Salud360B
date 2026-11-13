
package app.configuration;

import app.domain.Patient;
import app.persistence.dbconnection.DataBaseConnection;
import app.persistence.mapper.PatientMapper;
import app.persistence.mapper.RowMapper;
import app.persistence.repository.PatientRepositoryImpl;
import app.service.PatientServiceImpl;
import app.service.inputport.PatientService;
import app.service.outputport.PatientRepository;
import app.userinterface.UserInterface;
import app.view.PatientView;

import java.sql.Connection;

public class Config {

    public static UserInterface createUserInterface() {

        // Obtener la conexión a la base de datos
        Connection connection =
                DataBaseConnection.getInstance().getConnection();

        // Configurar el Mapper
        RowMapper<Patient> patientMapper = new PatientMapper();

        // Crear el repositorio mediante inyección de dependencias
        PatientRepository patientRepository =
                new PatientRepositoryImpl(connection, patientMapper);

        // Crear el servicio
        PatientService patientService =
                new PatientServiceImpl(patientRepository);

        // Crear la vista
        PatientView patientView =
                new PatientView(patientService);

        // Crear la interfaz de usuario
        return new UserInterface(patientView);
    }
}
