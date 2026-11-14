package app.service;

import app.domain.Appointment;
import app.domain.MedicalCenter;
import app.domain.Patient;
import app.domain.Professional;
import app.enums.AppointmentState;
import app.exceptions.BusinessException;
import app.helpers.DateHelper;
import app.service.inputports.AppointmentServiceInterface;
import app.service.inputports.MedicalCenterServiceInterface;
import app.service.inputports.PatientServiceInterface;
import app.service.inputports.ProfessionalServiceInterface;
import app.service.outputports.AppointmentRepositoryPort;
import app.validations.AppointmentValidator;
import app.validations.Validator;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentServiceImpl implements AppointmentServiceInterface {

    private final AppointmentRepositoryPort appointmentRepository;
    private final PatientServiceInterface patientService;
    private final ProfessionalServiceInterface professionalService;
    private final MedicalCenterServiceInterface medicalCenterService;

    public AppointmentServiceImpl(AppointmentRepositoryPort appointmentRepository,
                                  PatientServiceInterface patientService,
                                  ProfessionalServiceInterface professionalService,
                                  MedicalCenterServiceInterface medicalCenterService) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
        this.professionalService = professionalService;
        this.medicalCenterService = medicalCenterService;
    }

    @Override
    public Appointment createAppointment(long patientId, long professionalId, int centerId, LocalDateTime dateInit,
                                         LocalDateTime dateEnd, String descriptionAppointment) {
        AppointmentValidator.validateSchedule(dateInit, dateEnd);
        AppointmentValidator.validateDescription(descriptionAppointment);

        Patient patient = patientService.selectPatientById(patientId);
        Professional professional = professionalService.selectProfessionalById(professionalId);
        MedicalCenter center = medicalCenterService.selectMedicalCenterById(centerId);

        validateParticipants(patient, professional, center);
        validateNoOverlap(patient, professional, dateInit, dateEnd, null);

        // El Service crea el objeto de dominio (toda cita nueva nace SCHEDULED)
        Appointment appointment = new Appointment(null, patient, professional, center, dateInit, dateEnd,
                descriptionAppointment.trim(), AppointmentState.SCHEDULED);
        return appointmentRepository.createAppointment(appointment);
    }

    @Override
    public Appointment selectAppointmentById(int appointmentId) {
        return appointmentRepository.selectAppointmentById(appointmentId)
                .orElseThrow(() -> new BusinessException("No existe una cita con el id " + appointmentId + "."));
    }

    @Override
    public List<Appointment> selectAllAppointments() {
        return appointmentRepository.selectAllAppointments();
    }

    @Override
    public List<Appointment> selectAppointmentsByPatientId(long patientId) {
        patientService.selectPatientById(patientId); // valida que exista
        return appointmentRepository.selectAppointmentsByPatientId(patientId);
    }

    @Override
    public List<Appointment> selectAppointmentsByProfessionalId(long professionalId) {
        professionalService.selectProfessionalById(professionalId); // valida que exista
        return appointmentRepository.selectAppointmentsByProfessionalId(professionalId);
    }

    @Override
    public Appointment updateAppointment(int appointmentId, int centerId, LocalDateTime dateInit,
                                         LocalDateTime dateEnd, String descriptionAppointment) {
        Appointment appointment = selectAppointmentById(appointmentId);
        if (!appointment.getAppointmentState().isActive()) {
            throw new BusinessException("Solo se pueden modificar citas programadas o confirmadas.");
        }
        AppointmentValidator.validateSchedule(dateInit, dateEnd);
        AppointmentValidator.validateDescription(descriptionAppointment);

        MedicalCenter center = medicalCenterService.selectMedicalCenterById(centerId);
        validateParticipants(appointment.getPatient(), appointment.getProfessional(), center);
        validateNoOverlap(appointment.getPatient(), appointment.getProfessional(), dateInit, dateEnd, appointmentId);

        appointment.setMedicalCenter(center);
        appointment.setDateInit(dateInit);
        appointment.setDateEnd(dateEnd);
        appointment.setDescriptionAppointment(descriptionAppointment.trim());
        appointment.setAppointmentState(AppointmentState.SCHEDULED); // al reprogramar hay que volver a confirmar
        return appointmentRepository.updateAppointment(appointment);
    }

    @Override
    public Appointment changeAppointmentState(int appointmentId, AppointmentState newState) {
        Validator.requireNotNull(newState, "El nuevo estado");
        Appointment appointment = selectAppointmentById(appointmentId);
        AppointmentState current = appointment.getAppointmentState();

        if (!current.canTransitionTo(newState)) {
            throw new BusinessException("No se puede pasar una cita de '" + current + "' a '" + newState + "'.");
        }
        LocalDateTime now = LocalDateTime.now();
        switch (newState) {
            case CANCELLED -> {
                if (!now.isBefore(appointment.getDateInit())) {
                    throw new BusinessException("No se puede cancelar una cita que ya inició.");
                }
            }
            case COMPLETED -> {
                if (now.isBefore(appointment.getDateInit())) {
                    throw new BusinessException("No se puede completar una cita antes de su hora de inicio ("
                            + DateHelper.format(appointment.getDateInit()) + ").");
                }
            }
            case NO_SHOW -> {
                if (now.isBefore(appointment.getDateEnd())) {
                    throw new BusinessException("Solo se puede marcar inasistencia cuando la cita ya terminó.");
                }
            }
            default -> {
            }
        }
        appointment.setAppointmentState(newState);
        return appointmentRepository.updateAppointment(appointment);
    }

    @Override
    public void deleteAppointment(int appointmentId) {
        Appointment appointment = selectAppointmentById(appointmentId);
        if (appointment.getAppointmentState() == AppointmentState.COMPLETED) {
            throw new BusinessException("Las citas completadas hacen parte del historial y no se pueden eliminar.");
        }
        appointmentRepository.deleteAppointment(appointmentId);
    }

    // ---------- Reglas de negocio ----------

    private void validateParticipants(Patient patient, Professional professional, MedicalCenter center) {
        if (!center.isAvailable()) {
            throw new BusinessException("El centro '" + center.getName() + "' no está disponible.");
        }
        if (!center.hasProfessional(professional.getId())) {
            throw new BusinessException("El profesional " + professional.getFullName()
                    + " no atiende en el centro '" + center.getName() + "'.");
        }
        if (patient.getId() == professional.getId()) {
            throw new BusinessException("Un profesional no puede agendarse una cita a sí mismo.");
        }
    }

    /** Ni el profesional ni el paciente pueden tener otra cita activa que se cruce con el horario. */
    private void validateNoOverlap(Patient patient, Professional professional, LocalDateTime dateInit,
                                   LocalDateTime dateEnd, Integer ignoreAppointmentId) {
        boolean professionalBusy = appointmentRepository.selectAppointmentsByProfessionalId(professional.getId()).stream()
                .filter(a -> a.getAppointmentState().isActive())
                .filter(a -> !a.getAppointmentId().equals(ignoreAppointmentId))
                .anyMatch(a -> a.overlapsWith(dateInit, dateEnd));
        if (professionalBusy) {
            throw new BusinessException("El profesional ya tiene una cita en ese horario.");
        }
        boolean patientBusy = appointmentRepository.selectAppointmentsByPatientId(patient.getId()).stream()
                .filter(a -> a.getAppointmentState().isActive())
                .filter(a -> !a.getAppointmentId().equals(ignoreAppointmentId))
                .anyMatch(a -> a.overlapsWith(dateInit, dateEnd));
        if (patientBusy) {
            throw new BusinessException("El paciente ya tiene una cita en ese horario.");
        }
    }
}
