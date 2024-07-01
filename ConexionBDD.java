package Gestion_CSDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBDD {
    // Constante que almacena la URL de la base de datos
    private static final String BDD_URL = "jdbc:mysql://localhost:3306/gestion_club";
    // Constante que almacena el nombre de usuario para la conexi�n a la base de datos
    private static final String BDD_USUARIO = "root";
    // Constante que almacena la contrase�a para la conexi�n a la base de datos
    private static final String BDD_PASSWORD = "0219";

    // M�todo para obtener una conexi�n a la base de datos
    public static Connection getConnection() throws SQLException {
        // Utiliza el DriverManager para obtener una conexi�n con las credenciales y URL especificadas
        return DriverManager.getConnection(BDD_URL, BDD_USUARIO, BDD_PASSWORD);
    }
}
