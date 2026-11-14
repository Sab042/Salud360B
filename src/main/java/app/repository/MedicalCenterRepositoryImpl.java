package app.repository;

import app.domain.MedicalCenter;
import app.service.outputports.MedicalCenterRepositoryPort;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MedicalCenterRepositoryImpl implements MedicalCenterRepositoryPort {

    private final Map<Integer, MedicalCenter> store = new LinkedHashMap<>();
    private int nextId = 1;

    @Override
    public MedicalCenter createMedicalCenter(MedicalCenter medicalCenter) {
        medicalCenter.setCenterId(nextId++);
        store.put(medicalCenter.getCenterId(), medicalCenter);
        return medicalCenter;
    }

    @Override
    public Optional<MedicalCenter> selectMedicalCenterById(int centerId) {
        return Optional.ofNullable(store.get(centerId));
    }

    @Override
    public Optional<MedicalCenter> selectMedicalCenterByNameAndCity(String name, String city) {
        return store.values().stream()
                .filter(c -> c.getName().equalsIgnoreCase(name) && c.getCity().equalsIgnoreCase(city))
                .findFirst();
    }

    @Override
    public List<MedicalCenter> selectAllMedicalCenters() {
        return new ArrayList<>(store.values());
    }

    @Override
    public MedicalCenter updateMedicalCenter(MedicalCenter medicalCenter) {
        store.put(medicalCenter.getCenterId(), medicalCenter);
        return medicalCenter;
    }

    @Override
    public boolean deleteMedicalCenter(int centerId) {
        return store.remove(centerId) != null;
    }
}
