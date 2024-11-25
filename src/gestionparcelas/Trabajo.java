
package gestionparcelas;
import java.util.Date;
/**
 *
 * @author Eduardo Olalde
 */
public class Trabajo {
    
    // Atributos
    private int id;
    private Parcela parcela;
    private Persona agricultor;
    private Maquina maquina;
    private String tipo; // "arar", "sembrar", "cosechar", "fumigar", etc.
    private Date fechaInicio;
    private Date fechaFin;

    // Constructor
    public Trabajo(int id, Parcela parcela, String tipo, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.parcela = parcela;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Métodos
    public void asignarAgricultor(Persona agricultor) {
        this.agricultor = agricultor;
    }

    public void asignarMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public void mostrarInformacion() {
        System.out.println("ID de Trabajo: " + id);
        System.out.println("Parcela: " + (parcela != null ? parcela.toString() : "No asignada"));
        System.out.println("Agricultor: " + (agricultor != null ? agricultor.toString() : "No asignado"));
        System.out.println("Maquina: " + (maquina != null ? maquina.toString() : "No asignada"));
        System.out.println("Tipo de trabajo: " + tipo);
        System.out.println("Fecha de Inicio: " + fechaInicio);
        System.out.println("Fecha de Fin: " + fechaFin);
    }

    // Getters y setters opcionales, si necesitas acceso directo a los atributos
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Parcela getParcela() { return parcela; }
    public void setParcela(Parcela parcela) { this.parcela = parcela; }

    public Persona getAgricultor() { return agricultor; }
    public Maquina getMaquina() { return maquina; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
}
    

