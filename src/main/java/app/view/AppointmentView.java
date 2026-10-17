package app.view;

import app.service.inputport.AppointmentService;
import java.util.Scanner;

public class AppointmentView {

    Scanner sc = new Scanner(System.in);

    private final AppointmentService appointmentService;

    public AppointmentView(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    public void createAppointment() {
        System.out.println("Ingrese el id de la cita medica");
        Integer appointmentId = sc.nextInt();
        sc.nextLine();
        // appointmentService.createAppointment(appointmentId, ...);
    }
}
