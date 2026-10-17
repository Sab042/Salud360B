package app.repository;

import app.domain.Appointment;
import app.service.outputport.AppointmentRepositoryInterface;
import java.util.List;

public class AppointmentRepositoryArraysImpl implements AppointmentRepositoryInterface {

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return null;
    }

    @Override
    public Appointment selectAppointmentById(int id) {
        return null;
    }

    @Override
    public List<Appointment> selectAllAppointment() {
        return List.of();
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        return null;
    }

    @Override
    public void deleteAppointmentById(int id) {

    }
}
