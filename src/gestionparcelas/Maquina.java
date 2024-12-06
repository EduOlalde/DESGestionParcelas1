package gestionparcelas;

/**
 *
 * @author Eduardo Olalde
 */
public class Maquina {

    private final int id;
    private String tipo;
    private String modelo;
    private String estado;

    // Constructor
    public Maquina(int id, String tipo, String modelo, String estado) {
        this.id = id;
        this.tipo = tipo;
        this.modelo = modelo;
        this.estado = estado;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public String getEstado() {
        return estado;
    }

    // Setters
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Método para asignar un trabajo a la máquina
    public void asignarTrabajo(Trabajo trabajo) {
        // Aquí puedes añadir la lógica para asignar el trabajo a la máquina
        // Por ejemplo, puedes cambiar el estado de la máquina a "en uso"
        this.estado = "en uso";
        System.out.println(this.tipo + " " + this.modelo + " asignada al trabajo " + trabajo.getId());
    }

    @Override
    public String toString() {
        return "ID de Máquina: " + id + "\n"
                + "Tipo: " + tipo + "\n"
                + "Modelo: " + modelo + "\n"
                + "Estado: " + estado;
    }

}
