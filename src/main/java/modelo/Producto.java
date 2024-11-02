package modelo;

public class Producto {

    private int idProd;
    private String nombre;
    private String descripcion;
    private String imagen;
    private int stock;
    private double porcDesc;
    private double precio;

    public double PrecioCnDesc() {
        if (porcDesc > 0) {
            return precio - (precio * (porcDesc / 100.0));
        }
        return precio;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPorcDesc() {
        return porcDesc;
    }

    public void setPorcDesc(double porcDesc) {
        this.porcDesc = porcDesc;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
