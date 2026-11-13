package app.persistence.mapper;

import app.domain.Patient;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientMapper implements RowMapper<Patient> {

    @Override
    public Patient mapRow(ResultSet rs) throws SQLException {

        return new Patient(
                rs.getInt("id"),
                rs.getString("doc_type"),
                rs.getString("name"),
                rs.getString("last_name"),
                rs.getString("phone"),
                rs.getString("email"),
                rs.getString("address"),
                rs.getString("password"),
                rs.getDate("birth_date"),
                rs.getString("patient_type")
        );
    }
}