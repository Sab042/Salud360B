package app.view;

import app.domain.Professional;
import app.enums.Speciality;
import app.helpers.ConsoleHelper;
import app.helpers.DateHelper;
import app.service.inputports.ProfessionalServiceInterface;

import java.util.List;

public class ProfessionalView {

    private final ProfessionalServiceInterface professionalService;
    private final ConsoleHelper console;
    private final PersonForm personForm;

    public ProfessionalView(ProfessionalServiceInterface professionalService, ConsoleHelper console) {
        this.professionalService = professionalService;
        this.console = console;
        this.personForm = new PersonForm(console);
    }

    public void showMenu() {
        int option;
        do {
            option = console.menu("MENÚ PROFESIONALES", "Volver al menú principal",
                    "Registrar profesional",
                    "Consultar profesional por documento",
                    "Listar profesionales",
                    "Actualizar profesional",
                    "Eliminar profesional");
            switch (option) {
                case 1 -> console.execute(this::createProfessional);
                case 2 -> console.execute(this::selectProfessionalById);
                case 3 -> console.execute(this::selectAllProfessionals);
                case 4 -> console.execute(this::updateProfessional);
                case 5 -> console.execute(this::deleteProfessional);
                default -> {
                }
            }
        } while (option != 0);
    }

    public void createProfessional() {
        console.title("Registrar profesional");
        PersonForm.Data d = personForm.capture(null);
        Speciality speciality = console.readEnum("Especialidad", Speciality.class);
        String card = console.readText("Tarjeta profesional");
        Professional professional = professionalService.createProfessional(d.id(), d.docType(), d.name(),
                d.lastName(), d.phone(), d.email(), d.address(), d.username(), d.password(), d.birthDate(),
                speciality, card);
        console.success("Profesional registrado.");
        show(professional);
    }

    public void selectProfessionalById() {
        console.title("Consultar profesional");
        show(professionalService.selectProfessionalById(console.readLong("Número de documento")));
    }

    public void selectAllProfessionals() {
        console.title("Listado de profesionales");
        List<Professional> professionals = professionalService.selectAllProfessionals();
        if (professionals.isEmpty()) {
            console.info("No hay profesionales registrados.");
            return;
        }
        professionals.forEach(p -> console.info(String.format("%-13d %-28s %-20s TP: %s",
                p.getId(), p.getFullName(), p.getSpeciality(), p.getProfessionalCard())));
    }

    public void updateProfessional() {
        console.title("Actualizar profesional");
        long id = console.readLong("Número de documento del profesional a actualizar");
        show(professionalService.selectProfessionalById(id));
        console.info("Ingrese los nuevos datos:");
        PersonForm.Data d = personForm.capture(id);
        Speciality speciality = console.readEnum("Especialidad", Speciality.class);
        String card = console.readText("Tarjeta profesional");
        Professional professional = professionalService.updateProfessional(id, d.docType(), d.name(),
                d.lastName(), d.phone(), d.email(), d.address(), d.username(), d.password(), d.birthDate(),
                speciality, card);
        console.success("Profesional actualizado.");
        show(professional);
    }

    public void deleteProfessional() {
        console.title("Eliminar profesional");
        long id = console.readLong("Número de documento del profesional a eliminar");
        show(professionalService.selectProfessionalById(id));
        if (console.readYesNo("¿Confirma la eliminación?")) {
            professionalService.deleteProfessional(id);
            console.success("Profesional eliminado.");
        } else {
            console.info("Operación cancelada.");
        }
    }

    private void show(Professional p) {
        console.info("Documento   : " + p.getDocType() + " " + p.getId());
        console.info("Nombre      : " + p.getFullName() + " (" + p.getAge() + " años)");
        console.info("Teléfono    : " + p.getPhone());
        console.info("Correo      : " + p.getEmail());
        console.info("Dirección   : " + p.getAddress());
        console.info("Usuario     : " + p.getUsername());
        console.info("Nacimiento  : " + DateHelper.format(p.getBirthDate()));
        console.info("Especialidad: " + p.getSpeciality());
        console.info("Tarj. prof. : " + p.getProfessionalCard());
    }
}
