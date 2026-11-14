package app.domain;

import app.enums.DocType;
import app.enums.PatientType;

import java.time.LocalDate;

public class Patient extends Person {

    private PatientType typePatient;

    public Patient() {
        super();
    }

    public Patient(long id, DocType docType, String name, String lastName, String phone, String email,
                   String address, String username, String password, LocalDate birthDate,
                   PatientType typePatient) {
        super(id, docType, name, lastName, phone, email, address, username, password, birthDate);
        this.typePatient = typePatient;
    }

    public PatientType getTypePatient() {
        return typePatient;
    }

    public void setTypePatient(PatientType typePatient) {
        this.typePatient = typePatient;
    }
}
