
package app.persistence.repository;

import app.domain.Patient;
import app.persistence.mapper.RowMapper;
import app.service.outputport.PatientRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientRepositoryImpl implements PatientRepository {

    private final Connection connection;
    private final RowMapper<Patient> rowMapper;

    public PatientRepositoryImpl(Connection connection,
                                 RowMapper<Patient> rowMapper) {
        this.connection = connection;
        this.rowMapper = rowMapper;
    }

    @Override
    public Patient savePatient(Patient patient) {

        String sql = "INSERT INTO patients " +
                "(id, doc_type, name, last_name, phone, email, address, password, birth_date, patient_type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, patient.getId());
            statement.setString(2, patient.getDocType());
            statement.setString(3, patient.getName());
            statement.setString(4, patient.getLastName());
            statement.setString(5, patient.getPhone());
            statement.setString(6, patient.getEmail());
            statement.setString(7, patient.getAddress());
            statement.setString(8, patient.getPassword());

            if (patient.getBirthDate() != null) {
                statement.setDate(9, new java.sql.Date(
                        patient.getBirthDate().getTime()));
            } else {
                statement.setNull(9, java.sql.Types.DATE);
            }

            statement.setString(10, patient.getPatientType());

            statement.executeUpdate();
            return patient;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al guardar el paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Patient> selectAllPatients() {

        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                patients.add(rowMapper.mapRow(result));
            }

            return patients;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al consultar los pacientes: " + e.getMessage(), e);
        }
    }

    @Override
    public Patient selectPatientById(int id) {

        String sql = "SELECT * FROM patients WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return rowMapper.mapRow(result);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al buscar el paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public Patient updatePatient(Patient patient) {

        String sql = "UPDATE patients SET doc_type=?, name=?, last_name=?, " +
                "phone=?, email=?, address=?, password=?, birth_date=?, patient_type=? " +
                "WHERE id=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, patient.getDocType());
            statement.setString(2, patient.getName());
            statement.setString(3, patient.getLastName());
            statement.setString(4, patient.getPhone());
            statement.setString(5, patient.getEmail());
            statement.setString(6, patient.getAddress());
            statement.setString(7, patient.getPassword());

            if (patient.getBirthDate() != null) {
                statement.setDate(8, new java.sql.Date(
                        patient.getBirthDate().getTime()));
            } else {
                statement.setNull(8, java.sql.Types.DATE);
            }

            statement.setString(9, patient.getPatientType());
            statement.setInt(10, patient.getId());

            int rows = statement.executeUpdate();

            return rows > 0 ? patient : null;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al actualizar el paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletePatient(int id) {

        String sql = "DELETE FROM patients WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al eliminar el paciente: " + e.getMessage(), e);
        }
    }
}
