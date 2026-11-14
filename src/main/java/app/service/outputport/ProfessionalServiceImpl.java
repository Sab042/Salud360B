package app.service;

import app.domain.Appointment;
import app.domain.Professional;
import app.enums.DocType;
import app.enums.Speciality;
import app.exceptions.BusinessException;
import app.helpers.PasswordHelper;
import app.service.inputports.ProfessionalServiceInterface;
import app.service.outputports.AppointmentRepositoryPort;
import app.service.outputports.MedicalCenterRepositoryPort;
import app.service.outputports.ProfessionalRepositoryPort;
import app.validations.PersonValidator;
import app.validations.Validator;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.regex.Pattern;

public class ProfessionalServiceImpl implements ProfessionalServiceInterface {

    private static final Pattern CARD = Pattern.compile("^[A-Za-z0-9-]{4,20}$");
    private static final int MIN_AGE = 21;

    private final ProfessionalRepositoryPort professionalRepository;
    private final AppointmentRepositoryPort appointmentRepository;
    private final MedicalCenterRepositoryPort medicalCenterRepository;

    public ProfessionalServiceImpl(ProfessionalRepositoryPort professionalRepository,
                                   AppointmentRepositoryPort appointmentRepository,
                                   MedicalCenterRepositoryPort medicalCenterRepository) {
        this.professionalRepository = professionalRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicalCenterRepository = medicalCenterRepository;
    }

    @Override
    public Professional createProfessional(long id, DocType docType, String name, String lastName, String phone,
                                           String email, String address, String username, String password,
                                           LocalDate birthDate, Speciality speciality, String professionalCard) {
        PersonValidator.validate(id, docType, name, lastName, phone, email, address, username, birthDate);
        Validator.requirePassword(password);
        validateProfessionalData(birthDate, speciality, professionalCard);

        if (professionalRepository.selectProfessionalById(id).isPresent()) {
            throw new BusinessException("Ya existe un profesional con el documento " + id + ".");
        }
        if (professionalRepository.selectProfessionalByEmail(email.trim()).isPresent()) {
            throw new BusinessException("Ya existe un profesional con el correo " + email.trim() + ".");
        }
        if (professionalRepository.selectProfessionalByUsername(username.trim()).isPresent()) {
            throw new BusinessException("El usuario '" + username.trim() + "' ya está en uso.");
        }
        if (professionalRepository.selectProfessionalByCard(professionalCard.trim()).isPresent()) {
            throw new BusinessException("La tarjeta profesional " + professionalCard.trim() + " ya está registrada.");
        }

        // El Service crea el objeto de dominio
        Professional professional = new Professional(id, docType, name.trim(), lastName.trim(), phone.trim(),
                email.trim().toLowerCase(), address.trim(), username.trim(), PasswordHelper.hash(password),
                birthDate, speciality, professionalCard.trim().toUpperCase());
        return professionalRepository.createProfessional(professional);
    }

    @Override
    public Professional selectProfessionalById(long id) {
        return professionalRepository.selectProfessionalById(id)
                .orElseThrow(() -> new BusinessException("No existe un profesional con el documento " + id + "."));
    }

    @Override
    public List<Professional> selectAllProfessionals() {
        return professionalRepository.selectAllProfessionals();
    }

    @Override
    public Professional updateProfessional(long id, DocType docType, String name, String lastName, String phone,
                                           String email, String address, String username, String password,
                                           LocalDate birthDate, Speciality speciality, String professionalCard) {
        Professional professional = selectProfessionalById(id);

        PersonValidator.validate(id, docType, name, lastName, phone, email, address, username, birthDate);
        validateProfessionalData(birthDate, speciality, professionalCard);
        boolean changePassword = password != null && !password.isBlank();
        if (changePassword) {
            Validator.requirePassword(password);
        }

        professionalRepository.selectProfessionalByEmail(email.trim()).ifPresent(other -> {
            if (other.getId() != id) {
                throw new BusinessException("Ya existe otro profesional con el correo " + email.trim() + ".");
            }
        });
        professionalRepository.selectProfessionalByUsername(username.trim()).ifPresent(other -> {
            if (other.getId() != id) {
                throw new BusinessException("El usuario '" + username.trim() + "' ya está en uso.");
            }
        });
        professionalRepository.selectProfessionalByCard(professionalCard.trim()).ifPresent(other -> {
            if (other.getId() != id) {
                throw new BusinessException("La tarjeta profesional " + professionalCard.trim() + " ya está registrada.");
            }
        });

        professional.setDocType(docType);
        professional.setName(name.trim());
        professional.setLastName(lastName.trim());
        professional.setPhone(phone.trim());
        professional.setEmail(email.trim().toLowerCase());
        professional.setAddress(address.trim());
        professional.setUsername(username.trim());
        professional.setBirthDate(birthDate);
        professional.setSpeciality(speciality);
        professional.setProfessionalCard(professionalCard.trim().toUpperCase());
        if (changePassword) {
            professional.setPassword(PasswordHelper.hash(password));
        }
        return professionalRepository.updateProfessional(professional);
    }

    @Override
    public void deleteProfessional(long id) {
        selectProfessionalById(id);
        boolean hasActiveAppointments = appointmentRepository.selectAppointmentsByProfessionalId(id).stream()
                .map(Appointment::getAppointmentState)
                .anyMatch(state -> state.isActive());
        if (hasActiveAppointments) {
            throw new BusinessException("No se puede eliminar: el profesional tiene citas activas. Cancélelas primero.");
        }
        boolean assignedToCenter = medicalCenterRepository.selectAllMedicalCenters().stream()
                .anyMatch(center -> center.hasProfessional(id));
        if (assignedToCenter) {
            throw new BusinessException("No se puede eliminar: el profesional está asignado a un centro médico. Retírelo primero.");
        }
        professionalRepository.deleteProfessional(id);
    }

    private void validateProfessionalData(LocalDate birthDate, Speciality speciality, String professionalCard) {
        Validator.requireNotNull(speciality, "La especialidad");
        if (professionalCard == null || !CARD.matcher(professionalCard.trim()).matches()) {
            throw new BusinessException("La tarjeta profesional debe tener entre 4 y 20 caracteres (letras, números o guion).");
        }
        if (Period.between(birthDate, LocalDate.now()).getYears() < MIN_AGE) {
            throw new BusinessException("El profesional debe tener al menos " + MIN_AGE + " años.");
        }
    }
}
