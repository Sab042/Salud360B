package app.config;

import app.helpers.ConsoleHelper;
import app.repository.MedicalCenterRepositorylmpl;
import app.repository.MedicalCenterRepositoryImpl;
import app.repository.PatientRepositoryImpl;
import app.repository.ProfessionalRepositoryImpl;
import app.service.AppointmentServiceImpl;
import app.service.MedicalCenterServiceImpl;
import app.service.PatientServiceImpl;
import app.service.ProfessionalServiceImpl;
import app.service.inputports.AppointmentServiceInterface;
import app.service.inputports.MedicalCenterServiceInterface;
import app.service.inputports.PatientServiceInterface;
import app.service.inputports.ProfessionalServiceInterface;
import app.service.outputports.AppointmentRepositoryPort;
import app.service.outputports.MedicalCenterRepositoryPort;
import app.service.outputports.PatientRepositoryPort;
import app.service.outputports.ProfessionalRepositoryPort;
import app.view.AppointmentView;
import app.view.MainMenu;
import app.view.MedicalCenterView;
import app.view.PatientView;
import app.view.ProfessionalView;

/**
 * Raíz de composición: aquí se crean las implementaciones y se inyectan cada una
 * en quien la necesita, siempre a través de interfaces (inversión de dependencias):
 *
 *   View  ->  ServiceInterface (puerto de entrada)  <-  ServiceImpl
 *   ServiceImpl  ->  RepositoryPort (puerto de salida)  <-  RepositoryImpl
 *
 * Para cambiar la persistencia (p. ej. MySQL con XAMPP) basta con cambiar los
 * "new ...RepositoryImpl()" de este archivo.
 */
public class Config {

    private final ConsoleHelper console = new ConsoleHelper();

    private PatientView patientView;
    private ProfessionalView professionalView;
    private MedicalCenterView medicalCenterView;
    private AppointmentView appointmentView;
    private MainMenu mainMenu;

    public Config() {
        registerVerticals();
    }

    private void registerVerticals() {
        // 1. Puertos de salida -> adaptadores (repositorios)
        PatientRepositoryPort patientRepository = new PatientRepositoryImpl();
        ProfessionalRepositoryPort professionalRepository = new ProfessionalRepositoryImpl();
        MedicalCenterRepositoryPort medicalCenterRepository = new MedicalCenterRepositoryImpl();
        AppointmentRepositoryPort appointmentRepository = new MedicalCenterRepositorylmpl();

        // 2. Puertos de entrada -> adaptadores (servicios), inyectando los puertos de salida
        PatientServiceInterface patientService =
                new PatientServiceImpl(patientRepository, appointmentRepository);
        ProfessionalServiceInterface professionalService =
                new ProfessionalServiceImpl(professionalRepository, appointmentRepository, medicalCenterRepository);
        MedicalCenterServiceInterface medicalCenterService =
                new MedicalCenterServiceImpl(medicalCenterRepository, professionalService, appointmentRepository);
        AppointmentServiceInterface appointmentService =
                new AppointmentServiceImpl(appointmentRepository, patientService, professionalService, medicalCenterService);

        // 3. Vistas (verticales), inyectando los puertos de entrada
        patientView = new PatientView(patientService, console);
        professionalView = new ProfessionalView(professionalService, console);
        medicalCenterView = new MedicalCenterView(medicalCenterService, console);
        appointmentView = new AppointmentView(appointmentService, console);

        // 4. Menú principal con todas las verticales registradas
        mainMenu = new MainMenu(patientView, professionalView, medicalCenterView, appointmentView, console);
    }

    public PatientView getPatientView() {
        return patientView;
    }

    public ProfessionalView getProfessionalView() {
        return professionalView;
    }

    public MedicalCenterView getMedicalCenterView() {
        return medicalCenterView;
    }

    public AppointmentView getAppointmentView() {
        return appointmentView;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }
}
