package app.enums

/** Especialidades médicas disponibles.  */
enum class Speciality(label: String) {
    GENERAL_MEDICINE("Medicina general"),
    PEDIATRICS("Pediatría"),
    CARDIOLOGY("Cardiología"),
    DERMATOLOGY("Dermatología"),
    GYNECOLOGY("Ginecología"),
    ODONTOLOGY("Odontología"),
    PSYCHOLOGY("Psicología");

    private val label: String?

    init {
        this.label = label
    }

    override fun toString(): String {
        return label!!
    }
}