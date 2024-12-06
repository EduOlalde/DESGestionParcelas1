package gestionparcelas;

import ListasTemplates.*;
import static gestionparcelas.GestionFicheros.guardarParcelasEnArchivo;
import static gestionparcelas.GestionParcelas.leerCadena;
import static gestionparcelas.GestionParcelas.leerEntero;
import static gestionparcelas.GestionParcelas.leerReal;
import static gestionparcelas.GestionParcelas.parcelas;

/**
 * Clase que gestiona el menú de operaciones relacionadas con las parcelas.
 * Permite al usuario realizar acciones como alta, baja, modificación y listado
 * de parcelas. También permite la gestión de datos relacionados con las
 * parcelas mediante operaciones de búsqueda y guardado en archivos.
 *
 * @author Eduardo Olalde
 */
public class MenuParcelas {

    /**
     * Muestra el menú de gestión de parcelas y permite al usuario seleccionar
     * entre varias opciones: alta, baja, modificación, listado o salir.
     */
    public static void mostrarMenu() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Gestión de Parcelas ---");
            System.out.println("1. Alta de Parcela");
            System.out.println("2. Baja de Parcela");
            System.out.println("3. Modificar Parcela");
            System.out.println("4. Listar Parcelas");
            System.out.println("5. Volver al menú principal");

            int opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1 ->
                    altaParcela();
                case 2 ->
                    bajaParcela();
                case 3 ->
                    modificarParcela();
                case 4 ->
                    listarParcelas();
                case 5 ->
                    salir = true;
                default ->
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    /**
     * Permite dar de alta una nueva parcela. Se solicita la información
     * correspondiente, como la ubicación, extensión y cultivo, y se agrega la
     * nueva parcela a la lista. También se guarda la lista de parcelas en el
     * archivo correspondiente.
     */
    private static void altaParcela() {
        // Generar un nuevo ID automático tomando el último ID + 1
        int nuevoId = 1;
        Nodo<Parcela> nodo = parcelas.getNodoInicial();
        if (nodo != null) {
            nuevoId = nodo.getInf().getId() + 1;
        }

        System.out.println("Alta de parcela:");
        Agricultor agricultor = MenuAgricultores.seleccionarAgricultor();
        String ubicacion = leerCadena("Ubicación: ");
        double extension = leerReal("Extensión (en hectáreas): ");
        String cultivo = leerCadena("Cultivo: ");

        // Crear la nueva parcela
        Parcela parcela = new Parcela(nuevoId, agricultor, ubicacion, extension, cultivo);

        parcelas.add(parcela);
        guardarParcelasEnArchivo(parcelas);
        System.out.println("Parcela añadida correctamente.");
    }

    /**
     * Permite eliminar una parcela existente. Se solicita el ID de la parcela a
     * eliminar y, si se encuentra en la lista, se elimina. La lista de parcelas
     * se guarda después de la eliminación.
     */
    private static void bajaParcela() {
        System.out.println("Baja de parcela:");
        int id = leerEntero("ID de la parcela a eliminar: ");
        Parcela temp = new Parcela(id, null, "", 0, "");

        if (parcelas.borrarElemento(temp)) {
            guardarParcelasEnArchivo(parcelas);
            System.out.println("Parcela eliminada correctamente.");
        } else {
            System.out.println("Parcela no encontrada.");
        }
    }

    /**
     * Permite modificar los datos de una parcela existente. Se solicita el ID
     * de la parcela a modificar, y si se encuentra, se actualizan los campos de
     * la parcela. Después, se guarda la lista de parcelas en el archivo.
     */
    private static void modificarParcela() {
        int id = leerEntero("ID de la parcela a modificar: ");

        Nodo<Parcela> nodo = parcelas.getNodoInicial();
        while (nodo != null) {
            Parcela parcela = nodo.getInf();
            if (parcela.getId() == id) {
                parcela.setUbicacion(leerCadena("Nueva ubicación: "));
                parcela.setExtension(leerReal("Nueva extensión (en hectáreas): "));
                parcela.setCultivo(leerCadena("Nuevo cultivo: "));
                System.out.println("Parcela modificada correctamente.");
                guardarParcelasEnArchivo(parcelas);
                return;
            }
            nodo = nodo.getSig();
        }
        System.out.println("Parcela no encontrada.");
    }

    /**
     * Muestra un listado de todas las parcelas registradas. Si no hay parcelas
     * en la lista, se informa al usuario de que no hay datos disponibles.
     */
    public static void listarParcelas() {
        System.out.println("\n--- Parcelas ---");
        Nodo<Parcela> nodo = parcelas.getNodoInicial();
        if (nodo == null) {
            System.out.println("No hay trabajos registrados.");
            return;
        }
        while (nodo != null) {
            System.out.println(nodo.getInf().toString());
            nodo = nodo.getSig();
        }
    }

    /**
     * Busca una parcela por su ID. Si encuentra una parcela con el ID dado, la
     * retorna. Si no, retorna null.
     *
     * @param id El ID de la parcela a buscar.
     * @return La parcela encontrada o null si no se encuentra ninguna parcela
     * con el ID dado.
     */
    public static Parcela buscarParcelaPorId(int id) {
        Nodo<Parcela> nodo = parcelas.getNodoInicial();
        while (nodo != null) {
            if (nodo.getInf().getId() == id) {
                return nodo.getInf();
            }
            nodo = nodo.getSig();
        }
        return null; // Retorna null si no encuentra la parcela
    }

}
