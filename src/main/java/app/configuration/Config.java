package app.configuration;

import app.repository.PatientRepositoryImpl;
import app.service.PatientServiceImpl;
import app.service.inputport.PatientService;
import app.service.outputport.PatientRepository;
import app.userinterface.UserInterface;
import app.view.PatientView;

public class Config {

    public static UserInterface createUserInterface() {
        PatientRepository patientRepository = new PatientRepositoryImpl();
        PatientService patientService = new PatientServiceImpl(patientRepository);
        PatientView patientView = new PatientView(patientService);

        return new UserInterface(patientView);
    }
}