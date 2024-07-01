package Gestion_CSDD;

import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Socio extends Persona {

    // Atributos específicos de la clase Socio
    private int id_Socio;
    private String tipo_Socio;

    // Constructor de la clase Socio
    public Socio(int id_Socio, String nombre, String apellido, String direccion, String telefono, String email, String tipo_Socio) {
        super(nombre, apellido, direccion, telefono, email); // Llama al constructor de la clase base (Persona)
        this.id_Socio = id_Socio;
        this.tipo_Socio = tipo_Socio;
    }

    // Método para crear un nuevo socio y guardarlo en la base de datos
    public static Socio crear_Socio(int id_Socio, String nombre, String apellido, String direccion, String telefono, String email, String tipo_Socio, Connection conn) throws SQLException {
        Socio nuevo_Socio = new Socio(id_Socio, nombre, apellido, direccion, telefono, email, tipo_Socio);
        String query = "INSERT INTO gestion_club.socio (id_socio, nombre, apellido, direccion, telefono, email, tipo_socio) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_Socio);
            stmt.setString(2, nombre);
            stmt.setString(3, apellido);
            stmt.setString(4, direccion);
            stmt.setString(5, telefono);
            stmt.setString(6, email);
            stmt.setString(7, tipo_Socio);
            stmt.executeUpdate();
        }
        return nuevo_Socio;
    }

    // Método para modificar un socio existente en la base de datos
    public static void modificar_Socio(int id_socio, String nombre, String apellido, String direccion, String telefono, String email, String tipo_socio, Connection conn) throws SQLException {
        String query = "UPDATE gestion_club.socio SET nombre = ?, apellido = ?, direccion = ?, telefono = ?, email = ?, tipo_socio = ? WHERE id_socio = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, direccion);
            stmt.setString(4, telefono);
            stmt.setString(5, email);
            stmt.setString(6, tipo_socio);
            stmt.setInt(7, id_socio);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se encontró un socio con el ID especificado.");
            }
        }
    }

    // Método para consultar los detalles de un socio por su ID
    public static Socio consultar_Socio(int id_Socio, Connection conn) throws SQLException {
        Socio socioConsultado = null;
        String query = "SELECT * FROM socio WHERE id_socio = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_Socio);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                socioConsultado = new Socio(
                        rs.getInt("id_socio"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("tipo_Socio")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar el socio: " + e.getMessage());
            throw e; // Lanza la excepción para que sea manejada en el método que llama a consultar_Socio
        }
        return socioConsultado;
    }

    // Método para eliminar un socio por su ID
    public static boolean eliminar_Socio(int id_Socio, Connection conn) throws SQLException {
        String query = "DELETE FROM gestion_club.socio WHERE id_socio = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_Socio);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Método para listar todos los socios en la base de datos
    public static List<Socio> listar_Socios(Connection conn) throws SQLException {
        List<Socio> socios = new ArrayList<>();
        String query = "SELECT * FROM gestion_club.socio";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Socio socio = new Socio(
                        rs.getInt("id_socio"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("tipo_Socio")
                );
                socios.add(socio);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar socios: " + e.getMessage());
            throw e; // Lanza la excepción para que sea manejada en el método que llama a listar_Socios
        }
        return socios;
    }

    // Métodos GETTER y SETTER
    
    // Método toString para representar al socio como una cadena de texto
    @Override
    public String toString() {
        return "Socio{" +
                "id=" + id_Socio +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", tipo_Socio='" + tipo_Socio + '\'' +
                '}';
    }

    // Métodos GETTER y SETTER para los atributos específicos de la clase Socio
    public int getId_Socio() {
        return id_Socio;
    }

    public void setId_Socio(int id_Socio) {
        this.id_Socio = id_Socio;
    }

    public String getTipo_Socio() {
        return tipo_Socio;
    }

    public void setTipo_Socio(String tipo_Socio) {
        this.tipo_Socio = tipo_Socio;
    }
}
    
