package app.service.inputports;

import app.domain.Professional;
import app.enums.DocType;
import app.enums.Speciality;

import java.time.LocalDate;
import java.util.List;

public interface ProfessionalServiceInterface {

    Professional createProfessional(long id, DocType docType, String name, String lastName, String phone,
                                    String email, String address, String username, String password,
                                    LocalDate birthDate, Speciality speciality, String professionalCard);

    Professional selectProfessionalById(long id);

    List<Professional> selectAllProfessionals();

    /** Si password llega vacío se conserva la contraseña actual. */
    Professional updateProfessional(long id, DocType docType, String name, String lastName, String phone,
                                    String email, String address, String username, String password,
                                    LocalDate birthDate, Speciality speciality, String professionalCard);

    void deleteProfessional(long id);
}
