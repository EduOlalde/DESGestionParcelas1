package gestionparcelas;

import ListasTemplates.*;
import java.util.Scanner;
import java.io.*;
import java.util.Date;

/**
 *
 * @author Eduardo Olalde
 */
public class GestionParcelas {

    // Listas estáticas para manejar los datos
    private static Lista<Agricultor> agricultores = new Lista<>();
    private static Lista<Maquina> maquinas = new Lista<>();
    private static Lista<Parcela> parcelas = new Lista<>();
    private static Lista<Trabajo> trabajos = new Lista<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Cargar datos al inicio
        cargarAgricultoresDesdeArchivo();
        cargarMaquinasDesdeArchivo();
        cargarParcelasDesdeArchivo();
        cargarTrabajosDesdeArchivo();

        // Menú principal
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1:
                    gestionarAgricultores();
                    break;
                case 2:
                    gestionarMaquinas();
                    break;
                case 3:
                    gestionarParcelas();
                    break;
                case 4:
                    gestionarTrabajos();
                    break;
                case 5:
                    listarDatos();
                    break;
                case 6:
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    private static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private static void guardarAgricultoresEnArchivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("agricultores.txt"))) {
            Nodo<Agricultor> nodo = agricultores.getNodoInicial();
            while (nodo != null) {
                Agricultor agricultor = nodo.getInf();
                writer.println(agricultor.getId() + "," + agricultor.getNombre() + "," + agricultor.getPassword());
                nodo = nodo.getSig();
            }
            System.out.println("Datos de agricultores guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de agricultores: " + e.getMessage());
        }
    }

    private static void cargarAgricultoresDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("agricultores.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    int id = Integer.parseInt(datos[0]); // Recoger el id como entero
                    Agricultor agricultor = new Agricultor(id, datos[1], datos[2]);
                    agricultores.add(agricultor);
                }
            }
            System.out.println("Datos de agricultores cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de agricultores no encontrado. Se iniciará con una lista vacía.");
        } catch (IOException e) {
            System.out.println("Error al cargar los datos de agricultores: " + e.getMessage());
        }
    }

    private static void guardarMaquinasEnArchivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("maquinas.txt"))) {
            Nodo<Maquina> nodo = maquinas.getNodoInicial();
            while (nodo != null) {
                Maquina maquina = nodo.getInf();
                writer.println(maquina.getTipo() + "," + maquina.getModelo() + "," + maquina.getEstado());
                nodo = nodo.getSig();
            }
            System.out.println("Datos de máquinas guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de máquinas: " + e.getMessage());
        }
    }

    private static void cargarMaquinasDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("maquinas.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    Maquina maquina = new Maquina(datos[0], datos[1], datos[2]);
                    maquinas.add(maquina);
                }
            }
            System.out.println("Datos de máquinas cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de máquinas no encontrado. Se iniciará con una lista vacía.");
        } catch (IOException e) {
            System.out.println("Error al cargar los datos de máquinas: " + e.getMessage());
        }
    }

    private static void guardarParcelasEnArchivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("parcelas.txt"))) {
            Nodo<Parcela> nodo = parcelas.getNodoInicial();
            while (nodo != null) {
                Parcela parcela = nodo.getInf();
                // Escribimos los datos de la parcela en formato CSV
                writer.println(parcela.getId() + "," + parcela.getUbicacion() + "," + parcela.getExtension() + "," + parcela.getCultivo());
                nodo = nodo.getSig();
            }
            System.out.println("Datos de parcelas guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de parcelas: " + e.getMessage());
        }
    }

    private static void cargarParcelasDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("parcelas.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Dividimos la línea en partes usando la coma como separador
                String[] datos = linea.split(",");
                if (datos.length == 4) { // Validamos que haya exactamente 4 campos
                    int id = Integer.parseInt(datos[0]);
                    String ubicacion = datos[1];
                    double extension = Double.parseDouble(datos[2]);
                    String cultivo = datos[3];
                    // Creamos una nueva parcela con los datos leídos
                    Parcela parcela = new Parcela(id, ubicacion, extension, cultivo);
                    parcelas.add(parcela); // Agregamos la parcela a la lista
                }
            }
            System.out.println("Datos de parcelas cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de parcelas no encontrado. Se iniciará con una lista vacía.");
        } catch (IOException e) {
            System.out.println("Error al cargar los datos de parcelas: " + e.getMessage());
        }
    }

    private static void guardarTrabajosEnArchivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("trabajos.txt"))) {
            Nodo<Trabajo> nodo = trabajos.getNodoInicial();
            while (nodo != null) {
                Trabajo trabajo = nodo.getInf();
                // Escribimos los datos del trabajo en formato CSV
                writer.println(
                        trabajo.getId() + ","
                        + trabajo.getParcela().getId() + ","
                        + trabajo.getTipo() + ","
                        + (trabajo.getFechaInicio() != null ? trabajo.getFechaInicio().toString() : "") + ","
                        + (trabajo.getFechaFin() != null ? trabajo.getFechaFin().toString() : "")
                );
                nodo = nodo.getSig();
            }
            System.out.println("Datos de trabajos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de trabajos: " + e.getMessage());
        }
    }

    private static void cargarTrabajosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("trabajos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Dividimos la línea en partes usando la coma como separador
                String[] datos = linea.split(",");
                if (datos.length == 5) { // Validamos que haya exactamente 5 campos
                    int id = Integer.parseInt(datos[0]);
                    int parcelaId = Integer.parseInt(datos[1]);
                    String tipo = datos[2];
                    String fechaInicioStr = datos[3];
                    String fechaFinStr = datos[4];

                    // Convertimos las fechas de String a Date si no están vacías
                    Date fechaInicio = null;
                    Date fechaFin = null;

                    if (!fechaInicioStr.isEmpty()) {
                        fechaInicio = new Date(fechaInicioStr); // Suponiendo formato "yyyy-MM-dd"
                    }

                    if (!fechaFinStr.isEmpty()) {
                        fechaFin = new Date(fechaFinStr); // Suponiendo formato "yyyy-MM-dd"
                    }

                    // Creamos el objeto Trabajo y lo agregamos a la lista
                    Parcela parcela = new Parcela(parcelaId, "", 0, ""); // Este es solo un ejemplo, busca la parcela por ID
                    Trabajo trabajo = new Trabajo(id, parcela, tipo, fechaInicio, fechaFin);
                    trabajos.add(trabajo); // Agregamos el trabajo a la lista
                }
            }
            System.out.println("Datos de trabajos cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de trabajos no encontrado. Se iniciará con una lista vacía.");
        } catch (IOException e) {
            System.out.println("Error al cargar los datos de trabajos: " + e.getMessage());
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Gestionar Agricultores");
        System.out.println("2. Gestionar Máquinas");
        System.out.println("3. Gestionar Parcelas");
        System.out.println("4. Gestionar Trabajos");
        System.out.println("5. Listar todos los datos");
        System.out.println("6. Salir");
    }

    private static void gestionarAgricultores() {
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
        System.out.print("ID del agricultor, ");
        int id = leerEntero("introduce un número entero:");
        System.out.print("Nombre del agricultor: ");
        String nombre = leerCadena("");
        System.out.print("Password del agricultor: ");
        String password = leerCadena("");

        Agricultor agricultor = new Agricultor(id, nombre, password);

        // Verificamos si ya existe un agricultor con el mismo id
        boolean yaExiste = false;
        Nodo<Agricultor> nodo = agricultores.getNodoInicial();
        while (nodo != null) {
            Agricultor agricultorExistente = nodo.getInf();
            if (agricultorExistente.getId() == agricultor.getId()) { // Comparamos las IDs
                yaExiste = true;
                break;
            }
            nodo = nodo.getSig();
        }

        if (yaExiste) {
            System.out.println("Error: Ya existe un agricultor con la misma ID.");
        } else {
            agricultores.add(agricultor);
            guardarAgricultoresEnArchivo();
            System.out.println("Agricultor añadido correctamente.");
        }
    }

    private static void bajaAgricultor() {
        System.out.print("ID del agricultor a eliminar, ");
        int id = leerEntero("introduce un número entero: ");

        // Creamos un agricultor temporal con el ID a eliminar para usar el método borrarTodos
        Agricultor agricultorAEliminar = new Agricultor(id, "", "");

        // Intentamos borrar todos los agricultores con el ID proporcionado
        boolean eliminada = agricultores.borrarTodos(agricultorAEliminar);

        if (eliminada) {
            System.out.println("Persona eliminada.");
            guardarAgricultoresEnArchivo();
        } else {
            System.out.println("Persona no encontrada.");
        }
    }

    private static void modificarAgricultor() {
        System.out.print("ID de la persona a modificar, ");
        int id = leerEntero("introduce un número entero:");

        Agricultor agricultorAEncontrar = new Agricultor(id, "", "");

        // Buscamos la persona en la lista manual
        Nodo<Agricultor> nodoActual = agricultores.getNodoInicial(); // Método que retorna el nodo inicial de la lista
        while (nodoActual != null) {
            if (nodoActual.getInf().equals(agricultorAEncontrar)) {
                // Agricultor encontrada, modificar sus datos
                Agricultor persona = nodoActual.getInf();
                System.out.print("Nuevo nombre: ");
                persona.setNombre(leerCadena(""));
                System.out.print("Nueva password: ");
                persona.setPassword(leerCadena(""));
                System.out.println("Agricultor modificado.");
                guardarAgricultoresEnArchivo();
                return;
            }
            nodoActual = nodoActual.getSig();
        }

        System.out.println("Agricultor no encontrada.");
    }

    private static void listarAgricultores() {
        System.out.println("\nAgricultores:");
        Nodo<Agricultor> nodo = agricultores.getNodoInicial();
        while (nodo != null) {
            System.out.println(nodo.getInf().toString() + "\n");
            nodo = nodo.getSig();
        }
    }

    private static void gestionarMaquinas() {
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
        System.out.print("Tipo de máquina: ");
        String tipo = leerCadena("");
        System.out.print("Modelo de máquina: ");
        String modelo = leerCadena("");
        System.out.print("Estado de máquina: ");
        String estado = leerCadena("");

        Maquina maquina = new Maquina(tipo, modelo, estado);
        maquinas.add(maquina);
        guardarMaquinasEnArchivo();
        System.out.println("Máquina añadida correctamente.");
    }

    private static void bajaMaquina() {
        System.out.print("Modelo de la máquina a eliminar: ");
        String modelo = leerCadena("");
        Maquina temp = new Maquina("", modelo, "");

        if (maquinas.borrarTodos(temp)) {
            guardarMaquinasEnArchivo();
            System.out.println("Máquina eliminada correctamente.");
        } else {
            System.out.println("Máquina no encontrada.");
        }
    }

    private static void modificarMaquina() {
        System.out.print("Modelo de la máquina a modificar: ");
        String modelo = leerCadena("");

        Nodo<Maquina> nodo = maquinas.getNodoInicial();
        while (nodo != null) {
            Maquina maquina = nodo.getInf();
            if (maquina.getModelo().equals(modelo)) {
                System.out.print("Nuevo tipo: ");
                maquina.setTipo(leerCadena(""));
                System.out.print("Nuevo estado: ");
                maquina.setEstado(leerCadena(""));
                System.out.println("Máquina modificada correctamente.");
                guardarMaquinasEnArchivo();
                return;
            }
            nodo = nodo.getSig();
        }
        System.out.println("Máquina no encontrada.");
    }

    private static void listarMaquinas() {
        System.out.println("\nMáquinas:");
        Nodo<Maquina> nodo = maquinas.getNodoInicial();
        while (nodo != null) {
            System.out.println(nodo.getInf() + "\n"); 
            nodo = nodo.getSig();
        }
    }

    private static void gestionarParcelas() {
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
        System.out.print("ID de la parcela: ");
        int id = leerEntero("");
        scanner.nextLine(); // Limpiar buffer
        System.out.print("Ubicación: ");
        String ubicacion = leerCadena("");
        System.out.print("Extensión (en hectáreas): ");
        double extension = scanner.nextDouble();
        scanner.nextLine(); // Limpiar buffer
        System.out.print("Cultivo: ");
        String cultivo = leerCadena("");

        // Crear la nueva parcela
        Parcela parcela = new Parcela(id, ubicacion, extension, cultivo);

        // Verificar si ya existe una parcela con el mismo ID
        boolean yaExiste = false;
        Nodo<Parcela> nodo = parcelas.getNodoInicial();
        while (nodo != null) {
            Parcela parcelaExistente = nodo.getInf();
            if (parcelaExistente.getId() == parcela.getId()) { // Comparamos las IDs
                yaExiste = true;
                break;
            }
            nodo = nodo.getSig();
        }

        if (yaExiste) {
            System.out.println("Error: Ya existe una parcela con la misma ID.");
        } else {
            parcelas.add(parcela);
            guardarParcelasEnArchivo();
            System.out.println("Parcela añadida correctamente.");
        }
    }

    private static void bajaParcela() {
        System.out.print("ID de la parcela a eliminar: ");
        int id = leerEntero("");
        scanner.nextLine(); // Limpiar buffer
        Parcela temp = new Parcela(id, "", 0, "");

        if (parcelas.borrarTodos(temp)) {
            guardarParcelasEnArchivo();
            System.out.println("Parcela eliminada correctamente.");
        } else {
            System.out.println("Parcela no encontrada.");
        }
    }

    private static void modificarParcela() {
        System.out.print("ID de la parcela a modificar: ");
        int id = leerEntero("");
        scanner.nextLine(); // Limpiar buffer

        Nodo<Parcela> nodo = parcelas.getNodoInicial();
        while (nodo != null) {
            Parcela parcela = nodo.getInf();
            if (parcela.getId() == id) {
                System.out.print("Nueva ubicación: ");
                parcela.setUbicacion(leerCadena(""));
                System.out.print("Nueva extensión (en hectáreas): ");
                parcela.setExtension(scanner.nextDouble());
                scanner.nextLine(); // Limpiar buffer
                System.out.print("Nuevo cultivo: ");
                parcela.setCultivo(leerCadena(""));
                System.out.println("Parcela modificada correctamente.");
                guardarParcelasEnArchivo();
                return;
            }
            nodo = nodo.getSig();
        }
        System.out.println("Parcela no encontrada.");
    }

    private static void listarParcelas() {
        System.out.println("\nParcelas:");
        Nodo<Parcela> nodo = parcelas.getNodoInicial();
        while (nodo != null) {
            System.out.println(nodo.getInf().toString() + "\n");
            nodo = nodo.getSig();
        }
    }

    private static void listarDatos() {
        System.out.println("\n--- LISTADO ---");
        
        listarAgricultores();       
        listarMaquinas();        
        listarParcelas();
        listarTrabajos();
    }

    private static void gestionarTrabajos() {
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

    private static void asignarTrabajo() {
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

        // Mostrar las máquinas disponibles
        System.out.println("Seleccione una máquina:");
        Nodo<Maquina> nodoMaquina = maquinas.getNodoInicial();
        while (nodoMaquina != null) {
            Maquina maquina = nodoMaquina.getInf();
            System.out.println(maquina.getId() + ". " + maquina.getModelo());
            nodoMaquina = nodoMaquina.getSig();
        }
        int maquinaId = leerEntero("Ingrese el ID de la máquina: ");
        Maquina maquinaSeleccionada = buscarMaquinaPorId(maquinaId);

        // Solicitar el tipo de trabajo
        String tipoTrabajo = leerCadena("Ingrese el tipo de trabajo (ej. 'arar', 'sembrar', etc.): ");

        // Solicitar fechas de inicio y fin
        Date fechaInicio = null;

        System.out.println("Ingrese la fecha de inicio (formato: yyyy-MM-dd):");
        String fechaInicioStr = leerCadena("");
        if (!fechaInicioStr.isEmpty()) {
            fechaInicio = new Date(fechaInicioStr); // Convertir a Date
        }

        // Calcular el ID del nuevo trabajo
        int idTrabajo = 1;
        Nodo<Trabajo> nodoTrabajo = trabajos.getNodoInicial();
        while (nodoTrabajo != null) {
            idTrabajo++;
            nodoTrabajo = nodoTrabajo.getSig();
        }

        // Crear el trabajo
        Trabajo trabajo = new Trabajo(idTrabajo, parcelaSeleccionada, tipoTrabajo, fechaInicio, null);

        // Asignar la máquina y, opcionalmente, un agricultor
        trabajo.asignarMaquina(maquinaSeleccionada);

        // Si se tiene un agricultor disponible, asignarlo también
        Agricultor agricultorSeleccionado = seleccionarAgricultor();
        if (agricultorSeleccionado != null) {
            trabajo.asignarAgricultor(agricultorSeleccionado);
        }

        // Agregar el trabajo a la lista
        trabajos.add(trabajo);

        // Mostrar información del trabajo creado
        System.out.println("Trabajo creado:");
        System.out.println(trabajo.toString());

        // Guardar trabajos en el archivo
        guardarTrabajosEnArchivo();
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

                // Solicitar la fecha de fin
                System.out.println("Ingrese la fecha de fin (formato: yyyy-MM-dd):");
                String fechaFinStr = leerCadena("");
                Date fechaFin = null;

                if (!fechaFinStr.isEmpty()) {
                    fechaFin = new Date(fechaFinStr); // Convertir a Date
                    trabajo.setFechaFin(fechaFin); // Asignar la fecha de fin al trabajo
                    System.out.println("Trabajo finalizado con éxito.");
                    break;
                } else {
                    System.out.println("Fecha de fin no válida.");
                }
            }

            nodoTrabajo = nodoTrabajo.getSig();
        }

        if (!trabajoEncontrado) {
            System.out.println("Trabajo con ID " + idTrabajo + " no encontrado.");
        }

        // Guardar trabajos en el archivo después de modificar el estado
        guardarTrabajosEnArchivo();
    }

    private static Parcela buscarParcelaPorId(int id) {
        Nodo<Parcela> nodo = parcelas.getNodoInicial();
        while (nodo != null) {
            if (nodo.getInf().getId() == id) {
                return nodo.getInf();
            }
            nodo = nodo.getSig();
        }
        return null; // Retorna null si no encuentra la parcela
    }

    private static Maquina buscarMaquinaPorId(int id) {
        Nodo<Maquina> nodo = maquinas.getNodoInicial();
        while (nodo != null) {
            if (nodo.getInf().getId() == id) {
                return nodo.getInf();
            }
            nodo = nodo.getSig();
        }
        return null; // Retorna null si no encuentra la máquina
    }

    private static Agricultor seleccionarAgricultor() {
        // Si necesitas seleccionar un agricultor, puedes agregar una función similar a la de máquina o parcela
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
        return buscarAgricultorPorId(agricultorId); // Implementa esta búsqueda
    }

    private static Agricultor buscarAgricultorPorId(int id) {
        Nodo<Agricultor> nodo = agricultores.getNodoInicial();
        while (nodo != null) {
            if (nodo.getInf().getId() == id) {
                return nodo.getInf();
            }
            nodo = nodo.getSig();
        }
        return null; // Retorna null si no encuentra el agricultor
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

    private static void listarTrabajos() {
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