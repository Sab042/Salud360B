package app.enums

/**
 * Estados de una cita médica y sus transiciones permitidas:
 * SCHEDULED -> CONFIRMED | CANCELLED
 * CONFIRMED -> COMPLETED | CANCELLED | NO_SHOW
 * COMPLETED, CANCELLED y NO_SHOW son estados finales.
 */
enum class AppointmentState(label: String) {
    SCHEDULED("Programada"),
    CONFIRMED("Confirmada"),
    COMPLETED("Completada"),
    CANCELLED("Cancelada"),
    NO_SHOW("No asistió");

    private val label: String?

    init {
        this.label = label
    }

    fun canTransitionTo(next: AppointmentState?): Boolean {
        return when (this) {
            SCHEDULED -> next == CONFIRMED || next == CANCELLED
            CONFIRMED -> next == COMPLETED || next == CANCELLED || next == NO_SHOW
            else -> false
        }
    }

    val isActive: Boolean
        /** Una cita activa todavía ocupa la agenda (programada o confirmada).  */
        get() = this == SCHEDULED || this == CONFIRMED

    override fun toString(): String {
        return label!!
    }
}