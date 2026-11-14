package app;

import app.config.Config;

import java.util.NoSuchElementException;

public class Application {

    public static void main(String[] args) {
        Config config = new Config();
        try {
            config.getMainMenu().show();
        } catch (NoSuchElementException e) {
            // La entrada estándar se cerró (Ctrl+D / fin de archivo)
            System.out.println();
            System.out.println("Entrada finalizada. Hasta pronto.");
        }
    }
}
