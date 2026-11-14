package app.service;

import app.domain.MedicalCenter;
import app.domain.Professional;
import app.enums.CenterType;
import app.exceptions.BusinessException;
import app.service.inputports.MedicalCenterServiceInterface;
import app.service.inputports.ProfessionalServiceInterface;
import app.service.outputports.AppointmentRepositoryPort;
import app.service.outputports.MedicalCenterRepositoryPort;
import app.validations.Validator;

import java.util.ArrayList;
import java.util.List;

public class MedicalCenterServiceImpl implements MedicalCenterServiceInterface {

    private final MedicalCenterRepositoryPort medicalCenterRepository;
    private final ProfessionalServiceInterface professionalService;
    private final AppointmentRepositoryPort appointmentRepository;

    public MedicalCenterServiceImpl(MedicalCenterRepositoryPort medicalCenterRepository,
                                    ProfessionalServiceInterface professionalService,
                                    AppointmentRepositoryPort appointmentRepository) {
        this.medicalCenterRepository = medicalCenterRepository;
        this.professionalService = professionalService;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public MedicalCenter createMedicalCenter(String name, CenterType centerType, String address, String city,
                                             boolean isAvailable) {
        validate(name, centerType, address, city);
        if (medicalCenterRepository.selectMedicalCenterByNameAndCity(name.trim(), city.trim()).isPresent()) {
            throw new BusinessException("Ya existe un centro llamado '" + name.trim() + "' en " + city.trim() + ".");
        }

        // El Service crea el objeto de dominio (el id lo asigna el repositorio)
        MedicalCenter center = new MedicalCenter(null, name.trim(), centerType, address.trim(), city.trim(),
                new ArrayList<>(), isAvailable);
        return medicalCenterRepository.createMedicalCenter(center);
    }

    @Override
    public MedicalCenter selectMedicalCenterById(int centerId) {
        return medicalCenterRepository.selectMedicalCenterById(centerId)
                .orElseThrow(() -> new BusinessException("No existe un centro médico con el id " + centerId + "."));
    }

    @Override
    public List<MedicalCenter> selectAllMedicalCenters() {
        return medicalCenterRepository.selectAllMedicalCenters();
    }

    @Override
    public MedicalCenter updateMedicalCenter(int centerId, String name, CenterType centerType, String address,
                                             String city, boolean isAvailable) {
        MedicalCenter center = selectMedicalCenterById(centerId);
        validate(name, centerType, address, city);
        medicalCenterRepository.selectMedicalCenterByNameAndCity(name.trim(), city.trim()).ifPresent(other -> {
            if (!other.getCenterId().equals(centerId)) {
                throw new BusinessException("Ya existe otro centro llamado '" + name.trim() + "' en " + city.trim() + ".");
            }
        });

        center.setName(name.trim());
        center.setCenterType(centerType);
        center.setAddress(address.trim());
        center.setCity(city.trim());
        center.setAvailable(isAvailable);
        return medicalCenterRepository.updateMedicalCenter(center);
    }

    @Override
    public void deleteMedicalCenter(int centerId) {
        selectMedicalCenterById(centerId);
        boolean hasActiveAppointments = appointmentRepository.selectAppointmentsByMedicalCenterId(centerId).stream()
                .anyMatch(a -> a.getAppointmentState().isActive());
        if (hasActiveAppointments) {
            throw new BusinessException("No se puede eliminar: el centro tiene citas activas.");
        }
        medicalCenterRepository.deleteMedicalCenter(centerId);
    }

    @Override
    public MedicalCenter assignProfessional(int centerId, long professionalId) {
        MedicalCenter center = selectMedicalCenterById(centerId);
        Professional professional = professionalService.selectProfessionalById(professionalId);
        if (center.hasProfessional(professionalId)) {
            throw new BusinessException("El profesional ya está asignado a este centro.");
        }
        center.addProfessional(professional);
        return medicalCenterRepository.updateMedicalCenter(center);
    }

    @Override
    public MedicalCenter removeProfessional(int centerId, long professionalId) {
        MedicalCenter center = selectMedicalCenterById(centerId);
        if (!center.hasProfessional(professionalId)) {
            throw new BusinessException("El profesional no está asignado a este centro.");
        }
        boolean hasActiveAppointments = appointmentRepository.selectAppointmentsByMedicalCenterId(centerId).stream()
                .anyMatch(a -> a.getProfessional().getId() == professionalId && a.getAppointmentState().isActive());
        if (hasActiveAppointments) {
            throw new BusinessException("No se puede retirar: el profesional tiene citas activas en este centro.");
        }
        center.removeProfessional(professionalId);
        return medicalCenterRepository.updateMedicalCenter(center);
    }

    private void validate(String name, CenterType centerType, String address, String city) {
        Validator.requireText(name, "El nombre del centro", 80);
        Validator.requireNotNull(centerType, "El tipo de centro");
        Validator.requireText(address, "La dirección", 100);
        Validator.requireText(city, "La ciudad", 50);
    }
}
