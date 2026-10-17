package app.domain;

public class Appointment {

    private Integer appointmentId;
    private String dateInit;
    private String dateEnd;
    private MedicalCenter medicalCenter;
    private String description;
    private double cost;
    private String appointmentState;

    public Appointment() {
    }

    public Appointment(Integer appointmentId, String dateInit, String dateEnd, MedicalCenter medicalCenter, String description, double cost, String appointmentState) {
        this.appointmentId = appointmentId;
        this.dateInit = dateInit;
        this.dateEnd = dateEnd;
        this.medicalCenter = medicalCenter;
        this.description = description;
        this.cost = cost;
        this.appointmentState = appointmentState;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDateInit() {
        return dateInit;
    }

    public void setDateInit(String dateInit) {
        this.dateInit = dateInit;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public MedicalCenter getMedicalCenter() {
        return medicalCenter;
    }

    public void setMedicalCenter(MedicalCenter medicalCenter) {
        this.medicalCenter = medicalCenter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getAppointmentState() {
        return appointmentState;
    }

    public void setAppointmentState(String appointmentState) {
        this.appointmentState = appointmentState;
    }
}
