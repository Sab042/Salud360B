package app.domain.enums;

public enum DocumentTypeEnum {

    CEDULA_CIUDADANIA("Cedula de Ciudadania"),
    TARJETA_IDENTIDAD("Tarjeta de Identidad"),
    CEDULA_EXTRANJERIA("Cedula de Extranjeria"),
    PASAPORTE("Pasaporte");

    private final String value;

    DocumentTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}