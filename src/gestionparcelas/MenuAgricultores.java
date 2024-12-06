package gestionparcelas;

import ListasTemplates.*;
import static gestionparcelas.GestionParcelas.guardarAgricultoresEnArchivo;
import static gestionparcelas.GestionParcelas.leerCadena;
import static gestionparcelas.GestionParcelas.leerEntero;
import static gestionparcelas.GestionParcelas.agricultores;

public class MenuAgricultores {

    // Método principal del menú de agricultores
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
                case 1 ->
                    altaAgricultor();
                case 2 ->
                    bajaAgricultor();
                case 3 ->
                    modificarAgricultor();
                case 4 ->
                    listarAgricultores();
                case 5 ->
                    salir = true;
                default ->
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

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
        guardarAgricultoresEnArchivo();
        System.out.println("Agricultor añadido correctamente con ID: " + nuevoId);

    }

    private static void bajaAgricultor() {
        System.out.println("Baja de parcela:");
        int id = leerEntero("ID de la parcela a eliminar: ");
        Agricultor temp = new Agricultor(id, "", "");

        if (agricultores.borrarElemento(temp)) {
            guardarAgricultoresEnArchivo();
            System.out.println("Agricultor eliminada correctamente.");
        } else {
            System.out.println("Agricultor no encontrada.");
        }
    }

    private static void modificarAgricultor() {
        int id = leerEntero("ID del agricultor a modificar: ");

        Nodo<Agricultor> nodo = agricultores.getNodoInicial();
        while (nodo != null) {
            Agricultor agricultor = nodo.getInf();
            if (agricultor.getId() == id) {
                agricultor.setNombre(leerCadena("Nuevo nombre: "));
                agricultor.setPassword(leerCadena("Nueva password: "));
                System.out.println("Agricultor modificado correctamente.");
                guardarAgricultoresEnArchivo();
                return;
            }
            nodo = nodo.getSig();
        }

        System.out.println("Agricultor no encontrado.");
    }

    public static void listarAgricultores() {
        System.out.println("\n--- Listado de Agricultores ---");
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
