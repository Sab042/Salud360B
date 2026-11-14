package app.validations;

import app.exceptions.BusinessException;

import java.util.regex.Pattern;

/** Validaciones genéricas reutilizables. Lanzan BusinessException con un mensaje claro. */
public final class Validator {

    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private static final Pattern PHONE = Pattern.compile("^\\d{7,10}$");
    private static final Pattern PERSON_NAME = Pattern.compile("^[\\p{L} .'-]{2,60}$");
    private static final Pattern USERNAME = Pattern.compile("^[A-Za-z0-9_.]{4,20}$");

    private Validator() {
    }

    public static void requireNotNull(Object value, String field) {
        if (value == null) {
            throw new BusinessException(field + " es obligatorio.");
        }
    }

    public static void requireText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new BusinessException(field + " es obligatorio.");
        }
        if (value.trim().length() > maxLength) {
            throw new BusinessException(field + " no puede superar " + maxLength + " caracteres.");
        }
    }

    public static void requirePersonName(String value, String field) {
        if (value == null || !PERSON_NAME.matcher(value.trim()).matches()) {
            throw new BusinessException(field + " debe tener entre 2 y 60 letras (sin números).");
        }
    }

    public static void requireDocumentNumber(long id) {
        int digits = String.valueOf(id).length();
        if (id <= 0 || digits < 5 || digits > 12) {
            throw new BusinessException("El número de documento debe tener entre 5 y 12 dígitos.");
        }
    }

    public static void requireEmail(String email) {
        if (email == null || !EMAIL.matcher(email.trim()).matches()) {
            throw new BusinessException("El correo electrónico no tiene un formato válido.");
        }
    }

    public static void requirePhone(String phone) {
        if (phone == null || !PHONE.matcher(phone.trim()).matches()) {
            throw new BusinessException("El teléfono debe tener entre 7 y 10 dígitos, sin espacios.");
        }
    }

    public static void requireUsername(String username) {
        if (username == null || !USERNAME.matcher(username.trim()).matches()) {
            throw new BusinessException("El usuario debe tener entre 4 y 20 caracteres (letras, números, punto o guion bajo).");
        }
    }

    public static void requirePassword(String password) {
        boolean valid = password != null
                && password.length() >= 8
                && password.chars().anyMatch(Character::isLetter)
                && password.chars().anyMatch(Character::isDigit);
        if (!valid) {
            throw new BusinessException("La contraseña debe tener mínimo 8 caracteres e incluir letras y números.");
        }
    }
}