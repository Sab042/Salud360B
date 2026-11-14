package app.view;

import app.domain.Patient;
import app.helpers.ConsoleHelper;
import app.helpers.DateHelper;
import app.enums.PatientType;
import app.service.inputports.PatientServiceInterface;

import java.util.List;

public class PatientView {

    // La vista depende de la interfaz del servicio, no de su implementación
    private final PatientServiceInterface patientService;
    private final ConsoleHelper console;
    private final PersonForm personForm;

    public PatientView(PatientServiceInterface patientService, ConsoleHelper console) {
        this.patientService = patientService;
        this.console = console;
        this.personForm = new PersonForm(console);
    }

    public void showMenu() {
        int option;
        do {
            option = console.menu("MENÚ PACIENTES", "Volver al menú principal",
                    "Registrar paciente",
                    "Consultar paciente por documento",
                    "Listar pacientes",
                    "Actualizar paciente",
                    "Eliminar paciente");
            switch (option) {
                case 1 -> console.execute(this::createPatient);
                case 2 -> console.execute(this::selectPatientById);
                case 3 -> console.execute(this::selectAllPatients);
                case 4 -> console.execute(this::updatePatient);
                case 5 -> console.execute(this::deletePatient);
                default -> {
                }
            }
        } while (option != 0);
    }

    public void createPatient() {
        console.title("Registrar paciente");
        PersonForm.Data d = personForm.capture(null);
        PatientType type = console.readEnum("Tipo de paciente", PatientType.class);
        Patient patient = patientService.createPatient(d.id(), d.docType(), d.name(), d.lastName(), d.phone(),
                d.email(), d.address(), d.username(), d.password(), d.birthDate(), type);
        console.success("Paciente registrado.");
        show(patient);
    }

    public void selectPatientById() {
        console.title("Consultar paciente");
        show(patientService.selectPatientById(console.readLong("Número de documento")));
    }

    public void selectAllPatients() {
        console.title("Listado de pacientes");
        List<Patient> patients = patientService.selectAllPatients();
        if (patients.isEmpty()) {
            console.info("No hay pacientes registrados.");
            return;
        }
        patients.forEach(p -> console.info(String.format("%-13d %-28s %-22s %s",
                p.getId(), p.getFullName(), p.getTypePatient(), p.getEmail())));
    }

    public void updatePatient() {
        console.title("Actualizar paciente");
        long id = console.readLong("Número de documento del paciente a actualizar");
        Patient current = patientService.selectPatientById(id);
        show(current);
        console.info("Ingrese los nuevos datos:");
        PersonForm.Data d = personForm.capture(id);
        PatientType type = console.readEnum("Tipo de paciente", PatientType.class);
        Patient patient = patientService.updatePatient(id, d.docType(), d.name(), d.lastName(), d.phone(),
                d.email(), d.address(), d.username(), d.password(), d.birthDate(), type);
        console.success("Paciente actualizado.");
        show(patient);
    }

    public void deletePatient() {
        console.title("Eliminar paciente");
        long id = console.readLong("Número de documento del paciente a eliminar");
        show(patientService.selectPatientById(id));
        if (console.readYesNo("¿Confirma la eliminación?")) {
            patientService.deletePatient(id);
            console.success("Paciente eliminado.");
        } else {
            console.info("Operación cancelada.");
        }
    }

    private void show(Patient p) {
        console.info("Documento : " + p.getDocType() + " " + p.getId());
        console.info("Nombre    : " + p.getFullName() + " (" + p.getAge() + " años)");
        console.info("Teléfono  : " + p.getPhone());
        console.info("Correo    : " + p.getEmail());
        console.info("Dirección : " + p.getAddress());
        console.info("Usuario   : " + p.getUsername());
        console.info("Nacimiento: " + DateHelper.format(p.getBirthDate()));
        console.info("Tipo      : " + p.getTypePatient());
    }
}
