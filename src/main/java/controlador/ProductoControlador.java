package controlador;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import modelo.*;
import modeloDAO.*;

public class ProductoControlador extends HttpServlet {

    private ProductoDAO prodDao = new ProductoDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion") == null ? "" : request.getParameter("accion");

        switch (accion.toLowerCase()) {
            case "listar":
                Listar(request, response);
                break;
            case "guardar":
                Guardar(request, response);
                break;
            case "buscar":
                Buscar(request, response);
                break;
            case "eliminar":
                Eliminar(request, response);
                break;
        }
    }

    protected void Eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));
        String msg = prodDao.Eliminar(id);
        out.print(msg);
    }

    protected void Guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Producto obj = new Producto();

        obj.setIdProd(Integer.parseInt(request.getParameter("id_prod").trim()));
        obj.setNombre(request.getParameter("nombre").trim());
        obj.setPrecio(Double.parseDouble(request.getParameter("precio").trim()));
        obj.setPorcDesc(Double.parseDouble(request.getParameter("descuento").trim()));
        obj.setStock(Integer.parseInt(request.getParameter("stock").trim()));
        obj.setImagen(request.getParameter("imagen").trim());
        obj.setDescripcion(request.getParameter("descripcion").trim());
        String msg = "";

        if (obj.getIdProd() == 0) {
            msg = prodDao.Guardar(obj);
        } else {
            msg = prodDao.Editar(obj);
        }

        out.print(msg);
    }

    protected void Listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ArrayList<Producto> lista = prodDao.ListarTodos();

        request.setAttribute("lista", lista);
        request.getRequestDispatcher("pages/ListarGestProducto.jsp").forward(request, response);
    }

    protected void Buscar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();

        int id = Integer.parseInt(request.getParameter("id"));
        Producto obj = prodDao.BuscarPorId(id);

        out.print(gson.toJson(obj));
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
