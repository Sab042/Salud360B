package app.view;

import app.enums.DocType;
import app.helpers.ConsoleHelper;

import java.time.LocalDate;

/** Captura por consola los datos comunes de una Person (la reutilizan PatientView y ProfessionalView). */
public class PersonForm {

    public record Data(long id, DocType docType, String name, String lastName, String phone, String email,
                       String address, String username, String password, LocalDate birthDate) {
    }

    private final ConsoleHelper console;

    public PersonForm(ConsoleHelper console) {
        this.console = console;
    }

    /**
     * @param fixedId documento ya conocido (actualización) o null para pedirlo (registro).
     *                En actualización la contraseña es opcional: Enter la deja igual.
     */
    public Data capture(Long fixedId) {
        boolean updating = fixedId != null;
        long id = updating ? fixedId : console.readLong("Número de documento");
        DocType docType = console.readEnum("Tipo de documento", DocType.class);
        String name = console.readText("Nombre");
        String lastName = console.readText("Apellido");
        String phone = console.readText("Teléfono (7 a 10 dígitos)");
        String email = console.readText("Correo electrónico");
        String address = console.readText("Dirección");
        String username = console.readText("Usuario");
        String password = updating
                ? console.readOptionalText("Contraseña (Enter para no cambiarla)")
                : console.readText("Contraseña (mín. 8 caracteres, letras y números)");
        LocalDate birthDate = console.readDate("Fecha de nacimiento");
        return new Data(id, docType, name, lastName, phone, email, address, username, password, birthDate);
    }
}
