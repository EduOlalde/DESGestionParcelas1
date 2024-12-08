package gestionparcelas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Clase de utilidad para manejar la entrada de datos desde la consola.
 * Proporciona métodos para leer diferentes tipos de datos, como enteros,
 * cadenas, fechas y números decimales.
 *
 * <p>
 * Incluye validación básica para garantizar que los valores ingresados sean del
 * tipo esperado.</p>
 *
 * @author Eduardo Olalde
 */
public class EntradaDatos {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Lee un valor entero desde la entrada estándar (teclado).
     *
     * @param mensaje El mensaje que se mostrará al usuario para solicitar la
     * entrada.
     * @return El valor entero ingresado por el usuario.
     */
    public static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    /**
     * Lee una cadena de texto desde la entrada estándar (teclado).
     *
     * @param mensaje El mensaje que se mostrará al usuario para solicitar la
     * entrada.
     * @return La cadena de texto ingresada por el usuario.
     */
    public static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    /**
     * Lee una fecha en formato "yyyy-MM-dd" desde la entrada estándar
     * (teclado). El formato de la fecha es validado, y el usuario puede volver
     * a intentarlo si la fecha es incorrecta.
     *
     * @param mensaje El mensaje que se mostrará al usuario para solicitar la
     * fecha.
     * @return La fecha ingresada por el usuario como un objeto LocalDate.
     */
    public static LocalDate leerFecha(String mensaje) {
        LocalDate fecha = null;

        while (fecha == null) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();

            try {
                // Intentar parsear la entrada al formato LocalDate
                fecha = LocalDate.parse(entrada, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                System.out.println("Formato incorrecto. Use el formato yyyy-MM-dd.");
            }
        }
        return fecha;
    }

    /**
     * Lee un número decimal (real) desde la entrada estándar (teclado).
     *
     * @param mensaje El mensaje que se mostrará al usuario para solicitar el
     * número real.
     * @return El número decimal ingresado por el usuario.
     */
    public static double leerReal(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

}
