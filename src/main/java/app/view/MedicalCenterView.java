package app.view;

import app.service.PatientServiceImpl;
import java.util.Scanner;

public class MedicalCenterView {

    Scanner sc = new Scanner(System.in);

    private final PatientServiceImpl patientService;

    public MedicalCenterView(PatientServiceImpl patientServiceImpl) {
        this.patientService = patientServiceImpl;
    }

    public void createMedicalCenter() {
        System.out.println("Ingrese el id de la IPS / Consultorio: ");
    }

    public void selectMedicalCenterById(int id) {

    }

    public void selectAllMedicalCenters() {

    }

    public void updateMedicalCenter() {

    }

    public void deleteMedicalCenter(int id) {

    }
}
