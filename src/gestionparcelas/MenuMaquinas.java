package gestionparcelas;

import ListasTemplates.*;
import static gestionparcelas.GestionParcelas.guardarMaquinasEnArchivo;
import static gestionparcelas.GestionParcelas.leerCadena;
import static gestionparcelas.GestionParcelas.leerEntero;
import static gestionparcelas.GestionParcelas.maquinas;

/**
 *
 * @author Eduardo Olalde
 */
public class MenuMaquinas {

    // Método principal del menú de máquinas
    public static void mostrarMenu() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Gestión de Máquinas ---");
            System.out.println("1. Alta de Máquina");
            System.out.println("2. Baja de Máquina");
            System.out.println("3. Modificar Máquina");
            System.out.println("4. Listar Máquinas");
            System.out.println("5. Listar Máquinas Libres");
            System.out.println("6. Volver al menú principal");

            int opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1 ->
                    altaMaquina();
                case 2 ->
                    bajaMaquina();
                case 3 ->
                    modificarMaquina();
                case 4 ->
                    listarMaquinas();
                case 5 ->
                    listarMaquinasLibres();
                case 6 ->
                    salir = true;
                default ->
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private static void altaMaquina() {
        // Generar un nuevo ID automático tomando el último ID + 1
        int nuevoId = 1;
        Nodo<Maquina> nodo = maquinas.getNodoInicial();
        if (nodo != null) {
            nuevoId = nodo.getInf().getId() + 1;
        }

        System.out.println("Alta de máquina:");
        String tipo = leerCadena("Tipo de máquina: ");
        String modelo = leerCadena("Modelo de máquina: ");
        String estado = "libre"; // Asignar estado inicial como "libre"

        Maquina nuevaMaquina = new Maquina(nuevoId, tipo, modelo, estado);

        maquinas.add(nuevaMaquina);
        guardarMaquinasEnArchivo();

        System.out.println("Máquina añadida correctamente con ID: " + nuevoId);
    }

    private static void bajaMaquina() {
        System.out.println("Baja de máquina:");
        int id = leerEntero("ID de la máquina a eliminar: ");
        Maquina temp = new Maquina(id, "", "", "");

        if (maquinas.borrarElemento(temp)) {
            guardarMaquinasEnArchivo();
            System.out.println("Máquina eliminada correctamente.");
        } else {
            System.out.println("Máquina no encontrada.");
        }
    }

    private static void modificarMaquina() {
        int id = leerEntero("ID de la máquina a modificar: ");

        Nodo<Maquina> nodo = maquinas.getNodoInicial();
        while (nodo != null) {
            Maquina maquina = nodo.getInf();
            if (maquina.getId() == id) {
                maquina.setTipo(leerCadena("Nuevo tipo: "));
                maquina.setModelo(leerCadena("Nuevo modelo: "));
                maquina.setEstado(leerCadena("Nuevo estado: "));
                System.out.println("Máquina modificada correctamente.");
                guardarMaquinasEnArchivo();
                return;
            }
            nodo = nodo.getSig();
        }
        System.out.println("Máquina no encontrada.");
    }

    public static void listarMaquinas() {
        System.out.println("\nMáquinas:");
        Nodo<Maquina> nodo = maquinas.getNodoInicial();
        while (nodo != null) {
            System.out.println(nodo.getInf() + "\n");
            nodo = nodo.getSig();
        }
    }

    private static void listarMaquinasLibres() {
        // Crear la cola de máquinas libres
        Cola<Maquina> maquinasLibres = new Cola<>();

        // Recorrer la lista de máquinas y encolar las que están libres
        Nodo<Maquina> nodoMaquina = maquinas.getNodoInicial();
        while (nodoMaquina != null) {
            Maquina maquina = nodoMaquina.getInf();
            if ("libre".equals(maquina.getEstado())) {
                maquinasLibres.encolar(maquina);
            }
            nodoMaquina = nodoMaquina.getSig();
        }

        // Mostrar las máquinas libres
        System.out.println("Máquinas libres:");
        Nodo<Maquina> nodo = maquinasLibres.getNodoInicio();
        while (nodo != null) {
            Maquina maquina = nodo.getInf();
            System.out.println(maquina.getId() + ". " + maquina.getModelo());
            nodo = nodo.getSig();
        }

        if (maquinasLibres.esVacia()) {
            System.out.println("No hay máquinas libres.");
        }
    }

    public static Maquina buscarMaquinaPorId(int id) {
        Nodo<Maquina> nodo = maquinas.getNodoInicial();
        while (nodo != null) {
            if (nodo.getInf().getId() == id) {
                return nodo.getInf();
            }
            nodo = nodo.getSig();
        }
        return null; // Retorna null si no encuentra la máquina
    }
}
