package app.domain;

import app.enums.AppointmentState;

import java.time.Duration;
import java.time.LocalDateTime;

public class Appointment {

    private Integer appointmentId;
    private Patient patient;
    private Professional professional;
    private MedicalCenter medicalCenter;
    private LocalDateTime dateInit;
    private LocalDateTime dateEnd;
    private String descriptionAppointment;
    private AppointmentState appointmentState;

    public Appointment() {
    }

    public Appointment(Integer appointmentId, Patient patient, Professional professional,
                       MedicalCenter medicalCenter, LocalDateTime dateInit, LocalDateTime dateEnd,
                       String descriptionAppointment, AppointmentState appointmentState) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.professional = professional;
        this.medicalCenter = medicalCenter;
        this.dateInit = dateInit;
        this.dateEnd = dateEnd;
        this.descriptionAppointment = descriptionAppointment;
        this.appointmentState = appointmentState;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public MedicalCenter getMedicalCenter() {
        return medicalCenter;
    }

    public void setMedicalCenter(MedicalCenter medicalCenter) {
        this.medicalCenter = medicalCenter;
    }

    public LocalDateTime getDateInit() {
        return dateInit;
    }

    public void setDateInit(LocalDateTime dateInit) {
        this.dateInit = dateInit;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDescriptionAppointment() {
        return descriptionAppointment;
    }

    public void setDescriptionAppointment(String descriptionAppointment) {
        this.descriptionAppointment = descriptionAppointment;
    }

    public AppointmentState getAppointmentState() {
        return appointmentState;
    }

    public void setAppointmentState(AppointmentState appointmentState) {
        this.appointmentState = appointmentState;
    }

    // Methods

    public long getDurationMinutes() {
        return Duration.between(dateInit, dateEnd).toMinutes();
    }

    /** Dos rangos se cruzan si cada uno empieza antes de que el otro termine. */
    public boolean overlapsWith(LocalDateTime init, LocalDateTime end) {
        return dateInit.isBefore(end) && dateEnd.isAfter(init);
    }
}
