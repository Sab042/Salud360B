package app.service.helpers;

import app.domain.enums.PatientType;
import app.service.validations.FormTypeValidator;

public class SetPatientType {

    public static String getPatientType() {
        int option = FormTypeValidator.intValidator("1. Regimen Contributivo\n" +
                "2. Regimen Subsidiado\n" +
                "3. Particular / Prepagada");

        String value = "";
        switch (option) {
            case 1:
                value = PatientType.CONTRIBUTIVO.getValue();
                break;
            case 2:
                value = PatientType.SUBSIDIADO.getValue();
                break;
            case 3:
                value = PatientType.PARTICULAR.getValue();
                break;
            default:
                System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                break;
        }
        return value;
    }
}
