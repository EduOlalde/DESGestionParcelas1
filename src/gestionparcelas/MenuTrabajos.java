package gestionparcelas;

import ListasTemplates.*;
import static gestionparcelas.GestionFicheros.guardarEnArchivo;
import static gestionparcelas.EntradaDatos.*;
import static gestionparcelas.GestionParcelas.*;
import static gestionparcelas.MenuParcelas.buscarParcelaPorId;
import java.time.LocalDate;

/**
 * Clase que gestiona las operaciones relacionadas con los trabajos en el
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
                case 1:
                    asignarTrabajo();
                    break;
                case 2:
                    finalizarTrabajo();
                    break;
                case 3:
                    listarTrabajos();
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, selecciona una opción válida.");
            }
        }
    }

    /**
     * Asigna un trabajo a una máquina disponible utilizando colas estáticas. El
     * trabajo se asigna en función del tipo de trabajo seleccionado y de las
     * máquinas libres disponibles en las colas estáticas correspondientes.
     *
     * @see Trabajo
     * @see Maquina
     * @see Parcela
     */
    private static void asignarTrabajo() {
        // Comprueba si hay máquinas libres o no permite la asignación
        if (!MenuMaquinas.listarMaquinasLibres()) {
            return;
        }

        System.out.println("\nSeleccione el tipo de trabajo:");
        for (Maquina.TipoTrabajo tipo : Maquina.TipoTrabajo.values()) {
            System.out.println(tipo.ordinal() + 1 + ". " + tipo.name());
        }

        int tipoTrabajoSeleccionado = leerEntero("Ingrese el número del tipo de trabajo: ");

        if (tipoTrabajoSeleccionado < 1 || tipoTrabajoSeleccionado > Maquina.TipoTrabajo.values().length) {
            System.out.println("Opción no válida. Inténtelo de nuevo.");
            return;
        }

        // Convertir la selección en un valor del enum TipoTrabajo
        Maquina.TipoTrabajo tipoTrabajo = Maquina.TipoTrabajo.values()[tipoTrabajoSeleccionado - 1];

        // Seleccionar la cola estática correspondiente al tipo de trabajo
        Cola<Maquina> colaMaquinasTipo = null;
        switch (tipoTrabajo) {
            case arar:
                colaMaquinasTipo = mArarLibres;
                break;
            case cosechar:
                colaMaquinasTipo = mCosecharLibres;
                break;
            case sembrar:
                colaMaquinasTipo = mSembrarLibres;
                break;
            case fumigar:
                colaMaquinasTipo = mFumigarLibres;
                break;
            default:
                System.out.println("Tipo de trabajo no reconocido.");
                return;
        }

        // Si la cola está vacía, no hay máquinas disponibles para el tipo de trabajo
        if (colaMaquinasTipo.esVacia()) {
            System.out.println("No hay máquinas libres disponibles para el tipo de trabajo seleccionado.");
            return;
        }

        // Desencolar la primera máquina de la cola y asignarla
        Maquina maquinaSeleccionada = colaMaquinasTipo.desencolar();
        maquinaSeleccionada.setEstado(Maquina.Estado.asignada);

        System.out.println("\nSeleccione una parcela:");
        Iterador<Parcela> iteradorParcelas = new Iterador<>(parcelas);
        while (iteradorParcelas.hayElemento()) {
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

        LocalDate fechaInicio = leerFecha("Ingrese la fecha de inicio (formato: yyyy-MM-dd): ");

        // Generar un nuevo ID automático tomando el mayor ID existente + 1
        int idTrabajo = 1;
        Iterador<Trabajo> iterador = new Iterador<>(trabajos);
        while (iterador.hayElemento()) {
            Trabajo trabajo = iterador.dameValor();
            if (trabajo.getId() >= idTrabajo) {
                idTrabajo = trabajo.getId() + 1;
            }
            iterador.next();
        }

        Trabajo trabajo = new Trabajo(idTrabajo, parcelaSeleccionada, maquinaSeleccionada, tipoTrabajo, fechaInicio, null);

        trabajos.add(trabajo);

        System.out.println("Trabajo creado:");
        System.out.println(trabajo.toString());

        guardarEnArchivo(trabajos, "trabajos");
        guardarEnArchivo(maquinas, "maquinas");
    }

    /**
     * Finaliza un trabajo activo (con fecha de finalización null), solicitando
     * la fecha de finalización. Además, libera la máquina asignada y la encola
     * en su respectiva cola de máquinas libres.
     *
     * @see Trabajo#finalizarTrabajo(LocalDate)
     */
    private static void finalizarTrabajo() {

        System.out.println("\nTrabajos activos:");
        Iterador<Trabajo> iteradorTrabajos = new Iterador<>(trabajos);
        boolean hayTrabajosActivos = false;

        while (iteradorTrabajos.hayElemento()) {
            Trabajo trabajo = iteradorTrabajos.dameValor();

            if (trabajo.getFechaFin() == null) {
                hayTrabajosActivos = true;
                System.out.println("ID: " + trabajo.getId()
                        + " | Parcela: " + trabajo.getParcela().getUbicacion()
                        + " | Máquina: " + trabajo.getMaquina().getId()
                        + " | Tipo: " + trabajo.getTipoTrabajo()
                        + " | Fecha de inicio: " + trabajo.getFechaInicio());
            }
            iteradorTrabajos.next();
        }

        if (!hayTrabajosActivos) {
            System.out.println("No hay trabajos activos para finalizar.");
            return;
        }

        int idTrabajo = leerEntero("Ingrese el ID del trabajo a finalizar: ");

        iteradorTrabajos = new Iterador<>(trabajos); // Reiniciar el iterador
        boolean trabajoEncontrado = false;

        while (iteradorTrabajos.hayElemento()) {
            Trabajo trabajo = iteradorTrabajos.dameValor();

            if (trabajo.getId() == idTrabajo && trabajo.getFechaFin() == null) {
                trabajoEncontrado = true;

                LocalDate fechaFin = leerFecha("Ingrese la fecha de fin (formato: yyyy-MM-dd):");

                if (fechaFin != null) {
                    trabajo.setFechaFin(fechaFin);

                    // Liberar la máquina y encolarla en la cola correspondiente
                    Maquina maquina = trabajo.getMaquina();
                    maquina.setEstado(Maquina.Estado.libre);

                    switch (maquina.getTipoTrabajo()) {
                        case arar:
                            mArarLibres.encolar(maquina);
                            break;
                        case cosechar:
                            mCosecharLibres.encolar(maquina);
                            break;
                        case sembrar:
                            mSembrarLibres.encolar(maquina);
                            break;
                        case fumigar:
                            mFumigarLibres.encolar(maquina);
                            break;
                        default:
                            System.out.println("No se pudo identificar la cola para el tipo de trabajo: " + maquina.getTipoTrabajo());
                            return;
                    }

                    System.out.println("Trabajo finalizado con éxito.");
                } else {
                    System.out.println("Fecha de fin no válida.");
                }
                break;
            }
            iteradorTrabajos.next();
        }

        if (!trabajoEncontrado) {
            System.out.println("Trabajo con ID " + idTrabajo + " no encontrado o ya finalizado.");
        }

        guardarEnArchivo(trabajos, "trabajos");
        guardarEnArchivo(maquinas, "maquinas");
    }

    /**
     * Muestra la lista de todos los trabajos registrados en el sistema,
     * incluyendo su estado. Si no hay trabajos registrados, muestra un mensaje
     * indicando que no existen trabajos.
     */
    public static void listarTrabajos() {
        System.out.println("\n--- Trabajos ---");

        if (trabajos.esVacia()) {
            System.out.println("No hay trabajos registrados.");
            return;
        }

        Iterador<Trabajo> iteradorTrabajos = new Iterador<>(trabajos);

        while (iteradorTrabajos.hayElemento()) {
            Trabajo trabajo = iteradorTrabajos.dameValor();
            String estado = (trabajo.getFechaFin() == null) ? "Asignado" : "Terminado";

            System.out.println("Trabajo ID: " + trabajo.getId()
                    + " | Parcela: " + trabajo.getParcela().getUbicacion()
                    + " | Tipo: " + trabajo.getTipoTrabajo()
                    + " | Estado: " + estado);

            iteradorTrabajos.next();
        }
    }

}
