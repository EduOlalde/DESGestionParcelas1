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
    private Agricultor agricultor;
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
    public void asignarAgricultor(Agricultor agricultor) {
        this.agricultor = agricultor;
    }

    public void asignarMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    @Override
    public String toString() {
        return "ID de Trabajo: " + id + "\n"
                + "Parcela: " + (parcela != null ? parcela.toString() : "No asignada") + "\n"
                + "Agricultor: " + (agricultor != null ? agricultor.toString() : "No asignado") + "\n"
                + "Maquina: " + (maquina != null ? maquina.toString() : "No asignada") + "\n"
                + "Tipo de trabajo: " + tipo + "\n"
                + "Fecha de Inicio: " + fechaInicio + "\n"
                + "Fecha de Fin: " + fechaFin;
    }

    // Getters y setters opcionales, si necesitas acceso directo a los atributos
    public int getId() {
        return id;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }

    public Agricultor getAgricultor() {
        return agricultor;
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
}
