package gestionparcelas;

import ListasTemplates.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import static gestionparcelas.GestionFicheros.*;

/**
 * Clase principal que gestiona la aplicación de gestión de parcelas. Esta clase
 * maneja la carga de datos desde los archivos y el menú principal que permite
 * al usuario gestionar agricultores, máquinas, parcelas y trabajos.
 *
 * La aplicación funciona con cuatro listas estáticas que almacenan los objetos
 * correspondientes a cada entidad: agricultores, máquinas, parcelas y trabajos.
 *
 * @author Eduardo Olalde
 */
public class GestionParcelas {

    // Listas estáticas para manejar los datos
    public static Lista<Agricultor> agricultores = new Lista<>();
    public static Lista<Maquina> maquinas = new Lista<>();
    public static Lista<Parcela> parcelas = new Lista<>();
    public static Lista<Trabajo> trabajos = new Lista<>();
    public static Cola<Maquina> maquinasLibres = new Cola<>();

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Método principal de la aplicación. Carga los datos de agricultores,
     * máquinas, parcelas y trabajos desde los archivos correspondientes y
     * muestra el menú principal para interactuar con el usuario.
     *
     * @param args los argumentos de línea de comandos (no se usan en esta
     * aplicación)
     */
    public static void main(String[] args) {
        // Cargar datos al inicio
        cargarAgricultoresDesdeArchivo(agricultores);
        cargarMaquinasDesdeArchivo(maquinas);
        cargarParcelasDesdeArchivo(parcelas);
        cargarTrabajosDesdeArchivo(trabajos);

        // Menú principal
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1 ->
                    MenuAgricultores.mostrarMenu();
                case 2 ->
                    MenuMaquinas.mostrarMenu();
                case 3 ->
                    MenuParcelas.mostrarMenu();
                case 4 ->
                    MenuTrabajos.mostrarMenu();
                case 5 ->
                    listarDatos();
                case 6 -> {
                    salir = true;
                    System.out.println("¡Hasta luego!");
                }
                default ->
                    System.out.println("Opción no válida.");
            }
        }
    }

    /**
     * Muestra el menú principal con las opciones disponibles para el usuario.
     */
    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Gestionar Agricultores");
        System.out.println("2. Gestionar Máquinas");
        System.out.println("3. Gestionar Parcelas");
        System.out.println("4. Gestionar Trabajos");
        System.out.println("5. Listar todos los datos");
        System.out.println("6. Salir");
    }

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
        Scanner scanner = new Scanner(System.in);

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

    /**
     * Muestra los datos de agricultores, máquinas, parcelas y trabajos. Llama a
     * los métodos correspondientes de los menús de cada entidad.
     */
    private static void listarDatos() {
        System.out.println("\n--- LISTADO ---");

        MenuAgricultores.listarAgricultores();
        MenuMaquinas.listarMaquinas();
        MenuParcelas.listarParcelas();
        MenuTrabajos.listarTrabajos();
    }
}
