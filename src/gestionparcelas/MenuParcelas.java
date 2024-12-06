package gestionparcelas;

import ListasTemplates.*;
import static gestionparcelas.GestionFicheros.guardarParcelasEnArchivo;
import static gestionparcelas.GestionParcelas.leerCadena;
import static gestionparcelas.GestionParcelas.leerEntero;
import static gestionparcelas.GestionParcelas.leerReal;
import static gestionparcelas.GestionParcelas.parcelas;

/**
 *
 * @author Eduardo Olalde
 */
public class MenuParcelas {

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

    public static void listarParcelas() {
        System.out.println("\n--- Parcelas ---");
        Nodo<Parcela> nodo = parcelas.getNodoInicial();
        if (nodo == null) {
            System.out.println("No hay trabajos registrados.");
            return;
        }
        while (nodo != null) {
            System.out.println(nodo.getInf().toString() + "\n");
            nodo = nodo.getSig();
        }
    }

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
