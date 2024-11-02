package modelo;

public class Usuario extends Perfil {

    private int idUsuario;
    private String correo;
    private String password;
    private String fechaRecuperacion;
    private int estado;
    private String nombresUsu;
    private String apellidosUsu;
    private int codigo;

    public String nomEstado() {
        return estado == 1 ? "Activo" : "Inactivo";
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFechaRecuperacion() {
        return fechaRecuperacion;
    }

    public void setFechaRecuperacion(String fechaRecuperacion) {
        this.fechaRecuperacion = fechaRecuperacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombresUsu() {
        return nombresUsu;
    }

    public void setNombresUsu(String nombresUsu) {
        this.nombresUsu = nombresUsu;
    }

    public String getApellidosUsu() {
        return apellidosUsu;
    }

    public void setApellidosUsu(String apellidosUsu) {
        this.apellidosUsu = apellidosUsu;
    }

}
