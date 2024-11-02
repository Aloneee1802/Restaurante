package controlador;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import modelo.*;
import utils.*;
import modeloDAO.*;

public class CarritoControlador extends HttpServlet {

    private ProductoDAO prodDAO = new ProductoDAO();
    private ApiResponse api = new ApiResponse();
    private Carrito carrito = new Carrito();
    private Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion").toLowerCase();

        switch (accion) {
            case "listar":
                Listar(request, response);
                break;
            case "agregar":
                Agregar(request, response);
                break;
            case "eliminar":
                Eliminar(request, response);
                break;
        }
    }

    protected void Agregar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        String msg = "";
        try {
            ArrayList<DetalleVenta> lista = carrito.ObtenerSesion(request);

            int idProd = request.getParameter("id") == null ? 0 : Integer.parseInt(request.getParameter("id"));
            int cantidad = request.getParameter("cantidad") == null ? 0 : Integer.parseInt(request.getParameter("cantidad"));

            Producto p = prodDAO.BuscarPorId(idProd);
            int indice = carrito.ExisteProducto(lista, idProd);

            DetalleVenta c;
            if (indice == -1) {
                c = new DetalleVenta();
                c.setProducto(p);
                c.setCantidad(cantidad);
                lista.add(c);
            } else {
                c = lista.get(indice);
                c.Aumentar(cantidad);
                lista.set(indice, c);
            }

            carrito.GuardarSesion(request, lista);
            api.setMsg("OK");
            api.setData(lista.size());
        } catch (Exception ex) {
            api.setMsg(ex.getMessage());
        }

        out.print(gson.toJson(api));
    }

    protected void Listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.getRequestDispatcher("pages/ListarCarrito.jsp").forward(request, response);
    }

    protected void Eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String msg = "";
        try {
            ArrayList<DetalleVenta> lista = carrito.ObtenerSesion(request);

            int indice = request.getParameter("indice") == null ? -1 : Integer.parseInt(request.getParameter("indice"));

            if (indice >= 0 && indice < lista.size()) {
                DetalleVenta c = lista.get(indice);
                lista.remove(indice);
                carrito.GuardarSesion(request, lista);

                api.setMsg("OK");
                api.setData(lista.size());
            }
        } catch (Exception ex) {
            api.setMsg(ex.getMessage());
        }

        out.print(gson.toJson(api));
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
