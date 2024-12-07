package gestionparcelas;

import ListasTemplates.*;
import java.io.*;

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
     * Guarda la lista de agricultores en un archivo binario llamado
     * "agricultores.bin". Los objetos agricultores se guardan individualmente.
     *
     * @param agricultores La lista de agricultores a guardar en el archivo
     * binario.
     */
    public static void guardarAgricultoresEnArchivo(Lista agricultores) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("agricultores.bin"))) {
            
            Iterador<Agricultor> iterador = new Iterador<>(agricultores);
            while (iterador.hayElemento()) {
                Agricultor agricultor = iterador.dameValor();
                out.writeObject(agricultor); // Escribe cada agricultor individualmente
                iterador.next();
            }
            System.out.println("Datos de agricultores guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de agricultores: " + e.getMessage());
        }
    }

    /**
     * Carga los agricultores almacenados en un archivo binario llamado
     * "agricultores.bin" y los agrega a la lista proporcionada. Los datos
     * previos en la lista son eliminados.
     *
     * @param agricultores La lista donde se agregarán los agricultores cargados
     * desde el archivo binario.
     */
    @SuppressWarnings("unchecked") // Para evitar advertencias sobre cast
    public static void cargarAgricultoresDesdeArchivo(Lista agricultores) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("agricultores.bin"))) {
            
            agricultores.vaciarLista();

            // Intentamos leer objetos uno por uno hasta que no haya más
            while (true) {
                try {
                    Agricultor agricultor = (Agricultor) in.readObject();
                    agricultores.add(agricultor); 
                } catch (EOFException e) {
                    // Fin del archivo alcanzado
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("Error al deserializar un agricultor: " + e.getMessage());
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
     * Guarda la lista de máquinas en un archivo binario llamado "maquinas.bin".
     * Los objetos máquinas se guardan individualmente.
     *
     * @param maquinas La lista de máquinas a guardar en el archivo binario.
     */
    public static void guardarMaquinasEnArchivo(Lista maquinas) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("maquinas.bin"))) {
            
            Iterador<Maquina> iterador = new Iterador<>(maquinas);
            while (iterador.hayElemento()) {
                Maquina maquina = iterador.dameValor();
                out.writeObject(maquina); // Escribe cada máquina individualmente
                iterador.next();
            }
            System.out.println("Datos de máquinas guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de máquinas: " + e.getMessage());
        }
    }

    /**
     * Carga las máquinas almacenadas en un archivo binario llamado
     * "maquinas.bin" y las agrega a la lista proporcionada. Los datos previos
     * en la lista son eliminados.
     *
     * @param maquinas La lista donde se agregarán las máquinas cargadas desde
     * el archivo binario.
     */
    @SuppressWarnings("unchecked") // Para evitar advertencias sobre cast
    public static void cargarMaquinasDesdeArchivo(Lista maquinas) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("maquinas.bin"))) {
            
            maquinas.vaciarLista();

            // Intentamos leer objetos uno por uno hasta que no haya más
            while (true) {
                try {
                    Maquina maquina = (Maquina) in.readObject();
                    maquinas.add(maquina); 
                } catch (EOFException e) {
                    // Fin del archivo alcanzado
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("Error al deserializar una máquina: " + e.getMessage());
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
     * Guarda la lista de parcelas en un archivo binario llamado "parcelas.bin".
     * Los objetos parcelas se guardan individualmente.
     *
     * @param parcelas La lista de parcelas a guardar en el archivo binario.
     */
    public static void guardarParcelasEnArchivo(Lista parcelas) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("parcelas.bin"))) {
            
            Iterador<Parcela> iterador = new Iterador<>(parcelas);
            while (iterador.hayElemento()) {
                Parcela parcela = iterador.dameValor();
                out.writeObject(parcela); // Escribe cada parcela individualmente
                iterador.next();
            }
            System.out.println("Datos de parcelas guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de parcelas: " + e.getMessage());
        }
    }

    /**
     * Carga las parcelas almacenadas en un archivo binario llamado
     * "parcelas.bin" y las agrega a la lista proporcionada. Los datos previos
     * en la lista son eliminados.
     *
     * @param parcelas La lista donde se agregarán las parcelas cargadas desde
     * el archivo binario.
     */
    @SuppressWarnings("unchecked") // Para evitar advertencias sobre cast
    public static void cargarParcelasDesdeArchivo(Lista parcelas) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("parcelas.bin"))) {
            
            parcelas.vaciarLista();

            // Intentamos leer objetos uno por uno hasta que no haya más
            while (true) {
                try {
                    Parcela parcela = (Parcela) in.readObject();
                    parcelas.add(parcela); 
                } catch (EOFException e) {
                    // Fin del archivo alcanzado
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("Error al deserializar una parcela: " + e.getMessage());
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
     * Guarda la lista de trabajos en un archivo binario llamado "trabajos.bin".
     * Los objetos trabajos se guardan individualmente.
     *
     * @param trabajos La lista de trabajos a guardar en el archivo binario.
     */
    public static void guardarTrabajosEnArchivo(Lista trabajos) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("trabajos.bin"))) {
            
            Iterador<Trabajo> iterador = new Iterador<>(trabajos);
            while (iterador.hayElemento()) {
                Trabajo trabajo = iterador.dameValor();
                out.writeObject(trabajo); // Escribe cada trabajo individualmente
                iterador.next();
            }
            System.out.println("Datos de trabajos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de trabajos: " + e.getMessage());
        }
    }

    /**
     * Carga los trabajos almacenados en un archivo binario llamado
     * "trabajos.bin" y los agrega a la lista proporcionada. Los datos previos
     * en la lista son eliminados.
     *
     * @param trabajos La lista donde se agregarán los trabajos cargados desde
     * el archivo binario.
     */
    @SuppressWarnings("unchecked") // Para evitar advertencias sobre cast
    public static void cargarTrabajosDesdeArchivo(Lista trabajos) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("trabajos.bin"))) {
            
            trabajos.vaciarLista();

            // Intentamos leer objetos uno por uno hasta que no haya más
            while (true) {
                try {
                    Trabajo trabajo = (Trabajo) in.readObject();
                    trabajos.add(trabajo); // Agregar trabajo a la lista
                } catch (EOFException e) {
                    // Fin del archivo alcanzado
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("Error al deserializar un trabajo: " + e.getMessage());
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
