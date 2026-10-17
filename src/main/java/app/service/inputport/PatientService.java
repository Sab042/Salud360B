package app.service.inputport;

import app.domain.Patient;
import java.util.Date;
import java.util.List;

public interface PatientService {

    public Patient createPatient(int id, String docType, String name, String lastName, String phone, String email, String address, String password, Date birthDate, String patientType);

    public Patient selectPatientById(int id);

    public List<Patient> selectAllPatients();

    public Patient updatePatient(int id, String docType, String name, String lastName, String phone, String email, String address, String password, Date birthDate, String patientType);

    public void deletePatient(int id);
}
