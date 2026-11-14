package app.view;

import app.service.inputport.AppointmentServiceInterface;
import java.util.Scanner;

public class AppointmentView {

    Scanner sc = new Scanner(System.in);

    private final AppointmentServiceInterface appointmentService;

    public AppointmentView(AppointmentServiceInterface appointmentService) {
        this.appointmentService = appointmentService;
    }

    public void createAppointment() {
        System.out.println("Ingrese el id de la cita medica");
        Integer appointmentId = sc.nextInt();
        sc.nextLine();
        // appointmentService.createAppointment(appointmentId, ...);
    }
}
