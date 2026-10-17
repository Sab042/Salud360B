package app.service.helpers;

import app.domain.enums.DocumentTypeEnum;
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
                value = DocumentTypeEnum.CEDULA_CIUDADANIA.getValue();
                break;
            case 2:
                value = DocumentTypeEnum.TARJETA_IDENTIDAD.getValue();
                break;
            case 3:
                value = DocumentTypeEnum.CEDULA_EXTRANJERIA.getValue();
                break;
            case 4:
                value = DocumentTypeEnum.PASAPORTE.getValue();
                break;
            default:
                System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                break;
        }
        return value;
    }
}
