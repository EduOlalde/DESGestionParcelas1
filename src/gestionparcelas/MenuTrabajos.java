package gestionparcelas;

import ListasTemplates.*;
import static gestionparcelas.GestionFicheros.guardarTrabajosEnArchivo;
import static gestionparcelas.GestionParcelas.leerCadena;
import static gestionparcelas.GestionParcelas.leerEntero;
import static gestionparcelas.GestionParcelas.leerFecha;
import static gestionparcelas.GestionParcelas.maquinas;
import static gestionparcelas.GestionParcelas.parcelas;
import static gestionparcelas.GestionParcelas.trabajos;
import static gestionparcelas.MenuMaquinas.buscarMaquinaPorId;
import static gestionparcelas.MenuParcelas.buscarParcelaPorId;
import gestionparcelas.Maquina.Estado;
import java.util.Date;

/**
 *
 * @author Eduardo Olalde
 */
public class MenuTrabajos {

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

    private static void asignarTrabajo() {
        //Comprueba si hay máquinas libres o no permite la asignación
        if(!MenuMaquinas.listarMaquinasLibres()) return;
        
        // Mostrar las parcelas disponibles
        System.out.println("Seleccione una parcela:");
        Nodo<Parcela> nodoParcela = parcelas.getNodoInicial();
        while (nodoParcela != null) {
            Parcela parcela = nodoParcela.getInf();
            System.out.println(parcela.getId() + ". " + parcela.getUbicacion());
            nodoParcela = nodoParcela.getSig();
        }
        int parcelaId = leerEntero("Ingrese el ID de la parcela: ");
        Parcela parcelaSeleccionada = buscarParcelaPorId(parcelaId);

        if (parcelaSeleccionada == null) {
            System.out.println("Parcela no encontrada. Inténtelo de nuevo.");
            return;
        }

        // Mostrar las máquinas disponibles
        System.out.println("Seleccione una máquina:");
        MenuMaquinas.listarMaquinasLibres();
        
        int maquinaId = leerEntero("Ingrese el ID de la máquina: ");
        Maquina maquinaSeleccionada = buscarMaquinaPorId(maquinaId);

        if (maquinaSeleccionada == null) {
            System.out.println("Máquina no encontrada. Inténtelo de nuevo.");
            return;
        }
        else{
            maquinaSeleccionada.setEstado(Estado.asignada);
        }

        // Solicitar el tipo de trabajo
        String tipoTrabajo = leerCadena("Ingrese el tipo de trabajo (ej. 'arar', 'sembrar', etc.): ");

        // Solicitar fecha de inicio
        Date fechaInicio = leerFecha("Ingrese la fecha de inicio (formato: yyyy-MM-dd): ");

        // Calcular el ID del nuevo trabajo (basado en la cantidad actual de trabajos)
        int idTrabajo = 1;
        Nodo<Trabajo> nodoTrabajo = trabajos.getNodoInicial();
        while (nodoTrabajo != null) {
            idTrabajo++;
            nodoTrabajo = nodoTrabajo.getSig();
        }

        // Crear el objeto Trabajo con el constructor actualizado
        Trabajo trabajo = new Trabajo(idTrabajo, parcelaSeleccionada, maquinaSeleccionada, tipoTrabajo, fechaInicio, null);

        // Agregar el trabajo a la lista
        trabajos.add(trabajo);

        // Mostrar información del trabajo creado
        System.out.println("Trabajo creado:");
        System.out.println(trabajo.toString());

        // Guardar trabajos en el archivo
        //guardarTrabajosEnArchivo();
    }

    private static void finalizarTrabajo() {
        // Solicitar al usuario el ID del trabajo que desea finalizar
        int idTrabajo = leerEntero("Ingrese el ID del trabajo a finalizar: ");

        // Buscar el trabajo en la lista de trabajos
        Nodo<Trabajo> nodoTrabajo = trabajos.getNodoInicial();
        boolean trabajoEncontrado = false;

        while (nodoTrabajo != null) {
            Trabajo trabajo = nodoTrabajo.getInf();

            // Verificar si el trabajo tiene el ID proporcionado
            if (trabajo.getId() == idTrabajo) {
                trabajoEncontrado = true;

                Date fechaFin = leerFecha("Ingrese la fecha de fin (formato: yyyy-MM-dd):");

                // Validar y asignar la fecha de fin
                if (fechaFin != null) {
                    trabajo.setFechaFin(fechaFin); // Asignar la fecha de fin al trabajo
                    trabajo.getMaquina().setEstado(Estado.libre); // Libera la máquina usada
                    System.out.println("Trabajo finalizado con éxito.");
                } else {
                    System.out.println("Fecha de fin no válida.");
                }

                nodoTrabajo = nodoTrabajo.getSig();
            }

            if (!trabajoEncontrado) {
                System.out.println("Trabajo con ID " + idTrabajo + " no encontrado.");
            }

            // Guardar trabajos en el archivo después de modificar el estado
            //guardarTrabajosEnArchivo();
        }

    }

    public static void listarTrabajos() {
        System.out.println("\n--- Trabajos ---");
        // Recorrer la lista de trabajos y mostrar su estado
        Nodo<Trabajo> nodoTrabajo = trabajos.getNodoInicial();

        if (nodoTrabajo == null) {
            System.out.println("No hay trabajos registrados.");
            return;
        }

        System.out.println("Lista de trabajos:");
        while (nodoTrabajo != null) {
            Trabajo trabajo = nodoTrabajo.getInf();
            String estado = (trabajo.getFechaFin() == null) ? "Asignado" : "Terminado";
            System.out.println("Trabajo ID: " + trabajo.getId()
                    + " | Parcela: " + trabajo.getParcela().getUbicacion()
                    + " | Tipo: " + trabajo.getTipo()
                    + " | Estado: " + estado);
            nodoTrabajo = nodoTrabajo.getSig();
        }
    }
}
