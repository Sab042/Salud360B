package app.view;

import app.domain.Appointment;
import app.enums.AppointmentState;
import app.helpers.ConsoleHelper;
import app.helpers.DateHelper;
import app.service.inputports.AppointmentServiceInterface;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentView {

    private final AppointmentServiceInterface appointmentService;
    private final ConsoleHelper console;

    public AppointmentView(AppointmentServiceInterface appointmentService, ConsoleHelper console) {
        this.appointmentService = appointmentService;
        this.console = console;
    }

    public void showMenu() {
        int option;
        do {
            option = console.menu("MENÚ CITAS MÉDICAS", "Volver al menú principal",
                    "Agendar cita",
                    "Consultar cita por id",
                    "Listar todas las citas",
                    "Historial de citas de un paciente",
                    "Agenda de un profesional",
                    "Reprogramar cita",
                    "Confirmar cita",
                    "Completar cita",
                    "Cancelar cita",
                    "Marcar inasistencia (no show)",
                    "Eliminar cita");
            switch (option) {
                case 1 -> console.execute(this::createAppointment);
                case 2 -> console.execute(this::selectAppointmentById);
                case 3 -> console.execute(this::selectAllAppointments);
                case 4 -> console.execute(this::selectAppointmentsByPatientId);
                case 5 -> console.execute(this::selectAppointmentsByProfessionalId);
                case 6 -> console.execute(this::updateAppointment);
                case 7 -> console.execute(() -> changeState(AppointmentState.CONFIRMED));
                case 8 -> console.execute(() -> changeState(AppointmentState.COMPLETED));
                case 9 -> console.execute(() -> changeState(AppointmentState.CANCELLED));
                case 10 -> console.execute(() -> changeState(AppointmentState.NO_SHOW));
                case 11 -> console.execute(this::deleteAppointment);
                default -> {
                }
            }
        } while (option != 0);
    }

    public void createAppointment() {
        console.title("Agendar cita");
        long patientId = console.readLong("Documento del paciente");
        long professionalId = console.readLong("Documento del profesional");
        int centerId = console.readInt("Id del centro médico");
        LocalDateTime init = console.readDateTime("Fecha y hora de inicio");
        LocalDateTime end = console.readDateTime("Fecha y hora de fin");
        String description = console.readText("Motivo de la cita");
        Appointment appointment = appointmentService.createAppointment(patientId, professionalId, centerId,
                init, end, description);
        console.success("Cita agendada con id " + appointment.getAppointmentId() + ".");
        show(appointment);
    }

    public void selectAppointmentById() {
        console.title("Consultar cita");
        show(appointmentService.selectAppointmentById(console.readInt("Id de la cita")));
    }

    public void selectAllAppointments() {
        console.title("Listado de citas");
        list(appointmentService.selectAllAppointments());
    }

    public void selectAppointmentsByPatientId() {
        console.title("Historial de citas del paciente");
        list(appointmentService.selectAppointmentsByPatientId(console.readLong("Documento del paciente")));
    }

    public void selectAppointmentsByProfessionalId() {
        console.title("Agenda del profesional");
        list(appointmentService.selectAppointmentsByProfessionalId(console.readLong("Documento del profesional")));
    }

    public void updateAppointment() {
        console.title("Reprogramar cita");
        int id = console.readInt("Id de la cita");
        show(appointmentService.selectAppointmentById(id));
        console.info("Ingrese los nuevos datos:");
        int centerId = console.readInt("Id del centro médico");
        LocalDateTime init = console.readDateTime("Nueva fecha y hora de inicio");
        LocalDateTime end = console.readDateTime("Nueva fecha y hora de fin");
        String description = console.readText("Motivo de la cita");
        show(appointmentService.updateAppointment(id, centerId, init, end, description));
        console.success("Cita reprogramada (queda en estado " + AppointmentState.SCHEDULED + ").");
    }

    public void changeState(AppointmentState newState) {
        console.title("Cambiar estado de la cita a: " + newState);
        int id = console.readInt("Id de la cita");
        show(appointmentService.changeAppointmentState(id, newState));
        console.success("Estado actualizado a " + newState + ".");
    }

    public void deleteAppointment() {
        console.title("Eliminar cita");
        int id = console.readInt("Id de la cita a eliminar");
        show(appointmentService.selectAppointmentById(id));
        if (console.readYesNo("¿Confirma la eliminación?")) {
            appointmentService.deleteAppointment(id);
            console.success("Cita eliminada.");
        } else {
            console.info("Operación cancelada.");
        }
    }

    private void list(List<Appointment> appointments) {
        if (appointments.isEmpty()) {
            console.info("No hay citas para mostrar.");
            return;
        }
        appointments.forEach(a -> console.info(String.format("#%-3d %s - %s  %-13s Pac: %-24s Prof: %-24s %s",
                a.getAppointmentId(),
                DateHelper.format(a.getDateInit()),
                a.getDateEnd().toLocalTime(),
                a.getAppointmentState(),
                a.getPatient().getFullName(),
                a.getProfessional().getFullName(),
                a.getMedicalCenter().getName())));
    }

    private void show(Appointment a) {
        console.info("Cita #" + a.getAppointmentId() + " - " + a.getAppointmentState());
        console.info("Paciente   : " + a.getPatient().getId() + " " + a.getPatient().getFullName());
        console.info("Profesional: " + a.getProfessional().getId() + " " + a.getProfessional().getFullName()
                + " (" + a.getProfessional().getSpeciality() + ")");
        console.info("Centro     : " + a.getMedicalCenter().getName() + " - " + a.getMedicalCenter().getCity());
        console.info("Inicio     : " + DateHelper.format(a.getDateInit()));
        console.info("Fin        : " + DateHelper.format(a.getDateEnd()) + " (" + a.getDurationMinutes() + " min)");
        console.info("Motivo     : " + a.getDescriptionAppointment());
    }
}
