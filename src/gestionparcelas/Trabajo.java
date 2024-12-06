package gestionparcelas;

import java.time.LocalDate;

/**
 * Clase que representa un trabajo agrícola. Contiene información sobre la
 * parcela, la máquina utilizada, el tipo de trabajo realizado, y las fechas de
 * inicio y fin. Permite gestionar y asignar los diferentes aspectos
 * relacionados con un trabajo.
 *
 * @author Eduardo Olalde
 */
public class Trabajo {

    // Enum para los tipos de trabajo
    public enum tipoAtrabajo {
        arar,
        sembrar,
        cosechar,
        fumigar
    }

    // Atributos
    private final int id;          // Identificador único del trabajo
    private Parcela parcela;       // Parcela asignada al trabajo
    private Maquina maquina;       // Máquina asignada al trabajo
    private tipoAtrabajo tipoTrabajo; // Tipo de trabajo (enum)
    private LocalDate fechaInicio; // Fecha de inicio del trabajo
    private LocalDate fechaFin;    // Fecha de finalización del trabajo

    /**
     * Constructor que inicializa un trabajo con los valores proporcionados.
     *
     * @param id El identificador único del trabajo.
     * @param parcela La parcela donde se realiza el trabajo.
     * @param maquina La máquina asignada al trabajo.
     * @param tipo El tipo de trabajo a realizar.
     * @param fechaInicio La fecha de inicio del trabajo.
     * @param fechaFin La fecha de finalización del trabajo.
     */
    public Trabajo(int id, Parcela parcela, Maquina maquina, tipoAtrabajo tipo, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.parcela = parcela;
        this.maquina = maquina;
        this.tipoTrabajo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters y setters
    /**
     * Obtiene el identificador único del trabajo.
     *
     * @return El identificador del trabajo.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la parcela asignada al trabajo.
     *
     * @return La parcela del trabajo.
     */
    public Parcela getParcela() {
        return parcela;
    }

    /**
     * Asigna una nueva parcela al trabajo.
     *
     * @param parcela La nueva parcela a asignar.
     */
    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }

    /**
     * Obtiene la máquina asignada al trabajo.
     *
     * @return La máquina del trabajo.
     */
    public Maquina getMaquina() {
        return maquina;
    }

    /**
     * Obtiene el tipo de trabajo realizado.
     *
     * @return El tipo de trabajo.
     */
    public tipoAtrabajo getTipo() {
        return tipoTrabajo;
    }

    /**
     * Establece un nuevo tipo de trabajo.
     *
     * @param tipo El nuevo tipo de trabajo.
     */
    public void setTipo(tipoAtrabajo tipo) {
        this.tipoTrabajo = tipo;
    }

    /**
     * Obtiene la fecha de inicio del trabajo.
     *
     * @return La fecha de inicio.
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece una nueva fecha de inicio para el trabajo.
     *
     * @param fechaInicio La nueva fecha de inicio.
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha de finalización del trabajo.
     *
     * @return La fecha de finalización.
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece una nueva fecha de finalización para el trabajo.
     *
     * @param fechaFin La nueva fecha de finalización.
     */
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    // Métodos adicionales
    /**
     * Asigna una nueva máquina al trabajo.
     *
     * @param maquina La nueva máquina a asignar.
     */
    public void asignarMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    /**
     * Devuelve una representación en forma de cadena del trabajo, incluyendo
     * información sobre la parcela, la máquina, el tipo de trabajo, y las
     * fechas.
     *
     * @return Una cadena que representa al trabajo.
     */
    @Override
    public String toString() {
        return "ID de Trabajo: " + id
                + "\tParcela: " + (parcela != null ? parcela.getId() : "No asignada")
                + "\tMáquina: " + (maquina != null ? maquina.getId() : "No asignada")
                + "\tTipo de trabajo: " + (tipoTrabajo != null ? tipoTrabajo.name() : "No asignado")
                + "\tFecha de Inicio: " + (fechaInicio != null ? fechaInicio.toString() : "No asignada")
                + "\tFecha de Fin: " + (fechaFin != null ? fechaFin.toString() : "No asignada");
    }
}
