package app.service.outputports;

import app.domain.MedicalCenter;

import java.util.list;
import java.util.optional;


public interface MedicalCenterRepositoryPort {

    MedicalCenter createMedicalCenter(MedicalCenter medicalCenter);

    Optional<MedicalCenter> selectMedicalCenterById(int centerId);

    Optional<MedicalCenter> selectMedicalCenterByNameAndCity(String name, String city);

    List<MedicalCenter> selectAllMedicalCenters();

    MedicalCenter updateMedicalCenter(MedicalCenter medicalCenter);

    boolean deleteMedicalCenter(int centerId);
}
