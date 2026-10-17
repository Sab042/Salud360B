package app.service.inputport;

import app.domain.Appointment;
import app.domain.MedicalCenter;
import java.util.List;

public interface AppointmentService {

    public Appointment createAppointment(Integer appointmentId, String dateInit, String dateEnd, MedicalCenter medicalCenter, String description, double cost, String appointmentState);

    public Appointment selectAppointmentById(int id);

    public List<Appointment> selectAllAppointments();

    public Appointment updateAppointment(Integer appointmentId, String dateInit, String dateEnd, MedicalCenter medicalCenter, String description, double cost, String appointmentState);

    public void deleteAppointmentById(int id);
}
