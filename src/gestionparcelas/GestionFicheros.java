package gestionparcelas;

import ListasTemplates.*;
import java.io.*;

/**
 * Clase que proporciona métodos genéricos para guardar y cargar listas de
 * objetos en archivos binarios. Permite trabajar con cualquier tipo de objeto
 * siempre que estos implementen la interfaz Serializable.
 *
 * <p>
 * Los métodos están diseñados para ser reutilizables, utilizando genéricos para
 * manejar listas de diferentes tipos de objetos.</p>
 *
 * @author Eduardo Olalde
 */
public class GestionFicheros {

    /**
     * Guarda una lista de objetos en un archivo binario.
     *
     * <p>
     * Este método verifica que todos los objetos en la lista sean serializables
     * antes de escribirlos en el archivo.</p>
     *
     * @param <T> El tipo de los objetos contenidos en la lista.
     * @param lista La lista de objetos a guardar.
     * @param nombreArchivo El nombre del archivo en el que se guardarán los
     * datos.
     * @throws IllegalArgumentException Si alguno de los objetos no es
     * serializable.
     */
    public static <T> void guardarEnArchivo(Lista<T> lista, String nombreArchivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            Iterador<T> iterador = new Iterador<>(lista);

            while (iterador.hayElemento()) {
                T objeto = iterador.dameValor();

                // Verificar si el objeto es serializable
                if (!(objeto instanceof Serializable)) {
                    throw new IllegalArgumentException("El objeto " + objeto + " no es serializable.");
                }

                // Escribir el objeto en el archivo
                out.writeObject(objeto);
                iterador.next();
            }

            System.out.println("Datos guardados correctamente en " + nombreArchivo + ".");
        } catch (IOException e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }

    /**
     * Carga una lista de objetos desde un archivo binario.
     *
     * <p>
     * El método vacía la lista antes de cargar los datos desde el archivo y
     * verifica que los objetos leídos coincidan con el tipo esperado.</p>
     *
     * @param <T> El tipo de los objetos que se espera cargar.
     * @param lista La lista en la que se cargarán los datos.
     * @param nombreArchivo El nombre del archivo desde el que se cargarán los
     * datos.
     * @param claseEsperada La clase de los objetos que se espera cargar.
     */
    public static <T> void cargarDesdeArchivo(Lista<T> lista, String nombreArchivo, Class<T> claseEsperada) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nombreArchivo))) {

            // Vaciar la lista antes de cargar los datos
            lista.vaciarLista();

            // Leer objetos del archivo uno por uno
            while (true) {
                try {
                    Object objeto = in.readObject(); // Leer objeto genérico
                    if (claseEsperada.isInstance(objeto)) {
                        // Comprobar si el objeto es de la clase esperada
                        lista.add((T) objeto); // Agregar a la lista después del cast
                    } else {
                        System.out.println("Objeto no coincide con el tipo esperado: " + objeto.getClass().getName());
                    }
                } catch (EOFException e) {
                    // Fin del archivo alcanzado
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("Error al deserializar un objeto: " + e.getMessage());
                }
            }

            System.out.println("Datos cargados correctamente desde " + nombreArchivo + ".");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado. Se iniciará con una lista vacía.");
        } catch (IOException e) {
            System.out.println("Error al cargar datos desde : " + nombreArchivo + " " + e.getMessage());
        }
    }
}
