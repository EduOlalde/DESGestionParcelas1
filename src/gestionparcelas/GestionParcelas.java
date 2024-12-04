package gestionparcelas;

import ListasTemplates.*;
import java.util.Scanner;
import java.io.*;

/**
 *
 * @author Eduardo Olalde
 */
public class GestionParcelas {

    private static Lista<Agricultor> agricultores = new Lista<>();
    private static Lista<Maquina> maquinas = new Lista<>();
    private static Lista<Parcela> parcelas = new Lista<>();
    private static Scanner scanner = new Scanner(System.in);

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
                    Agricultor agricultor = new Agricultor(datos[0], datos[1], datos[2], ""); // El último parámetro es opcional
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

    public static void main(String[] args) {
        // Cargar datos al inicio
        cargarAgricultoresDesdeArchivo();
        cargarMaquinasDesdeArchivo();
        cargarParcelasDesdeArchivo();

        // Menú principal
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

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
                    listarDatos();
                    break;
                case 5:
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Gestionar Agricultores");
        System.out.println("2. Gestionar Máquinas");
        System.out.println("3. Gestionar Parcelas");
        System.out.println("4. Listar todos los datos");
        System.out.println("5. Salir");
        System.out.print("Selecciona una opción: ");
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

            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

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
        System.out.print("ID del agricultor: ");
        String id = scanner.nextLine();
        System.out.print("Nombre del agricultor: ");
        String nombre = scanner.nextLine();
        System.out.print("Password del agricultor: ");
        String password = scanner.nextLine();

        Agricultor agricultor = new Agricultor(id, nombre, password, ""); // Especialidad vacía por ahora
        agricultores.add(agricultor);
        guardarAgricultoresEnArchivo();
        System.out.println("Agricultor añadido correctamente.");

    }

    private static void bajaAgricultor() {
        System.out.print("ID del agricultor a eliminar: ");
        String id = scanner.nextLine();

        // Creamos un agricultor temporal con el ID a eliminar para usar el método borrarTodos
        Agricultor agricultorAEliminar = new Agricultor(id, "", "", "");

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
        System.out.print("ID de la persona a modificar: ");
        String id = scanner.nextLine();

        Agricultor agricultorAEncontrar = new Agricultor(id, "", "", "");

        // Buscamos la persona en la lista manual
        Nodo<Agricultor> nodoActual = agricultores.getNodoInicial(); // Método que retorna el nodo inicial de la lista
        while (nodoActual != null) {
            if (nodoActual.getInf().equals(agricultorAEncontrar)) {
                // Agricultor encontrada, modificar sus datos
                Agricultor persona = nodoActual.getInf();
                System.out.print("Nuevo nombre: ");
                persona.setNombre(scanner.nextLine());
                System.out.print("Nueva password: ");
                persona.setPassword(scanner.nextLine());
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
            nodo.getInf().mostrarInformacion();
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
            System.out.println("5. Volver al menú principal");

            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

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
                    salir = true;
                default ->
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private static void altaMaquina() {
        System.out.print("Tipo de máquina: ");
        String tipo = scanner.nextLine();
        System.out.print("Modelo de máquina: ");
        String modelo = scanner.nextLine();
        System.out.print("Estado de máquina: ");
        String estado = scanner.nextLine();

        Maquina maquina = new Maquina(tipo, modelo, estado);
        maquinas.add(maquina);
        guardarMaquinasEnArchivo();
        System.out.println("Máquina añadida correctamente.");
    }

    private static void bajaMaquina() {
        System.out.print("Modelo de la máquina a eliminar: ");
        String modelo = scanner.nextLine();
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
        String modelo = scanner.nextLine();

        Nodo<Maquina> nodo = maquinas.getNodoInicial();
        while (nodo != null) {
            Maquina maquina = nodo.getInf();
            if (maquina.getModelo().equals(modelo)) {
                System.out.print("Nuevo tipo: ");
                maquina.setTipo(scanner.nextLine());
                System.out.print("Nuevo estado: ");
                maquina.setEstado(scanner.nextLine());
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
            nodo.getInf().mostrarInformacion();
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

            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

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
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        System.out.print("Ubicación: ");
        String ubicacion = scanner.nextLine();
        System.out.print("Extensión (en hectáreas): ");
        double extension = scanner.nextDouble();
        scanner.nextLine(); // Limpiar buffer
        System.out.print("Cultivo: ");
        String cultivo = scanner.nextLine();

        Parcela parcela = new Parcela(id, ubicacion, extension, cultivo);
        parcelas.add(parcela);
        guardarParcelasEnArchivo();
        System.out.println("Parcela añadida correctamente.");
    }

    private static void bajaParcela() {
        System.out.print("ID de la parcela a eliminar: ");
        int id = scanner.nextInt();
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
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        Nodo<Parcela> nodo = parcelas.getNodoInicial();
        while (nodo != null) {
            Parcela parcela = nodo.getInf();
            if (parcela.getId() == id) {
                System.out.print("Nueva ubicación: ");
                parcela.setUbicacion(scanner.nextLine());
                System.out.print("Nueva extensión (en hectáreas): ");
                parcela.setExtension(scanner.nextDouble());
                scanner.nextLine(); // Limpiar buffer
                System.out.print("Nuevo cultivo: ");
                parcela.setCultivo(scanner.nextLine());
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
            nodo.getInf().mostrarInformacion();
            nodo = nodo.getSig();
        }
    }

    private static void listarDatos() {
        System.out.println("\n--- LISTADO ---");

        System.out.println("\nPersonas:");
        Nodo<Agricultor> nodoPersona = agricultores.getNodoInicial();
        while (nodoPersona != null) {
            nodoPersona.getInf().mostrarInformacion();
            nodoPersona = nodoPersona.getSig();
        }

        System.out.println("\nMáquinas:");
        Nodo<Maquina> nodoMaquina = maquinas.getNodoInicial();
        while (nodoMaquina != null) {
            nodoMaquina.getInf().mostrarInformacion();
            nodoMaquina = nodoMaquina.getSig();
        }

        System.out.println("\nParcelas:");
        Nodo<Parcela> nodoParcela = parcelas.getNodoInicial();
        while (nodoParcela != null) {
            nodoParcela.getInf().mostrarInformacion();
            nodoParcela = nodoParcela.getSig();
        }
    }

}
