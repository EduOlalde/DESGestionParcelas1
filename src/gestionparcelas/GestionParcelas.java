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
    public static Lista<Agricultor> agricultores = new Lista<>();
    public static Lista<Maquina> maquinas = new Lista<>();
    public static Lista<Parcela> parcelas = new Lista<>();
    public static Lista<Trabajo> trabajos = new Lista<>();
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
                case 1 ->
                    MenuAgricultores.mostrarMenu();
                case 2 ->
                    MenuMaquinas.mostrarMenu();
                case 3 ->
                    MenuParcelas.mostrarMenu();
                case 4 ->
                    MenuTrabajos.mostrarMenu();
                case 5 ->
                    listarDatos();
                case 6 -> {
                    salir = true;
                    System.out.println("¡Hasta luego!");
                }
                default ->
                    System.out.println("Opción no válida.");
            }
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

    public static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    public static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public static Date leerFecha(String mensaje) {
        Date fecha = null;

        while (fecha == null) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();

            try {
                // Dividir la entrada por "-"
                String[] partes = entrada.split("-");
                if (partes.length == 3) {
                    int anno = Integer.parseInt(partes[0]) - 1900; // Date usa el año desde 1900
                    int mes = Integer.parseInt(partes[1]) - 1; // Los meses van de 0 a 11
                    int dia = Integer.parseInt(partes[2]);

                    // Crear la fecha
                    fecha = new Date(anno, mes, dia);
                } else {
                    System.out.println("Formato incorrecto. Use el formato yyyy-MM-dd.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Formato incorrecto. Asegúrese de usar números válidos para año, mes y día.");
            } catch (IllegalArgumentException e) {
                System.out.println("Fecha inválida. Por favor, intente de nuevo.");
            }
        }

        return fecha;
    }

    public static double leerReal(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    public static void guardarAgricultoresEnArchivo() {
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

    public static void cargarAgricultoresDesdeArchivo() {
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

    public static void guardarMaquinasEnArchivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("maquinas.txt"))) {
            Nodo<Maquina> nodo = maquinas.getNodoInicial();
            while (nodo != null) {
                Maquina maquina = nodo.getInf();
                // Guardar ID, tipo, modelo y estado de la máquina
                writer.println(maquina.getId() + "," + maquina.getTipo() + "," + maquina.getModelo() + "," + maquina.getEstado());
                nodo = nodo.getSig();
            }
            System.out.println("Datos de máquinas guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de máquinas: " + e.getMessage());
        }
    }

    public static void cargarMaquinasDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("maquinas.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {  // Esperamos 4 datos: id, tipo, modelo, estado
                    int id = Integer.parseInt(datos[0]);  // Convertir ID a número
                    String tipo = datos[1];
                    String modelo = datos[2];
                    String estado = datos[3];

                    Maquina maquina = new Maquina(id, tipo, modelo, estado);
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

    public static void guardarParcelasEnArchivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("parcelas.txt"))) {
            Nodo<Parcela> nodo = parcelas.getNodoInicial();
            while (nodo != null) {
                Parcela parcela = nodo.getInf();
                // Escribimos los datos de la parcela en formato CSV incluyendo el ID del agricultor
                writer.println(parcela.getId() + "," + parcela.getAgricultor().getId() + ","
                        + parcela.getUbicacion() + "," + parcela.getExtension() + "," + parcela.getCultivo());
                nodo = nodo.getSig();
            }
            System.out.println("Datos de parcelas guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de parcelas: " + e.getMessage());
        }
    }

    public static void cargarParcelasDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("parcelas.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Dividimos la línea en partes usando la coma como separador
                String[] datos = linea.split(",");
                if (datos.length == 5) { // Validamos que haya exactamente 5 campos
                    int id = Integer.parseInt(datos[0]);
                    int idAgricultor = Integer.parseInt(datos[1]);
                    String ubicacion = datos[2];
                    double extension = Double.parseDouble(datos[3]);
                    String cultivo = datos[4];

                    // Buscar al agricultor por su ID
                    Agricultor agricultor = MenuAgricultores.buscarAgricultorPorId(idAgricultor);
                    if (agricultor != null) {
                        // Creamos una nueva parcela con los datos leídos
                        Parcela parcela = new Parcela(id, agricultor, ubicacion, extension, cultivo);
                        parcelas.add(parcela); // Agregamos la parcela a la lista
                    } else {
                        System.out.println("Agricultor con ID " + idAgricultor + " no encontrado. Parcela no cargada.");
                    }
                }
            }
            System.out.println("Datos de parcelas cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de parcelas no encontrado. Se iniciará con una lista vacía.");
        } catch (IOException e) {
            System.out.println("Error al cargar los datos de parcelas: " + e.getMessage());
        }
    }

    public static void guardarTrabajosEnArchivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("trabajos.txt"))) {
            Nodo<Trabajo> nodo = trabajos.getNodoInicial();
            while (nodo != null) {
                Trabajo trabajo = nodo.getInf();
                // Escribimos los datos del trabajo en formato CSV, incluyendo IDs de parcela y máquina
                writer.println(
                        trabajo.getId() + ","
                        + trabajo.getParcela().getId() + ","
                        + trabajo.getMaquina().getId() + ","
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

    public static void cargarTrabajosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("trabajos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Dividimos la línea en partes usando la coma como separador
                String[] datos = linea.split(",");
                if (datos.length == 6) { // Validamos que haya exactamente 6 campos
                    int id = Integer.parseInt(datos[0]);
                    int parcelaId = Integer.parseInt(datos[1]);
                    int maquinaId = Integer.parseInt(datos[2]);
                    String tipo = datos[3];
                    String fechaInicioStr = datos[4];
                    String fechaFinStr = datos[5];

                    // Convertimos las fechas de String a Date si no están vacías
                    Date fechaInicio = null;
                    Date fechaFin = null;

                    if (!fechaInicioStr.isEmpty()) {
                        fechaInicio = new Date(fechaInicioStr); // Suponiendo formato "yyyy-MM-dd"
                    }

                    if (!fechaFinStr.isEmpty()) {
                        fechaFin = new Date(fechaFinStr); // Suponiendo formato "yyyy-MM-dd"
                    }

                    // Buscar parcela y máquina por sus IDs
                    Parcela parcela = MenuParcelas.buscarParcelaPorId(parcelaId);
                    Maquina maquina = MenuMaquinas.buscarMaquinaPorId(maquinaId);

                    if (parcela != null && maquina != null) {
                        // Creamos el objeto Trabajo y lo agregamos a la lista
                        Trabajo trabajo = new Trabajo(id, parcela, maquina, tipo, fechaInicio, fechaFin);
                        trabajos.add(trabajo); // Agregamos el trabajo a la lista
                    } else {
                        System.out.println("No se encontró la parcela o la máquina para el trabajo con ID: " + id);
                    }
                }
            }
            System.out.println("Datos de trabajos cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de trabajos no encontrado. Se iniciará con una lista vacía.");
        } catch (IOException e) {
            System.out.println("Error al cargar los datos de trabajos: " + e.getMessage());
        }
    }

    private static void listarDatos() {
        System.out.println("\n--- LISTADO ---");

        MenuAgricultores.listarAgricultores();
        MenuMaquinas.listarMaquinas();
        MenuParcelas.listarParcelas();
        MenuTrabajos.listarTrabajos();
    }

}
