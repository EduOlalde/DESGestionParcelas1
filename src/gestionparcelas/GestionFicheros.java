
package gestionparcelas;

import ListasTemplates.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 *
 * @author Eduardo Olalde
 */
public class GestionFicheros {
    
    public static void guardarAgricultoresEnArchivo(Lista agricultores) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("agricultores.txt"))) {
            Nodo<Agricultor> nodo = agricultores.getNodoInicial();
            while (nodo != null) {
                Agricultor agricultor = nodo.getInf();
                writer.println(agricultor.getId() + "," + agricultor.getNombre() + "," + agricultor.getPassword());
                nodo = nodo.getSig();
            }
            System.out.println("Datos de agricultores guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de agricultores: " + e.getMessage());
        }
    }

    public static void cargarAgricultoresDesdeArchivo(Lista agricultores) {
        try (BufferedReader reader = new BufferedReader(new FileReader("agricultores.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    int id = Integer.parseInt(datos[0]); // Recoger el id como entero
                    Agricultor agricultor = new Agricultor(id, datos[1], datos[2]);
                    agricultores.add(agricultor);
                }
            }
            System.out.println("Datos de agricultores cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de agricultores no encontrado. Se iniciará con una lista vacía.");
        } catch (IOException e) {
            System.out.println("Error al cargar los datos de agricultores: " + e.getMessage());
        }
    }

    public static void guardarMaquinasEnArchivo(Lista maquinas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("maquinas.txt"))) {
            Nodo<Maquina> nodo = maquinas.getNodoInicial();
            while (nodo != null) {
                Maquina maquina = nodo.getInf();
                // Guardar ID, tipo, modelo y estado (convertido a texto)
                writer.println(
                        maquina.getId() + ","
                        + maquina.getTipo() + ","
                        + maquina.getModelo() + ","
                        + maquina.getEstado().name() // Convertir enum Estado a String
                );
                nodo = nodo.getSig();
            }
            System.out.println("Datos de máquinas guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de máquinas: " + e.getMessage());
        }
    }

    public static void cargarMaquinasDesdeArchivo(Lista maquinas) {
        try (BufferedReader reader = new BufferedReader(new FileReader("maquinas.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {  // Esperamos 4 datos: id, tipo, modelo, estado
                    int id = Integer.parseInt(datos[0]);  // Convertir ID a número
                    String tipo = datos[1];
                    String modelo = datos[2];

                    // Convertir el estado a un valor del enum Estado
                    try {
                        Maquina.Estado estado = Maquina.Estado.valueOf(datos[3].toLowerCase());
                        Maquina maquina = new Maquina(id, tipo, modelo, estado);
                        maquinas.add(maquina);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Estado inválido en el archivo para la máquina con ID " + id + ". Se omitirá esta entrada.");
                    }
                }
            }
            System.out.println("Datos de máquinas cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de máquinas no encontrado. Se iniciará con una lista vacía.");
        } catch (IOException e) {
            System.out.println("Error al cargar los datos de máquinas: " + e.getMessage());
        }
    }

    public static void guardarParcelasEnArchivo(Lista parcelas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("parcelas.txt"))) {
            Nodo<Parcela> nodo = parcelas.getNodoInicial();
            while (nodo != null) {
                Parcela parcela = nodo.getInf();
                // Escribimos los datos de la parcela en formato CSV incluyendo el ID del agricultor
                writer.println(parcela.getId() + "," + parcela.getAgricultor().getId() + ","
                        + parcela.getUbicacion() + "," + parcela.getExtension() + "," + parcela.getCultivo());
                nodo = nodo.getSig();
            }
            System.out.println("Datos de parcelas guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de parcelas: " + e.getMessage());
        }
    }

    public static void cargarParcelasDesdeArchivo(Lista parcelas) {
        try (BufferedReader reader = new BufferedReader(new FileReader("parcelas.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Dividimos la línea en partes usando la coma como separador
                String[] datos = linea.split(",");
                if (datos.length == 5) { // Validamos que haya exactamente 5 campos
                    int id = Integer.parseInt(datos[0]);
                    int idAgricultor = Integer.parseInt(datos[1]);
                    String ubicacion = datos[2];
                    double extension = Double.parseDouble(datos[3]);
                    String cultivo = datos[4];

                    // Buscar al agricultor por su ID
                    Agricultor agricultor = MenuAgricultores.buscarAgricultorPorId(idAgricultor);
                    if (agricultor != null) {
                        // Creamos una nueva parcela con los datos leídos
                        Parcela parcela = new Parcela(id, agricultor, ubicacion, extension, cultivo);
                        parcelas.add(parcela); // Agregamos la parcela a la lista
                    } else {
                        System.out.println("Agricultor con ID " + idAgricultor + " no encontrado. Parcela no cargada.");
                    }
                }
            }
            System.out.println("Datos de parcelas cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de parcelas no encontrado. Se iniciará con una lista vacía.");
        } catch (IOException e) {
            System.out.println("Error al cargar los datos de parcelas: " + e.getMessage());
        }
    }

    public static void guardarTrabajosEnArchivo(Lista trabajos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("trabajos.txt"))) {
            Nodo<Trabajo> nodo = trabajos.getNodoInicial();
            while (nodo != null) {
                Trabajo trabajo = nodo.getInf();
                // Escribimos los datos del trabajo en formato CSV, incluyendo IDs de parcela y máquina
                writer.println(
                        trabajo.getId() + ","
                        + trabajo.getParcela().getId() + ","
                        + trabajo.getMaquina().getId() + ","
                        + trabajo.getTipo() + ","
                        + (trabajo.getFechaInicio() != null ? trabajo.getFechaInicio().toString() : "") + ","
                        + (trabajo.getFechaFin() != null ? trabajo.getFechaFin().toString() : "")
                );
                nodo = nodo.getSig();
            }
            System.out.println("Datos de trabajos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos de trabajos: " + e.getMessage());
        }
    }

    public static void cargarTrabajosDesdeArchivo(Lista trabajos) {
        try (BufferedReader reader = new BufferedReader(new FileReader("trabajos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Dividimos la línea en partes usando la coma como separador
                String[] datos = linea.split(",");
                if (datos.length == 6) { // Validamos que haya exactamente 6 campos
                    int id = Integer.parseInt(datos[0]);
                    int parcelaId = Integer.parseInt(datos[1]);
                    int maquinaId = Integer.parseInt(datos[2]);
                    String tipo = datos[3];
                    String fechaInicioStr = datos[4];
                    String fechaFinStr = datos[5];

                    // Convertimos las fechas de String a Date si no están vacías
                    Date fechaInicio = null;
                    Date fechaFin = null;

                    if (!fechaInicioStr.isEmpty()) {
                        fechaInicio = new Date(fechaInicioStr); // Suponiendo formato "yyyy-MM-dd"
                    }

                    if (!fechaFinStr.isEmpty()) {
                        fechaFin = new Date(fechaFinStr); // Suponiendo formato "yyyy-MM-dd"
                    }

                    // Buscar parcela y máquina por sus IDs
                    Parcela parcela = MenuParcelas.buscarParcelaPorId(parcelaId);
                    Maquina maquina = MenuMaquinas.buscarMaquinaPorId(maquinaId);

                    if (parcela != null && maquina != null) {
                        // Creamos el objeto Trabajo y lo agregamos a la lista
                        Trabajo trabajo = new Trabajo(id, parcela, maquina, tipo, fechaInicio, fechaFin);
                        trabajos.add(trabajo); // Agregamos el trabajo a la lista
                    } else {
                        System.out.println("No se encontró la parcela o la máquina para el trabajo con ID: " + id);
                    }
                }
            }
            System.out.println("Datos de trabajos cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de trabajos no encontrado. Se iniciará con una lista vacía.");
        } catch (IOException e) {
            System.out.println("Error al cargar los datos de trabajos: " + e.getMessage());
        }
    }
    
}
