package gestionparcelas;

import java.util.Date;

/**
 *
 * @author Eduardo Olalde
 */
public class Trabajo {

    // Atributos
    private final int id;
    private Parcela parcela;
    private Maquina maquina;
    private String tipo; // "arar", "sembrar", "cosechar", "fumigar", etc.
    private Date fechaInicio;
    private Date fechaFin;

    // Constructor
    public Trabajo(int id, Parcela parcela, Maquina maquina, String tipo, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.parcela = parcela;
        this.maquina = maquina;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    // Métodos
    public void asignarMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    @Override
    public String toString() {
        return "ID de Trabajo: " + id + "\n"
                + "Parcela: " + (parcela != null ? parcela.getId() : "No asignada") + "\n"
                + "Maquina: " + (maquina != null ? maquina.getId() : "No asignada") + "\n"
                + "Tipo de trabajo: " + tipo + "\n"
                + "Fecha de Inicio: " + fechaInicio + "\n"
                + "Fecha de Fin: " + fechaFin;
    }
}
