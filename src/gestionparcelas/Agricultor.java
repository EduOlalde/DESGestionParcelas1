
package gestionparcelas;

/**
 *
 * @author Eduardo Olalde
 */
public class Agricultor {
    
    private String id;
    private String nombre;
    private String password;

    public Agricultor(String id, String nombre, String password, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    
    
    // Método para mostrar la información del agricultor
    public void mostrarInformacion() {
        System.out.println("Nombre: " + nombre);
        System.out.println("id: " + id);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Agricultor persona = (Agricultor) obj;
        return id.equals(persona.id);
    }

    
    
}
