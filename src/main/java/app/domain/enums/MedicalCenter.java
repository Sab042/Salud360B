package app.domain;

public class MedicalCenter {

    private Integer centerId;
    private String centerType;
    private String address;
    private String city;
    private Doctor doctor;
    private boolean isAvailable;

    public MedicalCenter() {
    }

    public Integer getCenterId() {
        return centerId;
    }

    public void setCenterId(Integer centerId) {
        this.centerId = centerId;
    }

    public String getCenterType() {
        return centerType;
    }

    public void setCenterType(String centerType) {
        this.centerType = centerType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
