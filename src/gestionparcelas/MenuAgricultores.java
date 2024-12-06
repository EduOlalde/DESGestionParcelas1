package gestionparcelas;

import ListasTemplates.*;
import static gestionparcelas.GestionFicheros.guardarAgricultoresEnArchivo;
import static gestionparcelas.GestionParcelas.leerCadena;
import static gestionparcelas.GestionParcelas.leerEntero;
import static gestionparcelas.GestionParcelas.agricultores;

/**
 * Clase que gestiona el menú de operaciones relacionadas con los agricultores.
 * Permite al usuario realizar acciones como alta, baja, modificación, listado
 * y selección de agricultores. También gestiona la búsqueda de agricultores
 * por su ID y guarda los datos en archivos.
 * 
 * @author Eduardo Olalde
 */
public class MenuAgricultores {

    /**
     * Muestra el menú de gestión de agricultores y permite al usuario seleccionar
     * entre varias opciones: alta, baja, modificación, listado o volver al menú principal.
     */
    public static void mostrarMenu() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Gestión de Agricultores ---");
            System.out.println("1. Alta de Agricultor");
            System.out.println("2. Baja de Agricultor");
            System.out.println("3. Modificar Agricultor");
            System.out.println("4. Listar Agricultores");
            System.out.println("5. Volver al menú principal");

            int opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1 -> altaAgricultor();
                case 2 -> bajaAgricultor();
                case 3 -> modificarAgricultor();
                case 4 -> listarAgricultores();
                case 5 -> salir = true;
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    /**
     * Permite dar de alta a un nuevo agricultor. Se solicita el nombre y la contraseña,
     * se asigna un ID automáticamente y se guarda el nuevo agricultor en la lista.
     * También se guarda la lista actualizada en el archivo correspondiente.
     */
    private static void altaAgricultor() {
        // Generar un nuevo ID automático tomando el último ID + 1
        int nuevoId = 1;
        Nodo<Agricultor> nodo = agricultores.getNodoInicial();
        if (nodo != null) {
            nuevoId = nodo.getInf().getId() + 1;
        }

        System.out.println("\n--- Alta de Agricultor ---");
        String nombre = leerCadena("Nombre del agricultor: ");
        String password = leerCadena("Password del agricultor: ");

        Agricultor agricultor = new Agricultor(nuevoId, nombre, password);

        agricultores.add(agricultor);
        guardarAgricultoresEnArchivo(agricultores);
        System.out.println("Agricultor añadido correctamente con ID: " + nuevoId);
    }

    /**
     * Permite eliminar un agricultor existente. Se solicita el ID del agricultor
     * a eliminar y, si se encuentra en la lista, se elimina. La lista se guarda
     * después de la eliminación.
     */
    private static void bajaAgricultor() {
        System.out.println("Baja de agricultor:");
        int id = leerEntero("ID del agricultor a eliminar: ");
        Agricultor temp = new Agricultor(id, "", "");

        if (agricultores.borrarElemento(temp)) {
            guardarAgricultoresEnArchivo(agricultores);
            System.out.println("Agricultor eliminado correctamente.");
        } else {
            System.out.println("Agricultor no encontrado.");
        }
    }

    /**
     * Permite modificar los datos de un agricultor existente. Se solicita el ID
     * del agricultor a modificar, y si se encuentra, se actualizan los campos
     * del agricultor. Luego, la lista se guarda en el archivo correspondiente.
     */
    private static void modificarAgricultor() {
        int id = leerEntero("ID del agricultor a modificar: ");

        Nodo<Agricultor> nodo = agricultores.getNodoInicial();
        while (nodo != null) {
            Agricultor agricultor = nodo.getInf();
            if (agricultor.getId() == id) {
                agricultor.setNombre(leerCadena("Nuevo nombre: "));
                agricultor.setPassword(leerCadena("Nueva password: "));
                System.out.println("Agricultor modificado correctamente.");
                guardarAgricultoresEnArchivo(agricultores);
                return;
            }
            nodo = nodo.getSig();
        }

        System.out.println("Agricultor no encontrado.");
    }

    /**
     * Muestra un listado de todos los agricultores registrados. Si no hay agricultores
     * en la lista, se informa al usuario de que no hay datos disponibles.
     */
    public static void listarAgricultores() {
        System.out.println("\n--- Agricultores ---");
        Nodo<Agricultor> nodo = agricultores.getNodoInicial();
        if (nodo == null) {
            System.out.println("No hay agricultores registrados.");
            return;
        }

        while (nodo != null) {
            System.out.println(nodo.getInf());
            nodo = nodo.getSig();
        }
    }

    /**
     * Permite seleccionar un agricultor de la lista de agricultores. Muestra los agricultores
     * registrados y solicita al usuario que ingrese el ID de un agricultor. Si se elige
     * un agricultor válido, se retorna el objeto correspondiente, o `null` si no se selecciona ninguno.
     * 
     * @return El agricultor seleccionado o `null` si no se selecciona ningún agricultor.
     */
    public static Agricultor seleccionarAgricultor() {
        System.out.println("Seleccione un agricultor:");
        Nodo<Agricultor> nodoAgricultor = agricultores.getNodoInicial();
        while (nodoAgricultor != null) {
            Agricultor agricultor = nodoAgricultor.getInf();
            System.out.println(agricultor.getId() + ". " + agricultor.getNombre());
            nodoAgricultor = nodoAgricultor.getSig();
        }
        int agricultorId = leerEntero("Ingrese el ID del agricultor (0 para ninguno): ");
        if (agricultorId == 0) {
            return null; // No se asigna agricultor
        }
        return buscarAgricultorPorId(agricultorId);
    }

    /**
     * Busca un agricultor por su ID. Si encuentra un agricultor con el ID dado,
     * lo retorna. Si no, retorna `null`.
     * 
     * @param id El ID del agricultor a buscar.
     * @return El agricultor encontrado o `null` si no se encuentra el agricultor con el ID dado.
     */
    public static Agricultor buscarAgricultorPorId(int id) {
        Nodo<Agricultor> nodo = agricultores.getNodoInicial();
        while (nodo != null) {
            if (nodo.getInf().getId() == id) {
                return nodo.getInf();
            }
            nodo = nodo.getSig();
        }
        return null; // Retorna null si no encuentra el agricultor
    }
}
