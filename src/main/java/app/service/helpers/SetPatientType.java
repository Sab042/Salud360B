package app.service.helpers;

import app.domain.enums.PatientTypeEnum;
import app.service.validations.FormTypeValidator;

public class SetPatientType {

    public static String getPatientType() {
        int option = FormTypeValidator.intValidator("1. Regimen Contributivo\n" +
                "2. Regimen Subsidiado\n" +
                "3. Particular / Prepagada");

        String value = "";
        switch (option) {
            case 1:
                value = PatientTypeEnum.CONTRIBUTIVO.getValue();
                break;
            case 2:
                value = PatientTypeEnum.SUBSIDIADO.getValue();
                break;
            case 3:
                value = PatientTypeEnum.PARTICULAR.getValue();
                break;
            default:
                System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                break;
        }
        return value;
    }
}
