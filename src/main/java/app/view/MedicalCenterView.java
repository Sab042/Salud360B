package app.view;

import app.domain.MedicalCenter;
import app.enums.CenterType;
import app.helpers.ConsoleHelper;
import app.service.inputports.MedicalCenterServiceInterface;

import java.util.List;

public class MedicalCenterView {

    private final MedicalCenterServiceInterface medicalCenterService;
    private final ConsoleHelper console;

    public MedicalCenterView(MedicalCenterServiceInterface medicalCenterService, ConsoleHelper console) {
        this.medicalCenterService = medicalCenterService;
        this.console = console;
    }

    public void showMenu() {
        int option;
        do {
            option = console.menu("MENÚ CENTROS MÉDICOS", "Volver al menú principal",
                    "Registrar centro médico",
                    "Consultar centro por id",
                    "Listar centros",
                    "Actualizar centro",
                    "Eliminar centro",
                    "Asignar profesional a un centro",
                    "Retirar profesional de un centro");
            switch (option) {
                case 1 -> console.execute(this::createMedicalCenter);
                case 2 -> console.execute(this::selectMedicalCenterById);
                case 3 -> console.execute(this::selectAllMedicalCenters);
                case 4 -> console.execute(this::updateMedicalCenter);
                case 5 -> console.execute(this::deleteMedicalCenter);
                case 6 -> console.execute(this::assignProfessional);
                case 7 -> console.execute(this::removeProfessional);
                default -> {
                }
            }
        } while (option != 0);
    }

    public void createMedicalCenter() {
        console.title("Registrar centro médico");
        String name = console.readText("Nombre del centro");
        CenterType type = console.readEnum("Tipo de centro", CenterType.class);
        String address = console.readText("Dirección");
        String city = console.readText("Ciudad");
        boolean available = console.readYesNo("¿Está disponible para agendar citas?");
        MedicalCenter center = medicalCenterService.createMedicalCenter(name, type, address, city, available);
        console.success("Centro médico registrado con id " + center.getCenterId() + ".");
        show(center);
    }

    public void selectMedicalCenterById() {
        console.title("Consultar centro médico");
        show(medicalCenterService.selectMedicalCenterById(console.readInt("Id del centro")));
    }

    public void selectAllMedicalCenters() {
        console.title("Listado de centros médicos");
        List<MedicalCenter> centers = medicalCenterService.selectAllMedicalCenters();
        if (centers.isEmpty()) {
            console.info("No hay centros médicos registrados.");
            return;
        }
        centers.forEach(c -> console.info(String.format("%-4d %-28s %-13s %-14s %-12s %d profesional(es)",
                c.getCenterId(), c.getName(), c.getCenterType(), c.getCity(),
                c.isAvailable() ? "Disponible" : "No disponible", c.getProfessionals().size())));
    }

    public void updateMedicalCenter() {
        console.title("Actualizar centro médico");
        int id = console.readInt("Id del centro a actualizar");
        show(medicalCenterService.selectMedicalCenterById(id));
        console.info("Ingrese los nuevos datos:");
        String name = console.readText("Nombre del centro");
        CenterType type = console.readEnum("Tipo de centro", CenterType.class);
        String address = console.readText("Dirección");
        String city = console.readText("Ciudad");
        boolean available = console.readYesNo("¿Está disponible para agendar citas?");
        show(medicalCenterService.updateMedicalCenter(id, name, type, address, city, available));
        console.success("Centro médico actualizado.");
    }

    public void deleteMedicalCenter() {
        console.title("Eliminar centro médico");
        int id = console.readInt("Id del centro a eliminar");
        show(medicalCenterService.selectMedicalCenterById(id));
        if (console.readYesNo("¿Confirma la eliminación?")) {
            medicalCenterService.deleteMedicalCenter(id);
            console.success("Centro médico eliminado.");
        } else {
            console.info("Operación cancelada.");
        }
    }

    public void assignProfessional() {
        console.title("Asignar profesional a un centro");
        int centerId = console.readInt("Id del centro");
        long professionalId = console.readLong("Documento del profesional");
        show(medicalCenterService.assignProfessional(centerId, professionalId));
        console.success("Profesional asignado.");
    }

    public void removeProfessional() {
        console.title("Retirar profesional de un centro");
        int centerId = console.readInt("Id del centro");
        long professionalId = console.readLong("Documento del profesional");
        show(medicalCenterService.removeProfessional(centerId, professionalId));
        console.success("Profesional retirado.");
    }

    private void show(MedicalCenter c) {
        console.info("Id         : " + c.getCenterId());
        console.info("Nombre     : " + c.getName());
        console.info("Tipo       : " + c.getCenterType());
        console.info("Dirección  : " + c.getAddress() + ", " + c.getCity());
        console.info("Disponible : " + (c.isAvailable() ? "Sí" : "No"));
        if (c.getProfessionals().isEmpty()) {
            console.info("Profesionales: (ninguno)");
        } else {
            console.info("Profesionales:");
            c.getProfessionals().forEach(p -> console.info("   - " + p.getId() + " " + p.getFullName() + " (" + p.getSpeciality() + ")"));
        }
    }
}
