package app.repository;

import app.domain.Professional;
import app.service.outputports.ProfessionalRepositoryPort;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProfessionalRepositoryImpl implements ProfessionalRepositoryPort {

    private final Map<Long, Professional> store = new LinkedHashMap<>();

    @Override
    public Professional createProfessional(Professional professional) {
        store.put(professional.getId(), professional);
        return professional;
    }

    @Override
    public Optional<Professional> selectProfessionalById(long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Professional> selectProfessionalByEmail(String email) {
        return store.values().stream().filter(p -> p.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    @Override
    public Optional<Professional> selectProfessionalByUsername(String username) {
        return store.values().stream().filter(p -> p.getUsername().equalsIgnoreCase(username)).findFirst();
    }

    @Override
    public Optional<Professional> selectProfessionalByCard(String professionalCard) {
        return store.values().stream().filter(p -> p.getProfessionalCard().equalsIgnoreCase(professionalCard)).findFirst();
    }

    @Override
    public List<Professional> selectAllProfessionals() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Professional updateProfessional(Professional professional) {
        store.put(professional.getId(), professional);
        return professional;
    }

    @Override
    public boolean deleteProfessional(long id) {
        return store.remove(id) != null;
    }
}
