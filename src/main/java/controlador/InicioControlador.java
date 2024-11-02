package controlador;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Reporte;
import modeloDAO.ClienteDAO;
import modeloDAO.ProductoDAO;
import modeloDAO.ReporteDAO;
import modeloDAO.VentaDAO;

public class InicioControlador extends HttpServlet {

    private ReporteDAO reporteDao = new ReporteDAO();
    private VentaDAO ventaDao = new VentaDAO();
    private ClienteDAO cliDao = new ClienteDAO();
    private ProductoDAO prodDao = new ProductoDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion") == null ? "" : request.getParameter("accion");

        switch (accion.toLowerCase()) {
            case "principal":
                Principal(request, response);
                break;
            case "reporte1":
                Reporte1(request, response);
                break;
        }
    }

    protected void Reporte1(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        ArrayList<Reporte> lista = reporteDao.TotalDelMes();
        out.print(gson.toJson(lista));

    }

    protected void Principal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("totalGanancia", ventaDao.TotalGananciaDelMes());
        request.setAttribute("totalVentas", ventaDao.TotalVentasDelMes());
        request.setAttribute("cantClientes", cliDao.CantidadClientes());
        request.setAttribute("cantProductos", prodDao.CantidadProductos());
        request.getRequestDispatcher("Inicio.jsp").forward(request, response);
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
