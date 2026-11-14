package app.enums

/** Tipos de centro médico.  */
enum class CenterType(label: String) {
    HOSPITAL("Hospital"),
    CLINIC("Clínica"),
    MEDICAL_OFFICE("Consultorio"),
    LABORATORY("Laboratorio");

    private val label: String?

    init {
        this.label = label
    }

    override fun toString(): String {
        return label!!
    }
}