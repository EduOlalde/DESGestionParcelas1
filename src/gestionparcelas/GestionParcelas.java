package gestionparcelas;

import ListasTemplates.*;
import static gestionparcelas.GestionFicheros.*;
import static gestionparcelas.EntradaDatos.*;

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
    public static Cola<Maquina> mArarLibres = new Cola<>();
    public static Cola<Maquina> mCosecharLibres = new Cola<>();
    public static Cola<Maquina> mSembrarLibres = new Cola<>();
    public static Cola<Maquina> mFumigarLibres = new Cola<>();

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
        // Encola las máquinas disponibles en sus respectivas colas
        MenuMaquinas.encolarMaquinasLibres(maquinas, mArarLibres, Maquina.TipoTrabajo.arar);
        MenuMaquinas.encolarMaquinasLibres(maquinas, mCosecharLibres, Maquina.TipoTrabajo.cosechar);
        MenuMaquinas.encolarMaquinasLibres(maquinas, mSembrarLibres, Maquina.TipoTrabajo.sembrar);
        MenuMaquinas.encolarMaquinasLibres(maquinas, mFumigarLibres, Maquina.TipoTrabajo.fumigar);

        // Menú principal
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1:
                    MenuAgricultores.mostrarMenu();
                    break;
                case 2:
                    MenuMaquinas.mostrarMenu();
                    break;
                case 3:
                    MenuParcelas.mostrarMenu();
                    break;
                case 4:
                    MenuTrabajos.mostrarMenu();
                    break;
                case 5:
                    listarDatos();
                    break;
                case 6: 
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
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
