package app.repository;

import app.config.DatabaseConnection;
import app.domain.Patient;
import app.enums.DocType;
import app.enums.PatientType;
import app.exceptions.BusinessException;
import app.service.outputports.PatientRepositoryPort;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Adaptador de salida: guarda los pacientes en MySQL (XAMPP) usando JDBC. */
public class PatientRepositoryImpl implements PatientRepositoryPort {

    @Override
    public Patient createPatient(Patient p) {
        String sql = "INSERT INTO patient (id, doc_type, name, last_name, phone, email, address, username, "
                + "password, birth_date, type_patient) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, p.getId());
            ps.setString(2, p.getDocType().name());
            ps.setString(3, p.getName());
            ps.setString(4, p.getLastName());
            ps.setString(5, p.getPhone());
            ps.setString(6, p.getEmail());
            ps.setString(7, p.getAddress());
            ps.setString(8, p.getUsername());
            ps.setString(9, p.getPassword());
            ps.setDate(10, java.sql.Date.valueOf(p.getBirthDate()));
            ps.setString(11, p.getTypePatient().name());
            ps.executeUpdate();
            return p;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Patient> selectPatientById(long id) {
        String sql = "SELECT * FROM patient WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Patient> selectPatientByEmail(String email) {
        return selectOneByText("SELECT * FROM patient WHERE LOWER(email) = LOWER(?)", email);
    }

    private Optional<Patient> selectOneByText(String query, String email) {
    }

    @Override
    public Optional<Patient>
}
