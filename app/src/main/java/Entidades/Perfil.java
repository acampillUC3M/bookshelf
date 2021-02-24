package Entidades;

/**
 * Clase que establece las características de los Usuarios
 */
public class Perfil {

    String Nombre;
    String email;
    String password;

    /**
     *
     * @param Nombre
     * @param email
     * @param password
     */
    public Perfil(String Nombre, String email, String password){


        this.Nombre = Nombre;
        this.email = email;
        this.password = password;
    }


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

