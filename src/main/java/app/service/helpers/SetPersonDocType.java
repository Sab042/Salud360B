package app.service.helpers;

import app.domain.enums.DocType;
import app.service.validations.FormTypeValidator;

public class SetPersonDocType {

    public static String getDocType() {
        int option = FormTypeValidator.intValidator("Seleccione:\n" +
                "1. Cedula\n" +
                "2. Tarjeta de Identidad\n" +
                "3. Cedula Extranjeria\n" +
                "4. Pasaporte");

        String value = "";
        switch (option) {
            case 1:
                value = DocType.CEDULA_CIUDADANIA.getValue();
                break;
            case 2:
                value = DocType.TARJETA_IDENTIDAD.getValue();
                break;
            case 3:
                value = DocType.CEDULA_EXTRANJERIA.getValue();
                break;
            case 4:
                value = DocType.PASAPORTE.getValue();
                break;
            default:
                System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                break;
        }
        return value;
    }
}
