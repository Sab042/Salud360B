package app.service.inputports;

import app.domain.Patient;
import app.enums.DocType;
import app.enums.PatientType;

import java.time.LocalDate;
import java.util.List;

public interface PatientServiceInterface {

    Patient createPatient(long id, DocType docType, String name, String lastName, String phone, String email,
                          String address, String username, String password, LocalDate birthDate,
                          PatientType typePatient);

    Patient selectPatientById(long id);

    List<Patient> selectAllPatients();

    /** Si password llega vacío se conserva la contraseña actual. */
    Patient updatePatient(long id, DocType docType, String name, String lastName, String phone, String email,
                          String address, String username, String password, LocalDate birthDate,
                          PatientType typePatient);

    void deletePatient(long id);
}
