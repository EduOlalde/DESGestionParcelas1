package gestionparcelas;

import java.io.Serializable;

/**
 * Clase que representa un agricultor. Esta clase contiene los atributos
 * necesarios para gestionar a los agricultores, como su identificador, nombre y
 * contraseña. Permite obtener y modificar estos atributos, además de
 * proporcionar una representación en forma de cadena del agricultor y una
 * implementación de comparación de objetos.
 *
 * @autor Eduardo Olalde
 */
public class Agricultor implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private final int id;       // Identificador único del agricultor
    private String nombre;      // Nombre del agricultor
    private String password;    // Contraseña del agricultor

    /**
     * Constructor que inicializa un agricultor con los valores proporcionados.
     *
     * @param id El identificador único del agricultor.
     * @param nombre El nombre del agricultor.
     * @param password La contraseña del agricultor.
     */
    public Agricultor(int id, String nombre, String password) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
    }

    /**
     * Obtiene el identificador del agricultor.
     *
     * @return El identificador del agricultor.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre del agricultor.
     *
     * @return El nombre del agricultor.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece un nuevo nombre para el agricultor.
     *
     * @param nombre El nuevo nombre del agricultor.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la contraseña del agricultor.
     *
     * @return La contraseña del agricultor.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece una nueva contraseña para el agricultor.
     *
     * @param password La nueva contraseña del agricultor.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Representa el agricultor como una cadena con su nombre y su ID.
     *
     * @return Una cadena que representa al agricultor con su nombre y su ID.
     */
    @Override
    public String toString() {
        return "ID: " + id + 
                "\tNombre: " + nombre;
    }
}
