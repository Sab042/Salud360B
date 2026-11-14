package app.service.outputports

import app.domain.Patient
import java.util.*

interface PatientRepositoryPort {
    fun createPatient(patient: Patient?): Patient?

    fun selectPatientById(id: Long): Optional<Patient?>?

    fun selectPatientByEmail(email: String?): Optional<Patient?>?

    fun selectPatientByUsername(username: String?): Optional<Patient?>?

    fun selectAllPatients(): MutableList<Patient?>?

    fun updatePatient(patient: Patient?): Patient?

    fun deletePatient(id: Long): Boolean
}