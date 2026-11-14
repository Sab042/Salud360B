package app.validations;

import app.exceptions.BusinessException;
import app.helpers.DateHelper;

import java.time.Duration;
import java.time.LocalDateTime;

/** Reglas de calendario para agendar una cita. */
public final class AppointmentValidator {

    public static final long MIN_MINUTES = 15;
    public static final long MAX_MINUTES = 120;

    private AppointmentValidator() {
    }

    public static void validateDescription(String description) {
        Validator.requireText(description, "La descripción de la cita", 250);
    }

    public static void validateSchedule(LocalDateTime dateInit, LocalDateTime dateEnd) {
        Validator.requireNotNull(dateInit, "La fecha de inicio");
        Validator.requireNotNull(dateEnd, "La fecha de fin");

        if (!dateInit.isAfter(LocalDateTime.now())) {
            throw new BusinessException("La cita debe programarse en una fecha y hora futura.");
        }
        if (!dateEnd.isAfter(dateInit)) {
            throw new BusinessException("La fecha de fin debe ser posterior a la de inicio.");
        }
        if (!dateInit.toLocalDate().equals(dateEnd.toLocalDate())) {
            throw new BusinessException("La cita debe iniciar y terminar el mismo día.");
        }
        long minutes = Duration.between(dateInit, dateEnd).toMinutes();
        if (minutes < MIN_MINUTES || minutes > MAX_MINUTES) {
            throw new BusinessException("La duración debe estar entre " + MIN_MINUTES + " y " + MAX_MINUTES + " minutos.");
        }
        if (!DateHelper.isWorkingDay(dateInit.toLocalDate())) {
            throw new BusinessException("No se atiende los domingos.");
        }
        if (dateInit.toLocalTime().isBefore(DateHelper.OPENING_TIME) || dateEnd.toLocalTime().isAfter(DateHelper.CLOSING_TIME)) {
            throw new BusinessException("El horario de atención es de " + DateHelper.OPENING_TIME + " a " + DateHelper.CLOSING_TIME + ".");
        }
    }
}
