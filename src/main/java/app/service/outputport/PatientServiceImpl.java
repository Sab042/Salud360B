package app.service;

import app.domain.Appointment;
import app.domain.Patient;
import app.enums.DocType;
import app.enums.PatientType;
import app.exceptions.BusinessException;
import app.helpers.PasswordHelper;
import app.service.inputports.PatientServiceInterface;
import app.service.outputports.AppointmentRepositoryPort;
import app.service.outputports.PatientRepositoryPort;
import app.validations.PersonValidator;
import app.validations.Validator;

import java.time.LocalDate;
import java.util.List;

public class PatientServiceImpl implements PatientServiceInterface {

    // Dependencias: siempre contra interfaces (inversión de dependencias)
    private final PatientRepositoryPort patientRepository;
    private final AppointmentRepositoryPort appointmentRepository;

    public PatientServiceImpl(PatientRepositoryPort patientRepository, AppointmentRepositoryPort appointmentRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Patient createPatient(long id, DocType docType, String name, String lastName, String phone, String email,
                                 String address, String username, String password, LocalDate birthDate,
                                 PatientType typePatient) {
        PersonValidator.validate(id, docType, name, lastName, phone, email, address, username, birthDate);
        Validator.requirePassword(password);
        Validator.requireNotNull(typePatient, "El tipo de paciente");

        if (patientRepository.selectPatientById(id).isPresent()) {
            throw new BusinessException("Ya existe un paciente con el documento " + id + ".");
        }
        if (patientRepository.selectPatientByEmail(email.trim()).isPresent()) {
            throw new BusinessException("Ya existe un paciente con el correo " + email.trim() + ".");
        }
        if (patientRepository.selectPatientByUsername(username.trim()).isPresent()) {
            throw new BusinessException("El usuario '" + username.trim() + "' ya está en uso.");
        }

        // El Service crea el objeto de dominio
        Patient patient = new Patient(id, docType, name.trim(), lastName.trim(), phone.trim(),
                email.trim().toLowerCase(), address.trim(), username.trim(),
                PasswordHelper.hash(password), birthDate, typePatient);
        return patientRepository.createPatient(patient);
    }

    @Override
    public Patient selectPatientById(long id) {
        return patientRepository.selectPatientById(id)
                .orElseThrow(() -> new BusinessException("No existe un paciente con el documento " + id + "."));
    }

    @Override
    public List<Patient> selectAllPatients() {
        return patientRepository.selectAllPatients();
    }

    @Override
    public Patient updatePatient(long id, DocType docType, String name, String lastName, String phone, String email,
                                 String address, String username, String password, LocalDate birthDate,
                                 PatientType typePatient) {
        Patient patient = selectPatientById(id);

        PersonValidator.validate(id, docType, name, lastName, phone, email, address, username, birthDate);
        Validator.requireNotNull(typePatient, "El tipo de paciente");
        boolean changePassword = password != null && !password.isBlank();
        if (changePassword) {
            Validator.requirePassword(password);
        }

        patientRepository.selectPatientByEmail(email.trim()).ifPresent(other -> {
            if (other.getId() != id) {
                throw new BusinessException("Ya existe otro paciente con el correo " + email.trim() + ".");
            }
        });
        patientRepository.selectPatientByUsername(username.trim()).ifPresent(other -> {
            if (other.getId() != id) {
                throw new BusinessException("El usuario '" + username.trim() + "' ya está en uso.");
            }
        });

        // Se modifica el mismo objeto para que las citas que lo referencian queden al día
        patient.setDocType(docType);
        patient.setName(name.trim());
        patient.setLastName(lastName.trim());
        patient.setPhone(phone.trim());
        patient.setEmail(email.trim().toLowerCase());
        patient.setAddress(address.trim());
        patient.setUsername(username.trim());
        patient.setBirthDate(birthDate);
        patient.setTypePatient(typePatient);
        if (changePassword) {
            patient.setPassword(PasswordHelper.hash(password));
        }
        return patientRepository.updatePatient(patient);
    }

    @Override
    public void deletePatient(long id) {
        selectPatientById(id);
        boolean hasActiveAppointments = appointmentRepository.selectAppointmentsByPatientId(id).stream()
                .map(Appointment::getAppointmentState)
                .anyMatch(state -> state.isActive());
        if (hasActiveAppointments) {
            throw new BusinessException("No se puede eliminar: el paciente tiene citas activas. Cancélelas primero.");
        }
        patientRepository.deletePatient(id);
    }
}