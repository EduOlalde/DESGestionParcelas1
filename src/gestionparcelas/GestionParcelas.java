
package gestionparcelas;
import ListasTemplates.*;
import java.util.Scanner;
/**
 * 
 * @author Eduardo Olalde
 */
public class GestionParcelas {
        
    private static Lista<Persona> personas = new Lista<>();
    private static Lista<Maquina> maquinas = new Lista<>();
    private static Lista<Parcela> parcelas = new Lista<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;
        
        while (!salir) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            switch (opcion) {
                case 1:
                    gestionarPersonas();
                    break;
                case 2:
                    gestionarMaquinas();
                    break;
                case 3:
                    gestionarParcelas();
                    break;
                case 4:
                    listarDatos();
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Gestionar Personas");
        System.out.println("2. Gestionar Máquinas");
        System.out.println("3. Gestionar Parcelas");
        System.out.println("4. Listar todos los datos");
        System.out.println("5. Salir");
        System.out.print("Selecciona una opción: ");
    }

    private static void gestionarPersonas() {
        System.out.println("\n--- GESTIONAR PERSONAS ---");
        System.out.println("1. Alta");
        System.out.println("2. Baja");
        System.out.println("3. Modificación");
        System.out.print("Selecciona una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        switch (opcion) {
            case 1:
                altaPersona();
                break;
            case 2:
                bajaPersona();
                break;
            case 3:
                modificarPersona();
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void altaPersona() {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        Persona persona = new Persona(id, nombre, password, "") {
            @Override
            public void mostrarInformacion() {
                super.mostrarInformacion();
                System.out.println("Especialidad: " + "General");
            }
        };
        personas.add(persona);
        System.out.println("Persona registrada.");
    }

    private static void bajaPersona() {
        System.out.print("ID de la persona a eliminar: ");
        String id = scanner.nextLine();

        // Creamos una persona temporal con el ID a eliminar para usar el método borrarTodos
        Persona personaAEliminar = new Persona(id, "", "", "") {
            @Override
            public void mostrarInformacion() {
                // Método necesario porque Persona es abstracta
            }
        };

        // Intentamos borrar todas las personas con el ID proporcionado
        boolean eliminada = personas.borrarTodos(personaAEliminar);

        if (eliminada) {
            System.out.println("Persona eliminada.");
        } else {
            System.out.println("Persona no encontrada.");
        }
    }

    private static void modificarPersona() {
        System.out.print("ID de la persona a modificar: ");
        String id = scanner.nextLine();

        Persona personaAEncontrar = new Persona(id, "", "", "") {
            @Override
            public void mostrarInformacion() {
                // Método necesario porque Persona es abstracta
            }
        };

        // Buscamos la persona en la lista manual
        Nodo<Persona> nodoActual = personas.getNodoInicial(); // Método que retorna el nodo inicial de la lista
        while (nodoActual != null) {
            if (nodoActual.getInf().equals(personaAEncontrar)) {
                // Persona encontrada, modificar sus datos
                Persona persona = nodoActual.getInf();
                System.out.print("Nuevo nombre: ");
                persona.setNombre(scanner.nextLine());
                System.out.print("Nueva password: ");
                persona.setPassword(scanner.nextLine());
                System.out.println("Persona modificada.");
                return;
            }
            nodoActual = nodoActual.getSig();
        }

        System.out.println("Persona no encontrada.");
    }

    private static void gestionarMaquinas() {
        System.out.println("\n--- GESTIONAR MÁQUINAS ---");
        System.out.println("1. Alta");
        System.out.println("2. Baja");
        System.out.println("3. Modificación");
        System.out.print("Selecciona una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer

        switch (opcion) {
            case 1:
                altaMaquina();
                break;
            case 2:
                bajaMaquina();
                break;
            case 3:
                modificarMaquina();
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void altaMaquina() {
        System.out.print("Tipo: ");
        String tipo = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();

        Maquina maquina = new Maquina(tipo, modelo, estado);
        maquinas.add(maquina);
        System.out.println("Máquina registrada.");
    }

    private static void bajaMaquina() {
        System.out.print("Modelo de la máquina a eliminar: ");
        String modelo = scanner.nextLine();

        // Creamos una máquina temporal para comparar por modelo
        Maquina maquinaAEliminar = new Maquina("", modelo, "");

        // Intentamos borrar todas las máquinas que coincidan con el modelo proporcionado
        boolean eliminada = maquinas.borrarTodos(maquinaAEliminar);

        if (eliminada) {
            System.out.println("Máquina eliminada.");
        } else {
            System.out.println("Máquina no encontrada.");
        }
    }


    private static void modificarMaquina() {
    System.out.print("Modelo de la máquina a modificar: ");
    String modelo = scanner.nextLine();
    
    // Creamos una máquina temporal para buscar por modelo
    Maquina maquinaABuscar = new Maquina("", modelo, "");
    
    // Buscamos manualmente en la lista de máquinas
    Nodo<Maquina> nodoActual = maquinas.getNodoInicial(); // Método que retorna el nodo inicial
        while (nodoActual != null) {
            if (nodoActual.getInf().equals(maquinaABuscar)) {
                // Máquina encontrada, modificar sus datos
                Maquina maquina = nodoActual.getInf();
                System.out.print("Nuevo tipo: ");
                maquina.setTipo(scanner.nextLine());
                System.out.print("Nuevo estado: ");
                maquina.setEstado(scanner.nextLine());
                System.out.println("Máquina modificada.");
                return;
            }
            nodoActual = nodoActual.getSig();
        }

        System.out.println("Máquina no encontrada.");
    }


    private static void gestionarParcelas() {
        System.out.println("\n--- GESTIONAR PARCELAS ---");
        System.out.println("1. Alta");
        System.out.println("2. Baja");
        System.out.println("3. Modificación");
        System.out.print("Selecciona una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer

        switch (opcion) {
            case 1:
                altaParcela();
                break;
            case 2:
                bajaParcela();
                break;
            case 3:
                modificarParcela();
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void altaParcela() {
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Limpiar buffer
        System.out.print("Ubicación: ");
        String ubicacion = scanner.nextLine();
        System.out.print("Extensión: ");
        double extension = scanner.nextDouble();
        scanner.nextLine();  // Limpiar buffer
        System.out.print("Cultivo: ");
        String cultivo = scanner.nextLine();

        Parcela parcela = new Parcela(id, ubicacion, extension, cultivo);
        parcelas.add(parcela);
        System.out.println("Parcela registrada.");
    }

    private static void bajaParcela() {
        System.out.print("ID de la parcela a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        // Creamos una instancia temporal de Parcela para la comparación
        Parcela parcelaAEliminar = new Parcela(id, "", 0, "");

        // Intentamos borrar todas las parcelas que coincidan con el ID proporcionado
        boolean eliminada = parcelas.borrarTodos(parcelaAEliminar);

        if (eliminada) {
            System.out.println("Parcela eliminada.");
        } else {
            System.out.println("Parcela no encontrada.");
        }
    }


    private static void modificarParcela() {
        System.out.print("ID de la parcela a modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        // Creamos una instancia temporal para buscar la parcela por ID
        Parcela parcelaABuscar = new Parcela(id, "", 0, "");

        // Buscamos manualmente en la lista de parcelas
        Nodo<Parcela> nodoActual = parcelas.getNodoInicial(); // Método que retorna el nodo inicial de la lista
        while (nodoActual != null) {
            if (nodoActual.getInf().equals(parcelaABuscar)) {
                // Parcela encontrada, modificar sus datos
                Parcela parcela = nodoActual.getInf();
                System.out.print("Nueva ubicación: ");
                parcela.setUbicacion(scanner.nextLine());
                System.out.print("Nueva extensión: ");
                parcela.setExtension(scanner.nextDouble());
                scanner.nextLine(); // Limpiar buffer
                System.out.print("Nuevo cultivo: ");
                parcela.setCultivo(scanner.nextLine());
                System.out.println("Parcela modificada.");
                return;
            }
            nodoActual = nodoActual.getSig();
        }

        System.out.println("Parcela no encontrada.");
    }


    private static void listarDatos() {
        System.out.println("\n--- LISTADO ---");

        System.out.println("\nPersonas:");
        Nodo<Persona> nodoPersona = personas.getNodoInicial();
        while (nodoPersona != null) {
            nodoPersona.getInf().mostrarInformacion();
            nodoPersona = nodoPersona.getSig();
        }

        System.out.println("\nMáquinas:");
        Nodo<Maquina> nodoMaquina = maquinas.getNodoInicial();
        while (nodoMaquina != null) {
            nodoMaquina.getInf().mostrarInformacion();
            nodoMaquina = nodoMaquina.getSig();
        }

        System.out.println("\nParcelas:");
        Nodo<Parcela> nodoParcela = parcelas.getNodoInicial();
        while (nodoParcela != null) {
            nodoParcela.getInf().mostrarInformacion();
            nodoParcela = nodoParcela.getSig();
        }
    }

    

    
}
