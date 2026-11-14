package app.validations;

import app.enums.DocType;
import app.exceptions.BusinessException;

import java.time.LocalDate;
import java.time.Period;

/** Validaciones comunes a toda Person (pacientes y profesionales). */
public final class PersonValidator {

    private static final int MAX_AGE = 120;

    private PersonValidator() {
    }

    public static void validate(long id, DocType docType, String name, String lastName, String phone,
                                String email, String address, String username, LocalDate birthDate) {
        Validator.requireDocumentNumber(id);
        Validator.requireNotNull(docType, "El tipo de documento");
        Validator.requirePersonName(name, "El nombre");
        Validator.requirePersonName(lastName, "El apellido");
        Validator.requirePhone(phone);
        Validator.requireEmail(email);
        Validator.requireText(address, "La dirección", 100);
        Validator.requireUsername(username);
        validateBirthDate(birthDate);
        validateDocTypeForAge(docType, birthDate);
    }

    private static void validateBirthDate(LocalDate birthDate) {
        Validator.requireNotNull(birthDate, "La fecha de nacimiento");
        if (birthDate.isAfter(LocalDate.now())) {
            throw new BusinessException("La fecha de nacimiento no puede ser futura.");
        }
        if (Period.between(birthDate, LocalDate.now()).getYears() > MAX_AGE) {
            throw new BusinessException("La fecha de nacimiento no es válida (más de " + MAX_AGE + " años).");
        }
    }

    /** La tarjeta de identidad es solo para menores de edad y la cédula solo para mayores. */
    private static void validateDocTypeForAge(DocType docType, LocalDate birthDate) {
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (docType == DocType.TI && age >= 18) {
            throw new BusinessException("La tarjeta de identidad solo aplica a menores de 18 años.");
        }
        if (docType == DocType.CC && age < 18) {
            throw new BusinessException("La cédula de ciudadanía solo aplica a mayores de 18 años.");
        }
    }
}