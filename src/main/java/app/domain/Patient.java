package app.domain;

import java.util.Date;

public class Patient extends Person {

    private String patientType;

    public Patient() {
        super();
    }

    public Patient(int id, String docType, String name, String lastName, String phone, String email, String address, String password, Date birthDate, String patientType) {
        super(id, docType, name, lastName, phone, email, address, password, birthDate);
        this.patientType = patientType;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void selectById(int id) {
        super.selectById(id);
    }

    @Override
    public void selectAll() {
        super.selectAll();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void delete(int id) {
        super.delete(id);
    }
}