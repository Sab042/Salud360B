package app.service.outputports;

import app.domain.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepositoryPort {

    Appointment createAppointment(Appointment appointment);

    Optional<Appointment> selectAppointmentById(int appointmentId);

    List<Appointment> selectAllAppointments();

    List<Appointment> selectAppointmentsByPatientId(long patientId);

    List<Appointment> selectAppointmentsByProfessionalId(long professionalId);

    List<Appointment> selectAppointmentsByMedicalCenterId(int centerId);

    Appointment updateAppointment(Appointment appointment);

    boolean deleteAppointment(int appointmentId);
}
