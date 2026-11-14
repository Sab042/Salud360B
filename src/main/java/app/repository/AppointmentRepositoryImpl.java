package app.repository;

import app.domain.Appointment;
import app.service.outputports.AppointmentRepositoryPort;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AppointmentRepositoryImpl implements AppointmentRepositoryPort {

    private final Map<Integer, Appointment> store = new LinkedHashMap<>();
    private int nextId = 1;

    @Override
    public Appointment createAppointment(Appointment appointment) {
        appointment.setAppointmentId(nextId++);
        store.put(appointment.getAppointmentId(), appointment);
        return appointment;
    }

    @Override
    public Optional<Appointment> selectAppointmentById(int appointmentId) {
        return Optional.ofNullable(store.get(appointmentId));
    }

    @Override
    public List<Appointment> selectAllAppointments() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Appointment> selectAppointmentsByPatientId(long patientId) {
        return store.values().stream().filter(a -> a.getPatient().getId() == patientId).toList();
    }

    @Override
    public List<Appointment> selectAppointmentsByProfessionalId(long professionalId) {
        return store.values().stream().filter(a -> a.getProfessional().getId() == professionalId).toList();
    }

    @Override
    public List<Appointment> selectAppointmentsByMedicalCenterId(int centerId) {
        return store.values().stream().filter(a -> a.getMedicalCenter().getCenterId() == centerId).toList();
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        store.put(appointment.getAppointmentId(), appointment);
        return appointment;
    }

    @Override
    public boolean deleteAppointment(int appointmentId) {
        return store.remove(appointmentId) != null;
    }
}
