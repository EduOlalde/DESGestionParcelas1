package gestionparcelas;

import ListasTemplates.*;
import static gestionparcelas.GestionFicheros.guardarTrabajosEnArchivo;
import static gestionparcelas.GestionParcelas.leerCadena;
import static gestionparcelas.GestionParcelas.leerEntero;
import static gestionparcelas.GestionParcelas.leerFecha;
import static gestionparcelas.GestionParcelas.parcelas;
import static gestionparcelas.GestionParcelas.trabajos;
import static gestionparcelas.GestionParcelas.maquinas;
import static gestionparcelas.MenuMaquinas.buscarMaquinaPorId;
import static gestionparcelas.MenuParcelas.buscarParcelaPorId;
import gestionparcelas.Maquina.Estado;
import java.time.LocalDate;

/**
 * Esta clase gestiona las operaciones relacionadas con los trabajos en el
 * sistema. Permite asignar, finalizar y listar los trabajos realizados en las
 * parcelas, así como gestionar el estado de las máquinas asignadas a los
 * trabajos.
 * <p>
 * El menú interactivo permite al usuario seleccionar una opción para asignar un
 * trabajo, finalizar un trabajo, listar los trabajos realizados o volver al
 * menú principal.
 * </p>
 *
 * @author Eduardo Olalde
 */
public class MenuTrabajos {

    /**
     * Muestra el menú principal de gestión de trabajos y permite al usuario
     * seleccionar una opción para asignar, finalizar o listar trabajos. También
     * permite regresar al menú principal.
     */
    public static void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú de Gestión de Trabajos ---");
            System.out.println("1. Asignar trabajo");
            System.out.println("2. Finalizar trabajo");
            System.out.println("3. Listar trabajos");
            System.out.println("4. Volver al menú principal");
            int opcionTrabajo = leerEntero("Selecciona una opción para gestionar los trabajos: ");

