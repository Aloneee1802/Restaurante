package modelo;

public class DetalleVenta {

    private Producto producto;
    private int cantidad;
    private double precio;

    public void Aumentar(int cant) {
        this.cantidad += cant;

        if (this.cantidad > producto.getStock()) {
            this.cantidad = producto.getStock();
        }
    }

    public double Total() {
        return cantidad * producto.PrecioCnDesc();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    

}
