package Gestion_CSDD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conn = null;

        try {
            // Establecer la conexión a la base de datos al iniciar la aplicación
            conn = ConexionBDD.getConnection();
            Administrador nuevoAdmin = Administrador.recuperar_Administrador(conn);
            
            while (true) {
                // Menú principal del sistema
                System.out.println("\n----- Menú Principal -----");
                System.out.println("1. Gestionar Administradores");
                System.out.println("2. Gestionar Usuarios");
                System.out.println("3. Gestionar Socios");
                System.out.println("4. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();  // Consumir nueva línea después de nextInt()

                switch (opcion) {
                    case 1:
                        gestionar_Administradores(scanner, conn, nuevoAdmin);
                        break;
                    case 2:
                        gestionar_Usuarios(scanner, conn, nuevoAdmin);
                        break;
                    case 3:
                        gestionar_Socios(scanner, conn);
                        break;
                    case 4:
                        // Salir del programa
                        System.out.println("Saliendo del sistema...");
                        scanner.close();
                        if (conn != null) {
                            conn.close();
                        }
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        } finally {
            try {
                // Cerrar la conexión a la base de datos al salir
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión a la base de datos: " + e.getMessage());
            }
            scanner.close();
        }
    }

    // Método para gestionar las operaciones relacionadas con los administradores
    private static void gestionar_Administradores(Scanner scanner, Connection conn, Administrador nuevoAdmin) {
        while (true) {
            // Menú de gestión de administradores
            System.out.println("\n--- Menú Administradores ---");
            System.out.println("1. Crear Usuario");
            System.out.println("2. Asignar Permisos");
            System.out.println("3. Modificar rol del Usuario");
            System.out.println("4. Eliminar Usuario");
            System.out.println("5. Mostrar lista de Usuarios");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea después de nextInt()

            switch (opcion) {
                case 1:
                    // Crear nuevo administrador o usuario
                    System.out.print("Ingrese el ID del nuevo Administrador o Usuario: ");
                    int id_Admin = Integer.parseInt(scanner.nextLine());
                    System.out.print("Ingrese el nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese el apellido: ");
                    String apellido = scanner.nextLine();
                    System.out.print("Ingrese la dirección: ");
                    String direccion = scanner.nextLine();
                    System.out.print("Ingrese el teléfono: ");
                    String telefono = scanner.nextLine();
                    System.out.print("Ingrese el email: ");
                    String email = scanner.nextLine();
                    System.out.print("Ingrese la contraseña: ");
                    String contraseña = scanner.nextLine();
                    System.out.print("Ingrese si es Usuario o Administrador: ");
                    String queRol = scanner.nextLine();
                    // Crear instancia de Administrador o Usuario y guardar en la base de datos
                    nuevoAdmin = new Administrador(id_Admin, nombre, apellido, direccion, telefono, email, contraseña, queRol);
                    try {
                        nuevoAdmin.guardar(conn);
                        if (nuevoAdmin.getRol().equals("Administrador")) {
                            System.out.println("Administrador creado exitosamente.");
                        } else {
                            System.out.println("Usuario creado exitosamente");
                        }
                    } catch (SQLException e) {
                        System.err.println("Error al guardar el administrador: " + e.getMessage());
                    }
                    break;

                case 2:
                    // Asignar permisos a un usuario existente
                    if (nuevoAdmin == null) {
                        System.out.println("Primero debe crear un administrador (opción 1).");
                        break;
                    }
                    System.out.print("Ingrese el ID del Usuario a asignar permisos: ");
                    int id_User_Permisos = Integer.parseInt(scanner.nextLine());
                    System.out.print("Ingrese el nuevo rol del Usuario: ");
                    String nuevoRol = scanner.nextLine();
                    try {
                        if (nuevoAdmin.asignar_Permisos(id_User_Permisos, nuevoRol, conn)) {
                            System.out.println("Permisos asignados exitosamente.");
                        } else {
                            System.out.println("Usuario no encontrado.");
                        }
                    } catch (SQLException e) {
                        System.err.println("Error al asignar permisos: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Modificar rol de un usuario existente
                    if (nuevoAdmin == null) {
                        System.out.println("Primero debe crear un administrador (opción 1).");
                        break;
                    }
                    System.out.print("Ingrese el ID del Usuario a modificar el rol: ");
                    int id_User_Rol = Integer.parseInt(scanner.nextLine());
                    System.out.print("Ingrese el nuevo rol del Usuario: ");
                    String nuevoRolUsuario = scanner.nextLine();
                    try {
                        if (nuevoAdmin.modificar_Rol(id_User_Rol, nuevoRolUsuario, conn)) {
                            System.out.println("Rol del usuario modificado exitosamente.");
                        } else {
                            System.out.println("Usuario no encontrado.");
                        }
                    } catch (SQLException e) {
                        System.err.println("Error al modificar el rol: " + e.getMessage());
                    }
                    break;

                case 4:
                    // Eliminar un usuario existente
                    if (nuevoAdmin == null) {
                        System.out.println("Primero debe crear un administrador (opción 1).");
                        break;
                    }
                    System.out.print("Ingrese el ID del Usuario a eliminar: ");
                    int id_User_Eliminar = Integer.parseInt(scanner.nextLine());
                    try {
                        if (nuevoAdmin.eliminar_Usuario(id_User_Eliminar, conn)) {
                            System.out.println("Usuario eliminado exitosamente.");
                        } else {
                            System.out.println("Usuario no encontrado.");
                        }
                    } catch (SQLException e) {
                        System.err.println("Error al eliminar el usuario: " + e.getMessage());
                    }
                    break;

                case 5:
                    // Mostrar lista de usuarios
                    if (nuevoAdmin == null) {
                        System.out.println("Primero debe crear un administrador (opción 1).");
                        break;
                    }
                    try {
                        nuevoAdmin.mostrar_Usuarios(conn);
                    } catch (SQLException e) {
                        System.err.println("Error al mostrar la lista de usuarios: " + e.getMessage());
                    }
                    break;

                case 6:
                    // Volver al menú principal
                    return;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    // Método para gestionar las operaciones relacionadas con los usuarios
    private static void gestionar_Usuarios(Scanner scanner, Connection conn, Usuario usuario_Actual) {
        while (true) {
            // Menú de gestión de usuarios
            System.out.println("\n--- Menú Usuarios ---");
            System.out.println("1. Autenticar Usuario");
            System.out.println("2. Cambiar Contraseña");
            System.out.println("3. Listar Usuarios");
            System.out.println("4. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea después de nextInt()

            switch (opcion) {
                case 1:
                    // Autenticar usuario por email y contraseña
                    System.out.print("Ingrese Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Ingrese Contraseña: ");
                    String contraseña = scanner.nextLine();
                    try {
                        if (usuario_Actual != null && usuario_Actual.autenticar_Usuario(email, contraseña, conn)) {
                            System.out.println("Autenticación exitosa.");
                        } else {
                            System.out.println("Autenticación fallida.");
                        }
                    } catch (SQLException e) {
                        System.err.println("Error al autenticar usuario: " + e.getMessage());
                    }
                    break;

                case 2:
                    // Cambiar contraseña del usuario autenticado
                    System.out.print("Ingrese Nueva Contraseña: ");
                    String nuevaContraseña = scanner.nextLine();
                    try {
                        if (usuario_Actual != null) {
                            usuario_Actual.cambiar_Contrasena(nuevaContraseña, conn);
                            System.out.println("Contraseña cambiada exitosamente.");
                        } else {
                            System.out.println("Debe autenticarse primero.");
                        }
                    } catch (SQLException e) {
                        System.err.println("Error al cambiar contraseña: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Listar todos los usuarios
                    try {
                        Usuario.listar_Usuarios(conn);
                    } catch (SQLException e) {
                        System.err.println("Error al listar usuarios: " + e.getMessage());
                    }
                    break;

                case 4:
                    // Volver al menú principal
                    return;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    // Método para gestionar las operaciones relacionadas con los socios
    private static void gestionar_Socios(Scanner scanner, Connection conn) {
        while (true) {
            // Menú de gestión de socios
            System.out.println("\n--- Menú Socios ---");
            System.out.println("1. Agregar Socio");
            System.out.println("2. Modificar Datos de un Socio");
            System.out.println("3. Consultar por Socio");
            System.out.println("4. Eliminar Socio");
            System.out.println("5. Listado de Socios");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir nueva línea después de nextInt()

            switch (opcion) {
                case 1:
                    // Agregar un nuevo socio
                    System.out.print("Ingrese ID del Socio: ");
                    int id_Socio = Integer.parseInt(scanner.nextLine());
                    System.out.print("Ingrese Nombre del Socio: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese Apellido del Socio: ");
                    String apellido = scanner.nextLine();
                    System.out.print("Ingrese Dirección del Socio: ");
                    String direccion = scanner.nextLine();
                    System.out.print("Ingrese Teléfono del Socio: ");
                    String telefono = scanner.nextLine();
                    System.out.print("Ingrese Email del Socio: ");
                    String email = scanner.nextLine();
                    System.out.print("Ingrese Tipo de Socio: ");
                    String tipo_socio = scanner.nextLine();
                    try {
                        // Crear socio en la base de datos
                        Socio.crear_Socio(id_Socio, nombre, apellido, direccion, telefono, email, tipo_socio, conn);
                        System.out.println("Socio agregado exitosamente.");
                    } catch (SQLException e) {
                        System.err.println("Error al agregar socio: " + e.getMessage());
                    }
                    break;

                case 2:
                    // Modificar datos de un socio existente
                    System.out.print("Ingrese ID del Socio a modificar: ");
                    int id_sociomod = Integer.parseInt(scanner.nextLine());
                    System.out.print("Ingrese Nombre del Socio: ");
                    String nuevo_nombre = scanner.nextLine();
                    System.out.print("Ingrese Apellido del Socio: ");
                    String nuevo_apellido = scanner.nextLine();
                    System.out.print("Ingrese Dirección del Socio: ");
                    String nueva_direccion = scanner.nextLine();
                    System.out.print("Ingrese Teléfono del Socio: ");
                    String nueva_telefono = scanner.nextLine();
                    System.out.print("Ingrese Email del Socio: ");
                    String nuevo_email = scanner.nextLine();
                    System.out.print("Ingrese Tipo de Socio (Full, Protector o Vitalicio: ");
                    String nuevo_tipo_socio = scanner.nextLine();
                    try {
                        // Modificar socio en la base de datos
                        Socio.modificar_Socio(id_sociomod, nuevo_nombre, nuevo_apellido, nueva_direccion, nueva_telefono, nuevo_email, nuevo_tipo_socio, conn);
                        System.out.println("Datos del socio modificados exitosamente.");
                    } catch (SQLException e) {
                        System.err.println("Error al modificar socio: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Consultar datos de un socio por ID
                    System.out.print("Ingrese ID del Socio a consultar: ");
                    int id_socioconsul = Integer.parseInt(scanner.nextLine());
                    try {
                        Socio socioConsultado = Socio.consultar_Socio(id_socioconsul, conn);
                        if (socioConsultado != null) {
                        	 System.out.println("Id Socio: " + id_socioconsul);
                             System.out.println("Nombre: " + socioConsultado.getNombre());
                             System.out.println("Apellido: " + socioConsultado.getApellido());
                             System.out.println("Dirección: " + socioConsultado.getDireccion());
                             System.out.println("Teléfono: " + socioConsultado.getTelefono());
                             System.out.println("Email: " + socioConsultado.getEmail());
                             System.out.println("Tipo de Socio: " + socioConsultado.getTipo_Socio());
                        } else {
                            System.out.println("Socio no encontrado.");
                        }
                    } catch (SQLException e) {
                        System.err.println("Error al consultar socio desde Main: " + e.getMessage());
                    }
                    break;

                case 4:
                    // Eliminar un socio por ID
                    System.out.print("Ingrese ID del Socio a eliminar: ");
                    int id_socioelimin = Integer.parseInt(scanner.nextLine());
                    try {
                        // Eliminar socio de la base de datos
                        if (Socio.eliminar_Socio(id_socioelimin, conn)) {
                            System.out.println("Socio eliminado exitosamente.");
                        } else {
                            System.out.println("Socio no encontrado.");
                        }
                    } catch (SQLException e) {
                        System.err.println("Error al eliminar socio: " + e.getMessage());
                    }
                    break;

                case 5:
                	try {
                        List<Socio> listaSocios = Socio.listar_Socios(conn);
                        if (!listaSocios.isEmpty()) {
                            System.out.println("Listado de Socios:");
                            for (Socio socio : listaSocios) {
                                System.out.println(socio); // Esto llamará al método toString() del objeto Socio
                            }
                        } else {
                            System.out.println("No hay socios registrados.");
                        }
                    } catch (SQLException e) {
                        System.err.println("Error al listar socios: " + e.getMessage());
                    }
                    break;
                case 6:
                    // Volver al menú principal
                    return;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }
}
   
    

