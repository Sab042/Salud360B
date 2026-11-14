package app.view;

import app.helpers.ConsoleHelper;

/** Menú principal: enruta a la vista de cada vertical. */
public class MainMenu {

    private final PatientView patientView;
    private final ProfessionalView professionalView;
    private final MedicalCenterView medicalCenterView;
    private final AppointmentView appointmentView;
    private final ConsoleHelper console;

    public MainMenu(PatientView patientView, ProfessionalView professionalView,
                    MedicalCenterView medicalCenterView, AppointmentView appointmentView,
                    ConsoleHelper console) {
        this.patientView = patientView;
        this.professionalView = professionalView;
        this.medicalCenterView = medicalCenterView;
        this.appointmentView = appointmentView;
        this.console = console;
    }

    public void show() {
        console.info("SALUD360 - Plataforma de Gestión Médica Integrada");
        int option;
        do {
            option = console.menu("MENÚ PRINCIPAL", "Salir",
                    "Pacientes",
                    "Profesionales",
                    "Centros médicos",
                    "Citas médicas");
            switch (option) {
                case 1 -> patientView.showMenu();
                case 2 -> professionalView.showMenu();
                case 3 -> medicalCenterView.showMenu();
                case 4 -> appointmentView.showMenu();
                default -> {
                }
            }
        } while (option != 0);
        console.info("Hasta pronto.");
    }
}