            switch (opcionTrabajo) {
                case 1 ->
                    asignarTrabajo();
                case 2 ->
                    finalizarTrabajo();
                case 3 ->
                    listarTrabajos();
                case 4 ->
                    salir = true;
                default ->
                    System.out.println("Opción no válida. Por favor, selecciona una opción válida.");
            }
        }
    }

    /**
     * Asigna un trabajo a una máquina y una parcela. Solicita al usuario los
     * datos necesarios como el tipo de trabajo, la parcela y la máquina, y
     * guarda la información del trabajo. Además, verifica que haya máquinas
     * libres para realizar la asignación.
     */
    private static void asignarTrabajo() {
        // Comprueba si hay máquinas libres o no permite la asignación
        if (!MenuMaquinas.listarMaquinasLibres()) {
            return;
        }

        int maquinaId = leerEntero("Ingrese el ID de la máquina: ");
        Maquina maquinaSeleccionada = buscarMaquinaPorId(maquinaId);

        if (maquinaSeleccionada == null) {
            System.out.println("Máquina no encontrada. Inténtelo de nuevo.");
            return;
        } else {
            maquinaSeleccionada.setEstado(Estado.asignada);
        }

        // Mostrar las parcelas disponibles
        System.out.println("\nSeleccione una parcela:");
        Iterador<Parcela> iteradorParcelas = new Iterador<>(parcelas);
        while (iteradorParcelas.hasNext()) {
            Parcela parcela = iteradorParcelas.dameValor();
            System.out.println(parcela.getId() + ". " + parcela.getUbicacion());
            iteradorParcelas.next();
        }
        int parcelaId = leerEntero("Ingrese el ID de la parcela: ");
        Parcela parcelaSeleccionada = buscarParcelaPorId(parcelaId);

        if (parcelaSeleccionada == null) {
            System.out.println("Parcela no encontrada.");
            return;
        }

        // Mostrar los tipos de trabajo disponibles
        System.out.println("\nSeleccione el tipo de trabajo:");
        for (Trabajo.tipoAtrabajo tipo : Trabajo.tipoAtrabajo.values()) {
            System.out.println(tipo.ordinal() + 1 + ". " + tipo.name()); // Mostrar las opciones disponibles
        }

        // Leer la opción del tipo de trabajo
        int tipoTrabajoSeleccionado = leerEntero("Ingrese el número del tipo de trabajo: ");

        // Validar que la opción esté en el rango de tipos disponibles
        if (tipoTrabajoSeleccionado < 1 || tipoTrabajoSeleccionado > Trabajo.tipoAtrabajo.values().length) {
            System.out.println("Opción no válida. Inténtelo de nuevo.");
            return;
        }

        // Convertir la selección en un valor del enum
        Trabajo.tipoAtrabajo tipoTrabajo = Trabajo.tipoAtrabajo.values()[tipoTrabajoSeleccionado - 1];

        // Solicitar fecha de inicio
        LocalDate fechaInicio = leerFecha("Ingrese la fecha de inicio (formato: yyyy-MM-dd): ");

        // Calcular el ID del nuevo trabajo (basado en la cantidad actual de trabajos)
        int idTrabajo = 1;
        Iterador<Trabajo> iteradorTrabajos = new Iterador<>(trabajos);
        while (iteradorTrabajos.hasNext()) {
            idTrabajo++;
            iteradorTrabajos.next();
        }

        // Crear el objeto Trabajo con el constructor actualizado
        Trabajo trabajo = new Trabajo(idTrabajo, parcelaSeleccionada, maquinaSeleccionada, tipoTrabajo, fechaInicio, null);

        // Agregar el trabajo a la lista
        trabajos.add(trabajo);

        // Mostrar información del trabajo creado
        System.out.println("Trabajo creado:");
        System.out.println(trabajo.toString());

        // Guardar trabajos en el archivo
        guardarTrabajosEnArchivo(trabajos);
        GestionFicheros.guardarMaquinasEnArchivo(maquinas);
    }

    /**
     * Finaliza un trabajo ya asignado, solicitando la fecha de finalización.
     * Además, libera la máquina que estaba asignada al trabajo.
     *
     * @see Trabajo#finalizarTrabajo(LocalDate)
     */
    private static void finalizarTrabajo() {
        // Solicitar al usuario el ID del trabajo que desea finalizar
        int idTrabajo = leerEntero("Ingrese el ID del trabajo a finalizar: ");

        // Buscar el trabajo en la lista de trabajos
        Iterador<Trabajo> iteradorTrabajos = new Iterador<>(trabajos);
        boolean trabajoEncontrado = false;

        while (iteradorTrabajos.hasNext()) {
            Trabajo trabajo = iteradorTrabajos.dameValor();

            // Verificar si el trabajo tiene el ID proporcionado
            if (trabajo.getId() == idTrabajo) {
                trabajoEncontrado = true;

                LocalDate fechaFin = leerFecha("Ingrese la fecha de fin (formato: yyyy-MM-dd):");

                // Validar y asignar la fecha de fin
                if (fechaFin != null) {
                    trabajo.setFechaFin(fechaFin); // Asignar la fecha de fin al trabajo
                    trabajo.getMaquina().setEstado(Estado.libre); // Libera la máquina usada
                    System.out.println("Trabajo finalizado con éxito.");
                } else {
                    System.out.println("Fecha de fin no válida.");
                }

                break;
            }

            iteradorTrabajos.next();
        }

        if (!trabajoEncontrado) {
            System.out.println("Trabajo con ID " + idTrabajo + " no encontrado.");
        }

        // Guardar trabajos en el archivo después de modificar el estado
        guardarTrabajosEnArchivo(trabajos);
        GestionFicheros.guardarMaquinasEnArchivo(maquinas);
    }

    /**
     * Muestra la lista de todos los trabajos registrados en el sistema,
     * incluyendo su estado. Si no hay trabajos registrados, muestra un mensaje
     * indicando que no existen trabajos.
     */
    public static void listarTrabajos() {
        System.out.println("\n--- Trabajos ---");

        // Recorrer la lista de trabajos y mostrar su estado usando iterador
        Iterador<Trabajo> iteradorTrabajos = new Iterador<>(trabajos);

        if (!iteradorTrabajos.hayElemento()) {
            System.out.println("No hay trabajos registrados.");
            return;
        }

        System.out.println("Lista de trabajos:");
        while (iteradorTrabajos.hasNext()) {
            Trabajo trabajo = iteradorTrabajos.dameValor();
            String estado = (trabajo.getFechaFin() == null) ? "Asignado" : "Terminado";
            System.out.println("Trabajo ID: " + trabajo.getId()
                    + " | Parcela: " + trabajo.getParcela().getUbicacion()
                    + " | Tipo: " + trabajo.getTipo()
                    + " | Estado: " + estado);
            iteradorTrabajos.next();
        }
    }
}
