package Gestion_CSDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Administrador extends Usuario {
    
    // Lista de usuarios gestionada por el administrador
    private List<Usuario> listaUsuarios;

    // Constructor de la clase Administrador
    public Administrador(int id_Usuario, String nombre, String apellido, String direccion, String telefono, String email, String contraseña, String rol) {
        super(id_Usuario, nombre, apellido, direccion, telefono, email, contraseña, rol);
        this.listaUsuarios = new ArrayList<>();
    }
    
    // Método para crear un nuevo usuario
    public Usuario crearUsuario(int id_Usuario, String nombre, String apellido, String direccion, String telefono, String email, String contraseña, String rol, Connection conn) throws SQLException {
        Usuario nuevoUsuario = new Usuario(id_Usuario, nombre, apellido, direccion, telefono, email, contraseña, rol);
        nuevoUsuario.guardar(conn); // Llama al método de guardar de Usuario
        listaUsuarios.add(nuevoUsuario); // Añade el nuevo usuario a la lista de usuarios
        return nuevoUsuario;
    }
    
    // Método para asignar permisos a un usuario
    public boolean asignar_Permisos(int idUsuario, String nuevoRol, Connection conn) throws SQLException {
        // Verificar si el usuario actual es un administrador
        if (!this.getTipo_Usuario().equalsIgnoreCase("Administrador")) {
            System.out.println("Acción no permitida. Solo los administradores pueden asignar permisos.");
            return false;
        }

        // Consulta SQL para actualizar el rol del usuario
        String sql = "UPDATE gestion_club.usuario SET tipo_usuario = ? WHERE id_usuario = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nuevoRol);
            pstmt.setInt(2, idUsuario);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Retorna verdadero si se actualizó al menos una fila
        }
    }
    
    // Método para modificar el rol de un usuario
    public boolean modificar_Rol(int id_User_Rol, String nuevoRol, Connection conn) throws SQLException {
        // Solo permitir modificar el rol si el usuario actual es Administrador
        if (!this.getTipo_Usuario().equalsIgnoreCase("Administrador")) {
            System.out.println("Acción no permitida. Solo los administradores pueden modificar roles.");
            return false;
        }

        // Consulta SQL para actualizar el rol del usuario
        String sql = "UPDATE gestion_club.usuario SET tipo_usuario = ? WHERE id_usuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoRol);
            stmt.setInt(2, id_User_Rol);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retorna verdadero si se actualizó al menos una fila
        }
    }

    // Método para eliminar un usuario
    public boolean eliminar_Usuario(int id_User_Eliminar, Connection conn) throws SQLException {
        // Solo permitir eliminar el usuario si el rol del actual es Administrador
        if (!this.getTipo_Usuario().equalsIgnoreCase("Administrador")) {
            System.out.println("Acción no permitida. Solo los administradores pueden eliminar usuarios.");
            return false;
        }

        // Consulta SQL para eliminar el usuario
        String sql = "DELETE FROM gestion_club.usuario WHERE id_usuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_User_Eliminar);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retorna verdadero si se eliminó al menos una fila
        }
    }

    // Método para mostrar todos los usuarios
    public void mostrar_Usuarios(Connection conn) throws SQLException {
        // Consulta SQL para obtener todos los usuarios
        String sql = "SELECT id_usuario, nombre, apellido, email, tipo_usuario FROM gestion_club.usuario";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            System.out.println("Lista de Usuarios:");
            // Iterar sobre los resultados de la consulta
            while (rs.next()) {
                int id_usuario = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String tipo_usuario = rs.getString("tipo_usuario");
                // Imprimir los detalles de cada usuario
                System.out.println("ID: " + id_usuario);
                System.out.println("Nombre: " + nombre);
                System.out.println("Apellido: " + apellido);
                System.out.println("Email: " + email);
                System.out.println("Rol: " + tipo_usuario);
                System.out.println("-------------------------");
            }
        }
    }

    // Método estático para recuperar un administrador
    public static Administrador recuperar_Administrador(Connection conn) throws SQLException {
        // Consulta SQL para obtener un administrador
        String query = "SELECT * FROM gestion_club.usuario WHERE tipo_usuario = 'Administrador' LIMIT 1";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                int id_usuario = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                String contraseña = rs.getString("contraseña");
                String tipo_usuario = rs.getString("tipo_usuario");
                // Retornar un nuevo objeto Administrador con los datos obtenidos
                return new Administrador(id_usuario, nombre, apellido, direccion, telefono, email, contraseña, tipo_usuario);
            }
        }
        return null; // Retornar null si no se encuentra un administrador
    }

   // Método para obtener el rol
    public String getRol() {
        return this.tipo_Usuario;
    }
}