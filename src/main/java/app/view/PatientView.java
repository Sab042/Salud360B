package app.view;

import app.domain.Patient;
import app.service.helpers.SetPatientType;
import app.service.helpers.SetPersonDocType;
import app.service.inputport.PatientService;
import app.service.validations.FormTypeValidator;
import java.util.Date;
import java.util.List;

public class PatientView {

    private final PatientService patientService;

    public PatientView(PatientService patientService) {
        this.patientService = patientService;
    }

    public void createPatient() {
        int id = FormTypeValidator.intValidator("Ingrese el id del paciente");
        System.out.println("Ingrese el tipo de documento del paciente");
        String docType = SetPersonDocType.getDocType();
        String name = FormTypeValidator.stringValidator("Ingrese el nombre del paciente");
        String lastName = FormTypeValidator.stringValidator("Ingrese el apellido del paciente");
        String phone = FormTypeValidator.stringValidator("Ingrese el telefono del paciente");
        String email = FormTypeValidator.stringValidator("Ingrese el email del paciente");
        String address = FormTypeValidator.stringValidator("Ingrese la direccion del paciente");
        String password = FormTypeValidator.stringValidator("Ingrese la contraseña del paciente");

        Date birthDate = getBirthDateFromInput();

        System.out.println("Ingrese el tipo de afiliacion del paciente: 1. Contributivo, 2. Subsidiado, 3. Particular");
        String patientType = SetPatientType.getPatientType();

        patientService.createPatient(id, docType, name, lastName, phone, email, address, password, birthDate, patientType);
    }

    public void showAllPatients() {
        List<Patient> patients = patientService.selectAllPatients();
        for (Patient patient : patients) {
            System.out.println(patient.getId() + " " + patient.getName() + " " + patient.getLastName() + " - " + patient.getPatientType());
        }
    }

    public Date getBirthDateFromInput() {
        String birthDateString = FormTypeValidator.stringValidator("Ingrese la fecha de nacimiento del paciente (formato: yyyy-MM-dd): ");
        Date birthDate = null;
        try {
            birthDate = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(birthDateString);
        } catch (java.text.ParseException e) {
            System.out.println("Formato de fecha inválido. Por favor, ingrese la fecha en el formato correcto.");
        }
        return birthDate;
    }
}