package app.service.inputports;

import app.domain.Appointment;
import app.enums.AppointmentState;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentServiceInterface {

    Appointment createAppointment(long patientId, long professionalId, int centerId, LocalDateTime dateInit,
                                  LocalDateTime dateEnd, String descriptionAppointment);

    Appointment selectAppointmentById(int appointmentId);

    List<Appointment> selectAllAppointments();

    List<Appointment> selectAppointmentsByPatientId(long patientId);

    List<Appointment> selectAppointmentsByProfessionalId(long professionalId);

    /** Reprograma una cita activa (nuevo centro, horario y/o descripción). */
    Appointment updateAppointment(int appointmentId, int centerId, LocalDateTime dateInit,
                                  LocalDateTime dateEnd, String descriptionAppointment);

    /** Aplica una transición del ciclo de vida: confirmar, completar, cancelar o marcar inasistencia. */
    Appointment changeAppointmentState(int appointmentId, AppointmentState newState);

    void deleteAppointment(int appointmentId);
}
