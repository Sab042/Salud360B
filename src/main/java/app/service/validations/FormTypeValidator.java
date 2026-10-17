package app.service.validations;

import java.util.InputMismatchException;
import java.util.Scanner;

public class FormTypeValidator {

    static Scanner sc = new Scanner(System.in);

    public static int intValidator(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                int value = sc.nextInt();
                sc.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Solo se admiten numeros enteros. " + e);
                sc.nextLine(); // Limpia el buffer para evitar un ciclo infinito
            }
        }
    }

    public static String stringValidator(String prompt) {
        while (true) {
            System.out.println(prompt);
            String value = sc.nextLine();
            if (value != null && !value.trim().isEmpty()) {
                return value;
            } else {
                System.out.println("El campo no puede estar vacio. Intente nuevamente.");
            }
        }
    }
}