package app.domain;

import app.enums.DocType;
import app.enums.Speciality;

import java.time.LocalDate;

public class Professional extends Person {

    private Speciality speciality;
    private String professionalCard;

    public Professional() {
        super();
    }

    public Professional(long id, DocType docType, String name, String lastName, String phone, String email,
                        String address, String username, String password, LocalDate birthDate,
                        Speciality speciality, String professionalCard) {
        super(id, docType, name, lastName, phone, email, address, username, password, birthDate);
        this.speciality = speciality;
        this.professionalCard = professionalCard;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public String getProfessionalCard() {
        return professionalCard;
    }

    public void setProfessionalCard(String professionalCard) {
        this.professionalCard = professionalCard;
    }
}
