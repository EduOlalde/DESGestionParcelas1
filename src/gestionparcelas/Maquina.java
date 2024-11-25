
package gestionparcelas;

/**
 *
 * @author Eduardo Olalde
 */
public class Maquina {

    private String tipo;
    private String modelo;
    private String estado;
    

    // Constructor
    public Maquina(String tipo, String modelo, String estado) {
        this.tipo = tipo;
        this.modelo = modelo;
        this.estado = estado;
    }

    // Getters
    public String getTipo() {
        return tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public String getEstado() {
        return estado;
    }

    // Setters (opcional, si necesitas modificar los datos de la máquina)
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

    // Método para mostrar la información de la máquina
    public void mostrarInformacion() {
        System.out.println("Tipo: " + tipo);
        System.out.println("Modelo: " + modelo);
        System.out.println("Estado: " + estado);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Maquina maquina = (Maquina) obj;
        return modelo.equals(maquina.modelo);
    }

}
