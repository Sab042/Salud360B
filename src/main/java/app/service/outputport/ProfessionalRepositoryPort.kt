package app.service.outputports

import app.domain.Professional
import java.util.*

interface ProfessionalRepositoryPort {
    fun createProfessional(professional: Professional?): Professional?

    fun selectProfessionalById(id: Long): Optional<Professional?>?

    fun selectProfessionalByEmail(email: String?): Optional<Professional?>?

    fun selectProfessionalByUsername(username: String?): Optional<Professional?>?

    fun selectProfessionalByCard(professionalCard: String?): Optional<Professional?>?

    fun selectAllProfessionals(): MutableList<Professional?>?

    fun updateProfessional(professional: Professional?): Professional?

    fun deleteProfessional(id: Long): Boolean
}