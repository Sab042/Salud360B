package app.domain;

import app.enums.CenterType;

import java.util.ArrayList;
import java.util.List;

public class MedicalCenter {

    private Integer centerId;
    private String name;
    private CenterType centerType;
    private String address;
    private String city;
    private List<Professional> professionals;
    private boolean isAvailable;

    public MedicalCenter() {
        this.professionals = new ArrayList<>();
    }

    public MedicalCenter(Integer centerId, String name, CenterType centerType, String address, String city,
                         List<Professional> professionals, boolean isAvailable) {
        this.centerId = centerId;
        this.name = name;
        this.centerType = centerType;
        this.address = address;
        this.city = city;
        this.professionals = professionals == null ? new ArrayList<>() : new ArrayList<>(professionals);
        this.isAvailable = isAvailable;
    }

    public Integer getCenterId() {
        return centerId;
    }

    public void setCenterId(Integer centerId) {
        this.centerId = centerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CenterType getCenterType() {
        return centerType;
    }

    public void setCenterType(CenterType centerType) {
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

    public List<Professional> getProfessionals() {
        return professionals;
    }

    public void setProfessionals(List<Professional> professionals) {
        this.professionals = professionals;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    // Methods

    public void addProfessional(Professional professional) {
        professionals.add(professional);
    }

    public void removeProfessional(long professionalId) {
        professionals.removeIf(p -> p.getId() == professionalId);
    }

    public boolean hasProfessional(long professionalId) {
        return professionals.stream().anyMatch(p -> p.getId() == professionalId);
    }
}
