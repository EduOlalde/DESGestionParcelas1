
package gestionparcelas;

/**
 *
 * @author Eduardo Olalde
 */
public class Parcela {

    private int id;
    private String ubicacion;
    private double extension;
    private String cultivo;

    // Constructor
    public Parcela(int id, String ubicacion, double extension, String cultivo) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.extension = extension;
        this.cultivo = cultivo;
    }

    // Getters
    public int getId() {
        return id;
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

    // Setters (opcional, si necesitas modificar los datos de la parcela)
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setExtension(double extension) {
        this.extension = extension;
    }

    public void setCultivo(String cultivo) {
        this.cultivo = cultivo;
    }

    // Método para asignar un trabajo a la parcela
    public void asignarTrabajo(Trabajo trabajo) {
        // Aquí puedes añadir la lógica para asignar el trabajo a la parcela
        // Por ejemplo, puedes añadir el trabajo a una lista de trabajos de la parcela
        System.out.println("Trabajo " + trabajo.getId() + " asignado a la parcela " + id);
    }

    // Método para mostrar la información de la parcela
    public void mostrarInformacion() {
        System.out.println("ID: " + id);
        System.out.println("Ubicación: " + ubicacion);
        System.out.println("Extensión: " + extension + " hectáreas");
        System.out.println("Cultivo: " + cultivo);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Parcela parcela = (Parcela) obj;
        return id == parcela.id;
}
}