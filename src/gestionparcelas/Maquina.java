package gestionparcelas;

/**
 * Clase que representa una máquina agrícola. Cada máquina tiene un ID único, un
 * tipo (como tractor, cosechadora), un modelo y un estado que refleja su
 * disponibilidad.
 *
 * También incluye métodos para modificar sus atributos y asignar trabajos.
 *
 * @author Eduardo Olalde
 */
public class Maquina {

    // Enumeración que define los posibles estados de una máquina.
    public enum Estado {
        libre, // La máquina está disponible para ser asignada.
        asignada, // La máquina está actualmente asignada a un trabajo.
        mantenimiento  // La máquina está en mantenimiento y no está disponible.
    }

    // Atributos
    private final int id;      // Identificador único de la máquina.
    private String tipo;       // Tipo de máquina (e.g., "tractor", "cosechadora").
    private String modelo;     // Modelo de la máquina.
    private Estado estado;     // Estado actual de la máquina.

    /**
     * Constructor para inicializar una máquina con los valores proporcionados.
     *
     * @param id El identificador único de la máquina.
     * @param tipo El tipo de la máquina.
     * @param modelo El modelo de la máquina.
     * @param estado El estado inicial de la máquina.
     */
    public Maquina(int id, String tipo, String modelo, Estado estado) {
        this.id = id;
        this.tipo = tipo;
        this.modelo = modelo;
        this.estado = estado;
    }

    // Métodos Getters
    /**
     * Obtiene el identificador único de la máquina.
     *
     * @return El ID de la máquina.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el tipo de la máquina.
     *
     * @return El tipo de la máquina.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Obtiene el modelo de la máquina.
     *
     * @return El modelo de la máquina.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Obtiene el estado actual de la máquina.
     *
     * @return El estado de la máquina.
     */
    public Estado getEstado() {
        return estado;
    }

    // Métodos Setters
    /**
     * Establece un nuevo tipo para la máquina.
     *
     * @param tipo El nuevo tipo de la máquina.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Establece un nuevo modelo para la máquina.
     *
     * @param modelo El nuevo modelo de la máquina.
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Cambia el estado de la máquina.
     *
     * @param estado El nuevo estado de la máquina.
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    // Métodos adicionales
    /**
     * Asigna un trabajo a la máquina. Cambia el estado de la máquina a
     * "asignada" e imprime un mensaje indicando que la máquina ha sido
     * asignada.
     *
     * @param trabajo El trabajo que se asignará a la máquina.
     */
    public void asignarTrabajo(Trabajo trabajo) {
        // Cambia el estado de la máquina a "asignada".
        this.estado = Estado.asignada;
        System.out.println(this.tipo + " " + this.modelo + " asignada al trabajo " + trabajo.getId());
    }

    /**
     * Devuelve una representación en forma de cadena de la máquina, incluyendo
     * su ID, tipo, modelo y estado actual.
     *
     * @return Una cadena que representa la máquina.
     */
    @Override
    public String toString() {
        return "ID de Máquina: " + id
                + "\tTipo: " + tipo
                + "\tModelo: " + modelo
                + "\tEstado: " + estado;
    }
}
