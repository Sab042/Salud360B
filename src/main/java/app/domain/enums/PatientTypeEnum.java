package app.domain.enums;

public enum PatientTypeEnum {

    CONTRIBUTIVO("Regimen Contributivo"),
    SUBSIDIADO("Regimen Subsidiado"),
    PARTICULAR("Particular / Prepagada");

    private final String value;

    PatientTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
