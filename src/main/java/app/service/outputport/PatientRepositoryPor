package app.service.outputports

import app.domain.MedicalCenter
import java.util.*

interface MedicalCenterRepositoryPort {
    fun createMedicalCenter(medicalCenter: MedicalCenter?): MedicalCenter?

    fun selectMedicalCenterById(centerId: Int): Optional<MedicalCenter?>?

    fun selectMedicalCenterByNameAndCity(name: String?, city: String?): Optional<MedicalCenter?>?

    fun selectAllMedicalCenters(): MutableList<MedicalCenter?>?

    fun updateMedicalCenter(medicalCenter: MedicalCenter?): MedicalCenter?

    fun deleteMedicalCenter(centerId: Int): Boolean
}