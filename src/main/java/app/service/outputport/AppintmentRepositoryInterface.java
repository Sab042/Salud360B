package app.service.outputport;

import app.domain.Appointment;
import java.util.List;

public interface AppointmentRepositoryInterface {

    public Appointment saveAppointment(Appointment appointment);

    public Appointment selectAppointmentById(int id);

    public List<Appointment> selectAllAppointment();

    public Appointment updateAppointment(Appointment appointment);

    public void deleteAppointmentById(int id);
}
