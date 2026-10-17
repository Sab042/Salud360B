package app.service.outputport;

import app.domain.Patient;
import java.util.List;

public interface PatientRepository {

    public Patient savePatient(Patient patient);

    public List<Patient> selectAllPatients();

    public Patient selectPatientById(int id);

    public Patient updatePatient(Patient patient);

    public void deletePatient(int id);
}