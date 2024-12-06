package gestionparcelas;

import ListasTemplates.*;
import java.io.*;
import java.time.LocalDate;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;

/**
 * Clase encargada de gestionar la lectura y escritura de datos de agricultores,
 * máquinas, parcelas y trabajos en archivos de texto. Utiliza la clase
 * {@link Iterador} para recorrer las listas de objetos y guardar/cargar datos
 * en formato CSV.
 *
 * Los métodos de esta clase permiten guardar los datos en archivos o cargarlos
 * desde archivos, para persistir información relevante sobre agricultores,
 * máquinas, parcelas y trabajos.
 *
 * @author Eduardo Olalde
 */
public class GestionFicheros {

    /**
     * Guarda la lista de agricultores en un archivo de texto llamado
     * "agricultores.txt". Los datos de cada agricultor se almacenan en formato
     * CSV (ID, nombre, password).
     *
     * @param agricultores La lista de agricultores a guardar en el archivo.
     */
    public static void guardarAgricultoresEnArchivo(Lista agricultores) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("agricultores.txt"))) {
            // Usamos el iterador para recorrer la lista de agricultores
            Iterador<Agricultor> iterador = new Iterador<>(agricultores);

            while (iterador.hayElemento()) {
                Agricultor agricultor = iterador.dameValor();
                writer.println(agricultor.getId() + "," + agricultor.getNombre() + "," + agricultor.getPassword());
                iterador.next(); // Avanzamos al siguiente agricultor
            }
            System.out.println("Datos de agricultores guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de agricultores: " + e.getMessage());
        }
    }

    /**
     * Carga los agricultores desde el archivo "agricultores.txt" y los agrega a
     * la lista de agricultores. El archivo debe contener los datos en formato
     * CSV (ID, nombre, password).
     *
     * @param agricultores La lista donde se agregarán los agricultores cargados
     * desde el archivo.
     */
    public static void cargarAgricultoresDesdeArchivo(Lista agricultores) {
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

    /**
     * Guarda la lista de máquinas en un archivo de texto llamado
     * "maquinas.txt". Los datos de cada máquina se almacenan en formato CSV
     * (ID, tipo, modelo, estado).
     *
     * @param maquinas La lista de máquinas a guardar en el archivo.
     */
    public static void guardarMaquinasEnArchivo(Lista maquinas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("maquinas.txt"))) {
            // Usamos el iterador para recorrer la lista de máquinas
            Iterador<Maquina> iterador = new Iterador<>(maquinas);

            while (iterador.hayElemento()) {
                Maquina maquina = iterador.dameValor();
                // Guardar ID, tipo, modelo y estado (convertido a texto)
                writer.println(
                        maquina.getId() + ","
                        + maquina.getTipo() + ","
                        + maquina.getModelo() + ","
                        + maquina.getEstado().name() // Convertir enum Estado a String
                );
                iterador.next(); // Avanzamos al siguiente elemento
            }
            System.out.println("Datos de máquinas guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de máquinas: " + e.getMessage());
        }
    }

    /**
     * Carga las máquinas desde el archivo "maquinas.txt" y las agrega a la
     * lista de máquinas. El archivo debe contener los datos en formato CSV (ID,
     * tipo, modelo, estado).
     *
     * @param maquinas La lista donde se agregarán las máquinas cargadas desde
     * el archivo.
     */
    public static void cargarMaquinasDesdeArchivo(Lista maquinas) {
        try (BufferedReader reader = new BufferedReader(new FileReader("maquinas.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {  // Esperamos 4 datos: id, tipo, modelo, estado
                    int id = Integer.parseInt(datos[0]);  // Convertir ID a número
                    String tipo = datos[1];
                    String modelo = datos[2];

                    // Convertir el estado a un valor del enum Estado
                    try {
                        Maquina.Estado estado = Maquina.Estado.valueOf(datos[3].toLowerCase());
                        Maquina maquina = new Maquina(id, tipo, modelo, estado);
                        maquinas.add(maquina); // Agregar la máquina a la lista
                    } catch (IllegalArgumentException e) {
                        System.out.println("Estado inválido en el archivo para la máquina con ID " + id + ". Se omitirá esta entrada.");
                    }
                }
            }
            System.out.println("Datos de máquinas cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de máquinas no encontrado. Se iniciará con una lista vacía.");
        } catch (IOException e) {
            System.out.println("Error al cargar los datos de máquinas: " + e.getMessage());
        }
    }

    /**
     * Guarda la lista de parcelas en un archivo de texto llamado
     * "parcelas.txt". Los datos de cada parcela se almacenan en formato CSV
     * (ID, ID Agricultor, ubicación, extensión, cultivo).
     *
     * @param parcelas La lista de parcelas a guardar en el archivo.
     */
    public static void guardarParcelasEnArchivo(Lista parcelas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("parcelas.txt"))) {
            // Usamos el iterador para recorrer la lista de parcelas
            Iterador<Parcela> iterador = new Iterador<>(parcelas);

            while (iterador.hayElemento()) {
                Parcela parcela = iterador.dameValor();
                // Escribimos los datos de la parcela en formato CSV incluyendo el ID del agricultor
                writer.println(parcela.getId() + "," + parcela.getAgricultor().getId() + ","
                        + parcela.getUbicacion() + "," + parcela.getExtension() + "," + parcela.getCultivo());
                iterador.next(); // Avanzamos al siguiente elemento
            }
            System.out.println("Datos de parcelas guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de parcelas: " + e.getMessage());
        }
    }

    /**
     * Carga las parcelas desde el archivo "parcelas.txt" y las agrega a la
     * lista de parcelas. El archivo debe contener los datos en formato CSV (ID,
     * ID Agricultor, ubicación, extensión, cultivo).
     *
     * @param parcelas La lista donde se agregarán las parcelas cargadas desde
     * el archivo.
     */
    public static void cargarParcelasDesdeArchivo(Lista parcelas) {
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

    /**
     * Guarda la lista de trabajos en un archivo de texto llamado
     * "trabajos.txt". Los datos de cada trabajo se almacenan en formato CSV
     * (ID, ID Parcela, ID Máquina, tipo de trabajo, fecha inicio, fecha fin).
     * Si alguna de las fechas es nula, se guarda un valor vacío en su lugar.
     *
     * @param trabajos La lista de trabajos a guardar en el archivo.
     */
    public static void guardarTrabajosEnArchivo(Lista trabajos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("trabajos.txt"))) {
            // Usamos el iterador para recorrer la lista de trabajos
            Iterador<Trabajo> iterador = new Iterador<>(trabajos);

            while (iterador.hayElemento()) {
                Trabajo trabajo = iterador.dameValor();

                // Preparamos las fechas para evitar nulls
                String fechaInicioStr = trabajo.getFechaInicio() != null ? trabajo.getFechaInicio().toString() : "";
                String fechaFinStr = trabajo.getFechaFin() != null ? trabajo.getFechaFin().toString() : "";

                // Escribimos los datos del trabajo en formato CSV, incluyendo IDs de parcela y máquina
                writer.println(
                        trabajo.getId() + ","
                        + trabajo.getParcela().getId() + ","
                        + trabajo.getMaquina().getId() + ","
                        + trabajo.getTipo() + ","
                        + fechaInicioStr + ","
                        + fechaFinStr
                );
                iterador.next(); // Avanzamos al siguiente trabajo
            }
            System.out.println("Datos de trabajos guardados correctamente.");
        } catch (IOException e) {
            // Capturamos cualquier error de entrada/salida y mostramos un mensaje detallado
            System.out.println("Error al guardar los datos de trabajos: " + e.getMessage());
        }
    }

    /**
     * Carga los datos de los trabajos desde un archivo CSV llamado
     * "trabajos.txt" y los agrega a la lista proporcionada.
     *
     * Cada línea del archivo debe contener información sobre un trabajo con los
     * siguientes campos separados por comas: - ID del trabajo - ID de la
     * parcela - ID de la máquina - Tipo de trabajo - Fecha de inicio (en
     * formato "yyyy-MM-dd") - Fecha de fin (en formato "yyyy-MM-dd") - puede
     * estar vacía
     *
     * Los trabajos se añaden a la lista de trabajos solo si los datos son
     * válidos y las parcelas y máquinas referenciadas existen en el sistema. Si
     * alguna fecha tiene un formato incorrecto o algún ID de parcela o máquina
     * no se encuentra, el trabajo se omite y se imprime un mensaje de error.
     *
     * @param trabajos La lista donde se agregarán los trabajos cargados desde
     * el archivo.
     */
    public static void cargarTrabajosDesdeArchivo(Lista trabajos) {
        // Definimos el formato esperado de las fechas
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader reader = new BufferedReader(new FileReader("trabajos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Dividimos la línea en partes usando la coma como separador
                String[] datos = linea.split(",");
                if (datos.length == 6) { // Se valida que haya exactamente 6 campos
                    int id = Integer.parseInt(datos[0]);
                    int parcelaId = Integer.parseInt(datos[1]);
                    int maquinaId = Integer.parseInt(datos[2]);
                    String tipo = datos[3];
                    String fechaInicioStr = datos[4];
                    String fechaFinStr = datos[5];

                    // Convertimos las fechas de String a LocalDate usando DateTimeFormatter
                    LocalDate fechaInicio = null;
                    LocalDate fechaFin = null;

                    try {
                        if (!fechaInicioStr.isEmpty()) {
                            fechaInicio = LocalDate.parse(fechaInicioStr, dateFormat); // Convertimos la fecha de inicio
                        }
                        // Solo convertimos fechaFin si no está vacía
                        if (!fechaFinStr.isEmpty()) {
                            fechaFin = LocalDate.parse(fechaFinStr, dateFormat); // Convertimos la fecha de fin
                        }
                    } catch (Exception e) {
                        System.out.println("Formato de fecha inválido para el trabajo con ID: " + id);
                        continue; // Pasamos al siguiente trabajo si hay un error de formato en las fechas
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

}
