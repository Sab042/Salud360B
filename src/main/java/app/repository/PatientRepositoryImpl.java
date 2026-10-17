package app.repository;

import app.domain.Patient;
import app.service.outputport.PatientRepository;
import java.util.ArrayList;
import java.util.List;

public class PatientRepositoryImpl implements PatientRepository {

    List<Patient> patients = new ArrayList<>();

    @Override
    public Patient savePatient(Patient patient) {
        patients.add(patient);
        return patient;
    }

    @Override
    public List<Patient> selectAllPatients() {
        return patients;
    }

    @Override
    public Patient selectPatientById(int id) {
        return null;
    }

    @Override
    public Patient updatePatient(Patient patient) {
        return null;
    }

    @Override
    public void deletePatient(int id) {

    }
}
