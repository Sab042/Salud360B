package app.service;

import app.domain.Patient;
import app.service.inputport.PatientService;
import app.service.outputport.PatientRepository;
import java.util.Date;
import java.util.List;

public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient createPatient(int id, String docType, String name, String lastName, String phone, String email, String address, String password, Date birthDate, String patientType) {
        Patient patient = new Patient(id, docType, name, lastName, phone, email, address, password, birthDate, patientType);
        return patientRepository.savePatient(patient);
    }

    @Override
    public Patient selectPatientById(int id) {
        return null;
    }

    @Override
    public List<Patient> selectAllPatients() {
        return patientRepository.selectAllPatients();
    }

    @Override
    public Patient updatePatient(int id, String docType, String name, String lastName, String phone, String email, String address, String password, Date birthDate, String patientType) {
        return null;
    }

    @Override
    public void deletePatient(int id) {

    }
}