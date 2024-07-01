package Gestion_CSDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Usuario {
    // Atributos de la clase Usuario
    public int id_Usuario;
    protected String nombre;
    protected String apellido;
    protected String direccion;
    protected String telefono;
    protected String email;
    protected String contraseña;
    protected String tipo_Usuario;

    // Constructor de la clase Usuario
    public Usuario(int id_Usuario, String nombre, String apellido, String direccion, String telefono, String email, String contraseña, String rol) {
        this.id_Usuario = id_Usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.contraseña = contraseña;
        this.tipo_Usuario = rol;
    }

    // Método para autenticar al usuario
    public boolean autenticar_Usuario(String email, String contraseña, Connection conn) throws SQLException {
        // Consulta SQL para verificar el email y la contraseña
        String sql = "SELECT * FROM gestion_club.usuario WHERE email = ? AND contraseña = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, contraseña);
            // Ejecutar la consulta
            try (ResultSet rs = stmt.executeQuery()) {
                // Si hay un resultado, significa que la autenticación fue exitosa
                return rs.next();
            }
        }
    }

    // Método para cambiar la contraseña del usuario
    public void cambiar_Contrasena(String nuevaContraseña, Connection conn) throws SQLException {
        // Consulta SQL para actualizar la contraseña
        String sql = "UPDATE gestion_club.usuario SET contraseña = ? WHERE id_usuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevaContraseña);
            stmt.setInt(2, this.id_Usuario);
            // Ejecutar la actualización
            stmt.executeUpdate();
        }
    }

    // Método estático para listar todos los usuarios
    public static void listar_Usuarios(Connection conn) throws SQLException {
        // Consulta SQL para obtener todos los usuarios
        String query = "SELECT id_usuario, nombre, apellido, email FROM gestion_club.usuario";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            // Iterar sobre los resultados de la consulta
            while (rs.next()) {
                int id_usuario = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                // Imprimir los detalles de cada usuario
                System.out.println("ID: " + id_usuario + ", Nombre: " + nombre + ", Apellido: " + apellido + ", Email: " + email);
            }
        } catch (SQLException e) {
            // Manejo de errores de SQL
            throw new SQLException("Error al listar usuarios: " + e.getMessage());
        }
    }

    // Método estático para obtener un usuario por su ID
    public static Usuario obtenerUsuarioPorId(int idUsuario, Connection conn) throws SQLException {
        // Consulta SQL para obtener un usuario por su ID
        String query = "SELECT * FROM gestion_club.usuario WHERE id_usuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            // Ejecutar la consulta
            try (ResultSet rs = stmt.executeQuery()) {
                // Si se encuentra el usuario, crear un nuevo objeto Usuario
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("contraseña"),
                        rs.getString("tipo_usuario")
                    );
                }
            }
        } catch (SQLException e) {
            // Manejo de errores de SQL
            throw new SQLException("Error al obtener usuario por ID: " + e.getMessage());
        }
        // Retornar null si no se encuentra el usuario
        return null;
    }

    // Método para guardar un nuevo usuario en la base de datos
    public void guardar(Connection conn) throws SQLException {
        // Consulta SQL para insertar un nuevo usuario
        String query = "INSERT INTO gestion_club.usuario (id_usuario, nombre, apellido, direccion, telefono, email, contraseña, tipo_usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, this.id_Usuario);
            stmt.setString(2, this.nombre);
            stmt.setString(3, this.apellido);
            stmt.setString(4, this.direccion);
            stmt.setString(5, this.telefono);
            stmt.setString(6, this.email);
            stmt.setString(7, this.contraseña);
            stmt.setString(8, this.tipo_Usuario);
            // Ejecutar la inserción
            stmt.executeUpdate();
        }
    }

    // Método para actualizar los datos de un usuario existente
    public void actualizar(Connection conn) throws SQLException {
        // Consulta SQL para actualizar los datos de un usuario
        String query = "UPDATE gestion_club.usuario SET nombre = ?, apellido = ?, direccion = ?, telefono = ?, email = ?, contraseña = ?, tipo_usuario = ? WHERE id_usuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, this.nombre);
            stmt.setString(2, this.apellido);
            stmt.setString(3, this.direccion);
            stmt.setString(4, this.telefono);
            stmt.setString(5, this.email);
            stmt.setString(6, this.contraseña);
            stmt.setString(7, this.tipo_Usuario);
            stmt.setInt(8, this.id_Usuario);
            // Ejecutar la actualización
            stmt.executeUpdate();
        }
    }


    // METODOS GETTER Y SETTER

    public int getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(int id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTipo_Usuario() {
        return tipo_Usuario;
    }

    public void setTipo_Usuario(String tipo_Usuario) {
        this.tipo_Usuario = tipo_Usuario;
    }
}

