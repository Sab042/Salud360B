package app.userinterface;

import app.service.validations.FormTypeValidator;
import app.view.PatientView;

public class UserInterface {

    private final PatientView patientView;

    public UserInterface(PatientView patientView) {
        this.patientView = patientView;
    }

    public void menuApp() {
        int init = FormTypeValidator.intValidator("Presione 1 para iniciar la aplicacion SALUD360 (0 para salir)");

        while (init != 0) {
            int option = FormTypeValidator.intValidator("""
                    Seleccione:
                    1. Registrar Paciente
                    2. Consultar todos los pacientes
                    0. Salir
                    """);
            switch (option) {
                case 1:
                    System.out.println("Registrar Paciente");
                    patientView.createPatient();
                    break;
                case 2:
                    System.out.println("Listar todos los pacientes");
                    patientView.showAllPatients();
                    break;
                case 0:
                    init = 0;
                    System.out.println("Saliendo de SALUD360...");
                    break;
                default:
                    System.out.println("Seleccione una opcion valida");
                    break;
            }
        }
    }
}