package app.domain.enums;

public enum PatientType {

    CONTRIBUTIVO("Regimen Contributivo"),
    SUBSIDIADO("Regimen Subsidiado"),
    PARTICULAR("Particular / Prepagada");

    private final String value;

    PatientType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
