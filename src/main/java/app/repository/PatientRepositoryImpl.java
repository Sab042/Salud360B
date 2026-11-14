package app.repository;

import app.domain.Patient;
import app.service.outputports.PatientRepositoryPort;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Adaptador de salida en memoria. Más adelante se reemplaza por uno JDBC/MySQL sin tocar el servicio. */
public class PatientRepositoryImpl implements PatientRepositoryPort {

    private final Map<Long, Patient> store = new LinkedHashMap<>();

    @Override
    public Patient createPatient(Patient patient) {
        store.put(patient.getId(), patient);
        return patient;
    }

    @Override
    public Optional<Patient> selectPatientById(long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Patient> selectPatientByEmail(String email) {
        return store.values().stream().filter(p -> p.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    @Override
    public Optional<Patient> selectPatientByUsername(String username) {
        return store.values().stream().filter(p -> p.getUsername().equalsIgnoreCase(username)).findFirst();
    }

    @Override
    public List<Patient> selectAllPatients() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Patient updatePatient(Patient patient) {
        store.put(patient.getId(), patient);
        return patient;
    }

    @Override
    public boolean deletePatient(long id) {
        return store.remove(id) != null;
    }
}
