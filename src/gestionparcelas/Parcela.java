package gestionparcelas;

/**
 * Clase que representa una parcela agrícola. Cada parcela tiene un
 * identificador único, un agricultor asociado, una ubicación, una extensión en
 * hectáreas y un cultivo asignado.
 *
 * @author Eduardo Olalde
 */
public class Parcela {

    // Atributos
    private final int id;                // Identificador único de la parcela.
    private final Agricultor agricultor; // Agricultor asociado a la parcela.
    private String ubicacion;            // Ubicación de la parcela.
    private double extension;            // Extensión de la parcela en hectáreas.
    private String cultivo;              // Tipo de cultivo asignado a la parcela.

    /**
     * Constructor que inicializa una nueva parcela con los valores
     * especificados.
     *
     * @param id Identificador único de la parcela.
     * @param agricultor Agricultor asignado a la parcela.
     * @param ubicacion Ubicación geográfica de la parcela.
     * @param extension Extensión de la parcela en hectáreas.
     * @param cultivo Cultivo asignado a la parcela.
     */
    public Parcela(int id, Agricultor agricultor, String ubicacion, double extension, String cultivo) {
        this.id = id;
        this.agricultor = agricultor;
        this.ubicacion = ubicacion;
        this.extension = extension;
        this.cultivo = cultivo;
    }

    // Métodos Getters
    /**
     * Obtiene el identificador único de la parcela.
     *
     * @return El ID de la parcela.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el agricultor asignado a la parcela.
     *
     * @return El agricultor asociado a la parcela.
     */
    public Agricultor getAgricultor() {
        return this.agricultor;
    }

    /**
     * Obtiene la ubicación de la parcela.
     *
     * @return La ubicación de la parcela como una cadena.
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * Obtiene la extensión de la parcela en hectáreas.
     *
     * @return La extensión de la parcela.
     */
    public double getExtension() {
        return extension;
    }

    /**
     * Obtiene el cultivo asignado a la parcela.
     *
     * @return El tipo de cultivo de la parcela.
     */
    public String getCultivo() {
        return cultivo;
    }

    // Métodos Setters
    /**
     * Establece una nueva ubicación para la parcela.
     *
     * @param ubicacion La nueva ubicación de la parcela.
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * Establece una nueva extensión para la parcela en hectáreas.
     *
     * @param extension La nueva extensión de la parcela.
     */
    public void setExtension(double extension) {
        this.extension = extension;
    }

    /**
     * Establece un nuevo tipo de cultivo para la parcela.
     *
     * @param cultivo El nuevo tipo de cultivo de la parcela.
     */
    public void setCultivo(String cultivo) {
        this.cultivo = cultivo;
    }

    /**
     * Devuelve una representación en forma de cadena de la parcela, incluyendo
     * su ID, agricultor asociado, ubicación, extensión y cultivo.
     *
     * @return Una cadena con la información detallada de la parcela.
     */
    @Override
    public String toString() {
        return "ID: " + id +
                "\tAgricultor: " + (agricultor != null ? agricultor.getId() : "No asignado") +
                "\tUbicación: " + ubicacion +
                "\tExtensión: " + extension + " hectáreas" + 
                "\tCultivo: " + cultivo;
    }
}
