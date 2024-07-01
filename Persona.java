package Gestion_CSDD;

public abstract class Persona {	
// Variables protegidas que pueden ser accedidas por las subclases
	protected String nombre;
    protected String apellido;
    protected String direccion;
    protected String telefono;
    protected String email; 
    
// Constructor que inicializa los atributos de la persona
    public Persona(String nombre, String apellido, String direccion, String telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;        
    }
    
//METODOS GETTER Y SETTER PARA ACCEDER Y MODIFICAR LOS ATRIBUTOS DE LA PERSONA
    
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

}
