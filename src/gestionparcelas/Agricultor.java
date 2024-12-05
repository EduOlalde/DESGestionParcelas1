package gestionparcelas;

/**
 *
 * @author Eduardo Olalde
 */
public class Agricultor {

    private int id;
    private String nombre;
    private String password;

    public Agricultor(int id, String nombre, String password) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n"
                + "ID: " + id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Agricultor persona = (Agricultor) obj;
        return id == (persona.id);
    }

}
