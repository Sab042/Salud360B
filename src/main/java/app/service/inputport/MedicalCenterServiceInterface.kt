package app.service.inputports

import app.domain.MedicalCenter
import app.enums.CenterType

interface MedicalCenterServiceInterface {
    fun createMedicalCenter(
        name: String?, centerType: CenterType?, address: String?, city: String?,
        isAvailable: Boolean
    ): MedicalCenter?

    fun selectMedicalCenterById(centerId: Int): MedicalCenter?

    fun selectAllMedicalCenters(): MutableList<MedicalCenter?>?

    fun updateMedicalCenter(
        centerId: Int, name: String?, centerType: CenterType?, address: String?,
        city: String?, isAvailable: Boolean
    ): MedicalCenter?

    fun deleteMedicalCenter(centerId: Int)

    fun assignProfessional(centerId: Int, professionalId: Long): MedicalCenter?

    fun removeProfessional(centerId: Int, professionalId: Long): MedicalCenter?
}