package gestionparcelas;

import ListasTemplates.*;
import static gestionparcelas.GestionFicheros.guardarMaquinasEnArchivo;
import static gestionparcelas.EntradaDatos.*;
import static gestionparcelas.GestionParcelas.*;
import gestionparcelas.Maquina.Estado;
import gestionparcelas.Maquina.TipoTrabajo;

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
                case 1:
                    altaMaquina();
                    break;
                case 2:
                    bajaMaquina();
                    break;
                case 3:
                    modificarMaquina();
                    break;
                case 4:
                    listarMaquinas();
                    break;
                case 5:
                    listarMaquinasLibres();
                    break;
                case 6:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    /**
     * Permite dar de alta una nueva máquina. Se solicita el tipo y modelo de la
     * máquina, y se asigna un estado inicial como "libre". La máquina se añade
     * a la lista de máquinas y la lista se guarda en el archivo
     * correspondiente. Luego, se actualizan las colas de máquinas libres.
     */
    private static void altaMaquina() {
        // Generar un nuevo ID automático tomando el mayor ID existente + 1
        int nuevoId = 1;

        Iterador<Maquina> iterador = new Iterador<>(maquinas);
        while (iterador.hayElemento()) {
            Maquina maquina = iterador.dameValor();
            if (maquina.getId() >= nuevoId) {
                nuevoId = maquina.getId() + 1;
            }
            iterador.next();
        }

        System.out.println("Alta de máquina:");
        System.out.println("Tipos de máquinas disponibles:");
        for (TipoTrabajo tipo : TipoTrabajo.values()) {
            System.out.println(tipo.ordinal() + 1 + ". " + tipo.name());
        }

        int tipoTrabajoSeleccionado = leerEntero("Ingrese el número del tipo de trabajo: ");

        if (tipoTrabajoSeleccionado < 1 || tipoTrabajoSeleccionado > TipoTrabajo.values().length) {
            System.out.println("Opción no válida. Inténtelo de nuevo.");
            return;
        }

        TipoTrabajo tipoTrabajo = TipoTrabajo.values()[tipoTrabajoSeleccionado - 1];
        String modelo = leerCadena("Modelo de máquina: ");
        Estado estado = Estado.libre;

        Maquina nuevaMaquina = new Maquina(nuevoId, tipoTrabajo, modelo, estado);

        maquinas.add(nuevaMaquina);

        // Guardar cambios y actualizar las colas
        guardarMaquinasEnArchivo(maquinas);
        actualizarCola(nuevaMaquina, "alta");

        System.out.println("Máquina añadida correctamente con ID: " + nuevoId);
    }

    /**
     * Permite eliminar una máquina existente. Se solicita el ID de la máquina a
     * eliminar y, si se encuentra en la lista, se elimina. La lista se guarda
     * después de la eliminación y se actualizan las colas
     */
    private static void bajaMaquina() {
        System.out.println("Baja de máquina:");
        int id = leerEntero("ID de la máquina a eliminar: ");
        Maquina temp = new Maquina(id, TipoTrabajo.arar, "", Estado.libre); // Tipo genérico para búsqueda

        if (maquinas.borrarTodos(temp)) {
            guardarMaquinasEnArchivo(maquinas);
            actualizarCola(temp, "baja");
            System.out.println("Máquina eliminada correctamente.");
        } else {
            System.out.println("Máquina no encontrada.");
        }
    }

    /**
     * Permite modificar los datos de una máquina existente. Se solicita el ID
     * de la máquina a modificar, y si se encuentra, se actualizan los campos
     * como el tipo, modelo y estado. La lista de máquinas se guarda después de
     * la modificación y se actualizan las colas.
     */
    private static void modificarMaquina() {
        int id = leerEntero("ID de la máquina a modificar: ");

        Iterador<Maquina> iter = new Iterador<>(maquinas);
        while (iter.hayElemento()) {
            Maquina maquina = iter.dameValor();
            if (maquina.getId() == id) {
                System.out.println("Tipos de máquinas disponibles:");
                for (TipoTrabajo tipo : TipoTrabajo.values()) {
                    System.out.println(tipo.ordinal() + 1 + ". " + tipo.name());
                }

                int tipoTrabajoSeleccionado = leerEntero("Ingrese el número del nuevo tipo de trabajo: ");

                // Validar que la opción esté en el rango de tipos disponibles
                if (tipoTrabajoSeleccionado < 1 || tipoTrabajoSeleccionado > TipoTrabajo.values().length) {
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
                    return;
                }

                // Convertir la selección en un valor del enum TipoTrabajo
                TipoTrabajo tipo = TipoTrabajo.values()[tipoTrabajoSeleccionado - 1];

                System.out.println("Estados disponibles: ");
                for (Estado estado : Estado.values()) {
                    System.out.println(estado.ordinal() + 1 + ". " + estado.name());
                }

                // Leer la opción del estado
                int tipoEstadoSeleccionado = leerEntero("Ingrese el número del nuevo estado: ");

                // Validar que la opción esté en el rango de tipos disponibles
                if (tipoEstadoSeleccionado < 1 || tipoEstadoSeleccionado > Estado.values().length) {
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
                    return;
                }

                // Convertir la selección en un valor del enum Estado
                Estado estado = Estado.values()[tipoEstadoSeleccionado - 1];

                maquina.setModelo(leerCadena("Nuevo modelo: "));
                maquina.setTipoTrabajo(tipo);
                maquina.setEstado(estado);

                System.out.println("Máquina modificada correctamente.");
                guardarMaquinasEnArchivo(maquinas);
                actualizarCola(maquina, "baja");
                actualizarCola(maquina, "alta");
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

        System.out.println("\n--- Máquinas libres ---");
        Nodo<Maquina> nodo = maquinasLibres.getNodoInicio();
        while (nodo != null) {
            Maquina maquina = nodo.getInf();
            System.out.println("ID: " + maquina.getId() + ", tipo: "
                    + maquina.getTipoTrabajo() + ", modelo: " + maquina.getModelo());
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
        return null;
    }

    /**
     * Método que agrega a la cola todas las máquinas libres de un TipoTrabajo
     * específico.
     *
     * @param listaMaquinas La lista de máquinas de la cual se seleccionarán las
     * máquinas.
     * @param cola La cola donde se agregarán las máquinas.
     * @param tipoTrabajo El tipo de trabajo por el cual filtrar las máquinas.
     */
    public static void encolarMaquinasLibres(Lista<Maquina> listaMaquinas, Cola<Maquina> cola, Maquina.TipoTrabajo tipoTrabajo) {
        Nodo<Maquina> nodoActual = listaMaquinas.getNodoInicial();

        while (nodoActual != null) {
            Maquina maquina = nodoActual.getInf();
            // Verifica que la máquina sea del tipo especificado y esté libre
            if (maquina != null && maquina.getTipoTrabajo() == tipoTrabajo && maquina.getEstado() == Maquina.Estado.libre) {
                cola.encolar(maquina);
            }
            nodoActual = nodoActual.getSig();
        }
    }

    /**
     * Actualiza la cola de máquinas libres correspondiente al tipo de trabajo
     * de la máquina especificada. La cola se actualiza según la acción indicada
     * (alta o baja).
     *
     * @param maquina La máquina cuya cola de tipo de trabajo se debe
     * actualizar.
     * @param accion La acción a realizar: "alta" para encolar, "baja" para
     * eliminar.
     */
    private static void actualizarCola(Maquina maquina, String accion) {
        if (maquina == null) {
            System.out.println("La máquina proporcionada es nula.");
            return;
        }

        // Identificar la cola correspondiente al tipo de trabajo de la máquina
        Cola<Maquina> colaActualizar;
        switch (maquina.getTipoTrabajo()) {
            case arar:
                colaActualizar = mArarLibres;
                break;
            case cosechar:
                colaActualizar = mCosecharLibres;
                break;
            case sembrar:
                colaActualizar = mSembrarLibres;
                break;
            case fumigar:
                colaActualizar = mFumigarLibres;
                break;
            default:
                System.out.println("Tipo de trabajo no válido para la máquina.");
                return;
        }

        // Realizar la acción solicitada
        switch (accion.toLowerCase()) {
            case "alta":
                // Encolar la máquina si está libre
                if (maquina.getEstado() == Estado.libre) {
                    colaActualizar.encolar(maquina);
                    System.out.println("Máquina " + maquina.getId() + " añadida a la cola de tipo " + maquina.getTipoTrabajo());
                } else {
                    System.out.println("La máquina no está libre para ser añadida.");
                }
                break;

            case "baja":
                // Eliminar la máquina de la cola
                Cola<Maquina> tempCola = new Cola<>();
                boolean maquinaEliminada = false;

                // Transferir todas las máquinas excepto la que se va a eliminar
                while (!colaActualizar.esVacia()) {
                    Maquina m = colaActualizar.desencolar();
                    if (m.getId() != maquina.getId()) {
                        tempCola.encolar(m);
                    } else {
                        maquinaEliminada = true;
                    }
                }

                // Si la máquina fue eliminada, actualizar la cola
                if (maquinaEliminada) {
                    colaActualizar = tempCola;
                    System.out.println("Máquina " + maquina.getId() + " eliminada de la cola de tipo " + maquina.getTipoTrabajo());
                } else {
                    System.out.println("Máquina con ID " + maquina.getId() + " no encontrada en la cola.");
                }
                break;

            default:
                System.out.println("Acción no válida. Use 'alta' o 'baja'.");
                break;
        }
    }

}
