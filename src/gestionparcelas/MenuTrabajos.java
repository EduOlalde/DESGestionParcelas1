package gestionparcelas;

import ListasTemplates.*;
import static gestionparcelas.GestionFicheros.guardarTrabajosEnArchivo;
import static gestionparcelas.GestionFicheros.guardarMaquinasEnArchivo;
import static gestionparcelas.EntradaDatos.*;
import static gestionparcelas.GestionParcelas.*;
import static gestionparcelas.MenuParcelas.buscarParcelaPorId;
import gestionparcelas.Maquina.Estado;
import gestionparcelas.Maquina.TipoTrabajo;
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
     * Asigna un trabajo a una máquina disponible. El trabajo se asigna en
     * función del tipo de trabajo seleccionado y de las máquinas libres
     * disponibles que coincidan con ese tipo. Si hay máquinas disponibles para
     * el tipo de trabajo seleccionado, se asigna la primera máquina de la cola
     * correspondiente.
     *
     * Luego de asignar la máquina, se procede a asociarla con una parcela
     * seleccionada, se establece la fecha de inicio del trabajo y se guarda la
     * información en los archivos correspondientes.
     *
     * El proceso se realiza en los siguientes pasos:
     * <ul>
     * <li>Se verifica si existen máquinas libres.</li>
     * <li>El usuario selecciona el tipo de trabajo.</li>
     * <li>Se crea una cola de máquinas disponibles para el tipo de
     * trabajo.</li>
     * <li>Se asigna la primera máquina disponible de la cola.</li>
     * <li>El usuario selecciona una parcela.</li>
     * <li>Se establece la fecha de inicio del trabajo.</li>
     * <li>Se guarda el trabajo y la máquina en los archivos
     * correspondientes.</li>
     * </ul>
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
        for (TipoTrabajo tipo : TipoTrabajo.values()) {
            System.out.println(tipo.ordinal() + 1 + ". " + tipo.name()); 
        }
        
        int tipoTrabajoSeleccionado = leerEntero("Ingrese el número del tipo de trabajo: ");
       
        if (tipoTrabajoSeleccionado < 1 || tipoTrabajoSeleccionado > TipoTrabajo.values().length) {
            System.out.println("Opción no válida. Inténtelo de nuevo.");
            return;
        }

        // Convertir la selección en un valor del enum TipoTrabajo
        TipoTrabajo tipoTrabajo = TipoTrabajo.values()[tipoTrabajoSeleccionado - 1];

        // Crear una cola para las máquinas libres del tipo seleccionado
        Cola<Maquina> colaMaquinasTipo = new Cola<>();

        // Recorremos la lista de máquinas y agregamos a la cola aquellas que estén libres y coincidan con el tipo de trabajo
        Iterador<Maquina> iteradorMaquinas = new Iterador<>(maquinas);
        while (iteradorMaquinas.hayElemento()) {
            Maquina maquina = iteradorMaquinas.dameValor();
            if (maquina.getEstado() == Estado.libre && maquina.getTipoTrabajo() == tipoTrabajo) {
                colaMaquinasTipo.encolar(maquina);
            }
            iteradorMaquinas.next();
        }

        // Si la cola está vacía, no hay máquinas disponibles para el tipo de trabajo
        if (colaMaquinasTipo.esVacia()) {
            System.out.println("No hay máquinas libres disponibles para el tipo de trabajo seleccionado.");
            return;
        }
       
        Maquina maquinaSeleccionada = colaMaquinasTipo.desencolar();
        maquinaSeleccionada.setEstado(Estado.asignada);

        
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

        guardarTrabajosEnArchivo(trabajos);
        guardarMaquinasEnArchivo(maquinas);
    }

    /**
     * Finaliza un trabajo activo (con fecha de finalización null), solicitando
     * la fecha de finalización. Además, libera la máquina que estaba asignada
     * al trabajo.
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

        while (iteradorTrabajos.hasNext()) {
            Trabajo trabajo = iteradorTrabajos.dameValor();
            
            if (trabajo.getId() == idTrabajo && trabajo.getFechaFin() == null) {
                trabajoEncontrado = true;
              
                LocalDate fechaFin = leerFecha("Ingrese la fecha de fin (formato: yyyy-MM-dd):");
               
                if (fechaFin != null) {
                    trabajo.setFechaFin(fechaFin); 
                    trabajo.getMaquina().setEstado(Estado.libre); 
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
        
        guardarTrabajosEnArchivo(trabajos);
        guardarMaquinasEnArchivo(maquinas);
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
