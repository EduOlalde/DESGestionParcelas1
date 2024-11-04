/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionparcelas;

/**
 *
 * @author Eduardo Olalde
 */
public class Persona {
    
    private String id;
    private String nombre;
    private String password;
    private String especialidad;

    public Persona(String id, String nombre, String password, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.especialidad = especialidad;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    
    // Método para mostrar la información del agricultor
    public void mostrarInformacion() {
        System.out.println("Nombre: " + nombre);
        System.out.println("id: " + id);
        System.out.println("Especialidad: " + especialidad);
    }
    
    
}
