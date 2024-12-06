
package gestionparcelas;

/**
 *
 * @author Eduardo Olalde
 */
public class Parcela {

    private final int id;
    private final Agricultor agricultor;
    private String ubicacion;
    private double extension;
    private String cultivo;

    // Constructor
    public Parcela(int id, Agricultor agricultor, String ubicacion, double extension, String cultivo) {
        this.id = id;
        this.agricultor = agricultor;
        this.ubicacion = ubicacion;
        this.extension = extension;
        this.cultivo = cultivo;
    }

    // Getters
    public int getId() {
        return id;
    }
    
    public Agricultor getAgricultor(){
        return this.agricultor;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public double getExtension() {
        return extension;
    }

    public String getCultivo() {
        return cultivo;
    }

    // Setters 
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setExtension(double extension) {
        this.extension = extension;
    }

    public void setCultivo(String cultivo) {
        this.cultivo = cultivo;
    }
    
    @Override
    public String toString() {
        return "ID: " + id + "\n" +
           "Agricultor: " + (agricultor != null ? agricultor.getId() : "No asignado") + "\n" +
           "Ubicación: " + ubicacion + "\n" +
           "Extensión: " + extension + " hectáreas\n" +
           "Cultivo: " + cultivo;
    }
 
}