package utils;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import modelo.DetalleVenta;

public class Carrito {

    public Carrito() {
    }

    public ArrayList<DetalleVenta> ObtenerSesion(HttpServletRequest request) {
        ArrayList<DetalleVenta> lista;

        if (request.getSession().getAttribute("carrito") == null) {
            lista = new ArrayList<>();
        } else {
            lista = (ArrayList<DetalleVenta>) request.getSession().getAttribute("carrito");
        }
        return lista;
    }

    public void Reiniciar(HttpServletRequest request) {
        ArrayList<DetalleVenta> lista = new ArrayList<>();
        request.getSession().setAttribute("carrito", lista);
        request.getSession().setAttribute("total", TotalPagar(lista));
    }

    public int ExisteProducto(ArrayList<DetalleVenta> lista, int idProd) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getProducto().getIdProd() == idProd) {
                return i;
            }
        }
        return -1;
    }

    public double TotalPagar(ArrayList<DetalleVenta> lista) {
        double total = 0;
        for (DetalleVenta c : lista) {
            total += Utileria.Redondear(c.Total());
        }
        return total;
    }

    public void GuardarSesion(HttpServletRequest request, ArrayList<DetalleVenta> lista) {
        request.getSession().setAttribute("carrito", lista);
        request.getSession().setAttribute("total", TotalPagar(lista));
    }
}
