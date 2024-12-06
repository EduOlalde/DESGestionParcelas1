package gestionparcelas;

import ListasTemplates.*;
import static gestionparcelas.GestionFicheros.guardarMaquinasEnArchivo;
import static gestionparcelas.GestionParcelas.leerCadena;
import static gestionparcelas.GestionParcelas.leerEntero;
import static gestionparcelas.GestionParcelas.maquinas;
import gestionparcelas.Maquina.Estado;

/**
 * Clase que gestiona el menú de operaciones relacionadas con las máquinas.
 * Permite al usuario realizar acciones como alta, baja, modificación, listado
 * de máquinas y listado de máquinas libres. También gestiona la búsqueda de
 * máquinas por su ID y guarda los datos en archivos.
 *
 * @autor Eduardo Olalde
 */
public class MenuMaquinas {

    /**
     * Muestra el menú de gestión de máquinas y permite al usuario seleccionar
     * entre varias opciones: alta, baja, modificación, listado de máquinas,
     * listado de máquinas libres o volver al menú principal.
     */
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

    /**
     * Permite dar de alta una nueva máquina. Se solicita el tipo y modelo de la
     * máquina, y se asigna un estado inicial como "libre". La máquina se añade
     * a la lista de máquinas y la lista se guarda en el archivo
     * correspondiente.
     */
    private static void altaMaquina() {
        // Generar un nuevo ID automático tomando el último ID + 1
        int nuevoId = 1;
        Iterador<Maquina> iter = new Iterador<>(maquinas);
        while (iter.hayElemento()) {
            iter.next();  // Avanzamos hasta el final
        }

        if (iter.hayElemento()) {
            nuevoId = iter.dameValor().getId() + 1;
        }

        System.out.println("Alta de máquina:");
        String tipo = leerCadena("Tipo de máquina: ");
        String modelo = leerCadena("Modelo de máquina: ");
        Estado estado = Estado.libre; // Asignar estado inicial como "libre"

        Maquina nuevaMaquina = new Maquina(nuevoId, tipo, modelo, estado);

        maquinas.add(nuevaMaquina);
        guardarMaquinasEnArchivo(maquinas);

        System.out.println("Máquina añadida correctamente con ID: " + nuevoId);
    }

    /**
     * Permite eliminar una máquina existente. Se solicita el ID de la máquina a
     * eliminar y, si se encuentra en la lista, se elimina. La lista se guarda
     * después de la eliminación.
     */
    private static void bajaMaquina() {
        System.out.println("Baja de máquina:");
        int id = leerEntero("ID de la máquina a eliminar: ");
        Maquina temp = new Maquina(id, "", "", Estado.libre);

        if (maquinas.borrarElemento(temp)) {
            guardarMaquinasEnArchivo(maquinas);
            System.out.println("Máquina eliminada correctamente.");
        } else {
            System.out.println("Máquina no encontrada.");
        }
    }

    /**
     * Permite modificar los datos de una máquina existente. Se solicita el ID
     * de la máquina a modificar, y si se encuentra, se actualizan los campos
     * como el tipo, modelo y estado. La lista de máquinas se guarda después de
     * la modificación.
     */
    private static void modificarMaquina() {
        int id = leerEntero("ID de la máquina a modificar: ");

        Iterador<Maquina> iter = new Iterador<>(maquinas);
        while (iter.hayElemento()) {
            Maquina maquina = iter.dameValor();
            if (maquina.getId() == id) {
                maquina.setTipo(leerCadena("Nuevo tipo: "));
                maquina.setModelo(leerCadena("Nuevo modelo: "));
                System.out.println("Estados disponibles: ");
                for (Estado estado : Estado.values()) {
                    System.out.println("- " + estado.name());
                }

                // Leer y validar el nuevo estado
                while (true) {
                    String estadoStr = leerCadena("Nuevo estado: ");
                    try {
                        Estado nuevoEstado = Estado.valueOf(estadoStr.toLowerCase());
                        maquina.setEstado(nuevoEstado);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Estado no válido. Intente nuevamente.");
                    }
                }

                System.out.println("Máquina modificada correctamente.");
                guardarMaquinasEnArchivo(maquinas);
                return;
            }
            iter.next();
        }
        System.out.println("Máquina no encontrada.");
    }

    /**
     * Muestra un listado de todas las máquinas registradas. Si no hay máquinas
     * en la lista, se informa al usuario de que no hay datos disponibles.
     */
    public static void listarMaquinas() {
        System.out.println("\n--- Máquinas ---");
        Iterador<Maquina> iter = new Iterador<>(maquinas);
        if (!iter.hayElemento()) {
            System.out.println("No hay máquinas registradas.");
            return;
        }

        while (iter.hayElemento()) {
            System.out.println(iter.dameValor().toString());
            iter.next();
        }
    }

    /**
     * Muestra un listado de las máquinas que están libres. Si no hay máquinas
     * libres, se muestra un mensaje indicándolo.
     *
     * @return `true` si hay máquinas libres, `false` si no.
     */
    public static boolean listarMaquinasLibres() {
        boolean hayLibre = true;
        // Crear la cola de máquinas libres
        Cola<Maquina> maquinasLibres = new Cola<>();

        // Recorrer la lista de máquinas y encolar las que están libres
        Iterador<Maquina> iter = new Iterador<>(maquinas);
        while (iter.hayElemento()) {
            Maquina maquina = iter.dameValor();
            if (maquina.getEstado() == Estado.libre) {
                maquinasLibres.encolar(maquina);
            }
            iter.next();
        }

        // Mostrar las máquinas libres
        System.out.println("\n--- Máquinas libres ---");
        Nodo<Maquina> nodo = maquinasLibres.getNodoInicio();
        while (nodo != null) {
            Maquina maquina = nodo.getInf();
            System.out.println("ID: " + maquina.getId() + ", tipo: "
                    + maquina.getTipo() + ", modelo: " + maquina.getModelo());
            nodo = nodo.getSig();
        }

        if (maquinasLibres.esVacia()) {
            System.out.println("No hay máquinas libres.");
            hayLibre = false;
        }
        return hayLibre;
    }

    /**
     * Busca una máquina por su ID. Si encuentra una máquina con el ID dado, la
     * retorna. Si no la encuentra, retorna `null`.
     *
     * @param id El ID de la máquina a buscar.
     * @return La máquina encontrada o `null` si no la encuentra.
     */
    public static Maquina buscarMaquinaPorId(int id) {
        Iterador<Maquina> iter = new Iterador<>(maquinas);
        while (iter.hayElemento()) {
            Maquina maquina = iter.dameValor();
            if (maquina.getId() == id) {
                return maquina;
            }
            iter.next();
        }
        return null; // Retorna null si no encuentra la máquina
    }
}
