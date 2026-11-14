package app.service.inputports

import app.domain.Patient
import app.enums.DocType
import app.enums.PatientType
import java.time.LocalDate

interface PatientServiceInterface {
    fun createPatient(
        id: Long, docType: DocType?, name: String?, lastName: String?, phone: String?, email: String?,
        address: String?, username: String?, password: String?, birthDate: LocalDate?,
        typePatient: PatientType?
    ): Patient?

    fun selectPatientById(id: Long): Patient?

    fun selectAllPatients(): MutableList<Patient?>?

    /** Si password llega vacío se conserva la contraseña actual.  */
    fun updatePatient(
        id: Long, docType: DocType?, name: String?, lastName: String?, phone: String?, email: String?,
        address: String?, username: String?, password: String?, birthDate: LocalDate?,
        typePatient: PatientType?
    ): Patient?

    fun deletePatient(id: Long)
}