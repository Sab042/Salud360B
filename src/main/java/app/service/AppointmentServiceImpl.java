package app.service;

import app.domain.Appointment;
import app.domain.MedicalCenter;
import app.service.inputport.AppointmentService;
import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {

    @Override
    public Appointment createAppointment(Integer appointmentId, String dateInit, String dateEnd, MedicalCenter medicalCenter, String description, double cost, String appointmentState) {
        return null;
    }

    @Override
    public Appointment selectAppointmentById(int id) {
        return null;
    }

    @Override
    public List<Appointment> selectAllAppointments() {
        return List.of();
    }

    @Override
    public Appointment updateAppointment(Integer appointmentId, String dateInit, String dateEnd, MedicalCenter medicalCenter, String description, double cost, String appointmentState) {
        return null;
    }

    @Override
    public void deleteAppointmentById(int id) {

    }
}
