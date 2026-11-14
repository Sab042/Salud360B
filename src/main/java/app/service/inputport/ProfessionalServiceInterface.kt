package app.service.inputports

import app.domain.Professional
import app.enums.DocType
import app.enums.Speciality
import java.time.LocalDate

interface ProfessionalServiceInterface {
    fun createProfessional(
        id: Long, docType: DocType?, name: String?, lastName: String?, phone: String?,
        email: String?, address: String?, username: String?, password: String?,
        birthDate: LocalDate?, speciality: Speciality?, professionalCard: String?
    ): Professional?

    fun selectProfessionalById(id: Long): Professional?

    fun selectAllProfessionals(): MutableList<Professional?>?

    /** Si password llega vacío se conserva la contraseña actual.  */
    fun updateProfessional(
        id: Long, docType: DocType?, name: String?, lastName: String?, phone: String?,
        email: String?, address: String?, username: String?, password: String?,
        birthDate: LocalDate?, speciality: Speciality?, professionalCard: String?
    ): Professional?

    fun deleteProfessional(id: Long)
}