package app.domain;

import java.util.Date;

public class Doctor extends Person {

    private String specialty;
    private String professionalCard;

    public Doctor() {
        super();
    }

    public Doctor(int id, String docType, String name, String lastName, String phone, String email, String address, String password, Date birthDate, String specialty, String professionalCard) {
        super(id, docType, name, lastName, phone, email, address, password, birthDate);
        this.specialty = specialty;
        this.professionalCard = professionalCard;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getProfessionalCard() {
        return professionalCard;
    }

    public void setProfessionalCard(String professionalCard) {
        this.professionalCard = professionalCard;
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