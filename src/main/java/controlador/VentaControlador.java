package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Cliente;
import modelo.Usuario;
import modelo.Venta;
import modeloDAO.ClienteDAO;
import modeloDAO.UsuarioDAO;
import modeloDAO.VentaDAO;
import utils.Carrito;

public class VentaControlador extends HttpServlet {

    private UsuarioDAO usuDao = new UsuarioDAO();
    private ClienteDAO cliDao = new ClienteDAO();
    private Carrito carrito = new Carrito();
    private VentaDAO ventaDao = new VentaDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion") == null ? "" : request.getParameter("accion");

        switch (accion.toLowerCase()) {
            case "consultar":
                Consultar(request, response);
                break;
            case "procesar":
                Procesar(request, response);
                break;
            case "historial":
                HistorialMisVentas(request, response);
                break;
        }

    }

    protected void HistorialMisVentas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Usuario usu = (Usuario) request.getSession().getAttribute("usuario");

        if (usu != null) {
            Cliente objCli = cliDao.BuscarPorIdUsuario(usu.getIdUsuario());

            ArrayList<Venta> lista = ventaDao.BuscarPorCliente(objCli.getIdCliente());

            request.setAttribute("lista", lista);
            request.getRequestDispatcher("HistorialMisVentas.jsp").forward(request, response);
        } else {

            request.getSession().setAttribute("error", "No se encuentra autentificado");
            response.sendRedirect("Login.jsp");
        }

    }

    protected void Procesar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Usuario usu = (Usuario) request.getSession().getAttribute("usuario");

        if (usu != null) {
            Cliente objCli = cliDao.BuscarPorIdUsuario(usu.getIdUsuario());
            Venta objVenta = new Venta();
            objVenta.setCliente(objCli);
            objVenta.setDetalles(carrito.ObtenerSesion(request));
            objVenta.setTotal(carrito.TotalPagar(objVenta.getDetalles()));

            String msg = ventaDao.ProcesarVenta(objVenta);

            if (msg.equals("OK")) {
                carrito.Reiniciar(request);
                request.getSession().setAttribute("success", "Venta procesada de forma correcta!");
                response.sendRedirect("Venta?accion=historial");
            } else {
                request.getSession().setAttribute("error", "No se pudo procesar venta: " + msg);
                response.sendRedirect("Carrito.jsp");
            }

        } else {
            request.setAttribute("error", "Debe iniciar sesión para poder generar compra.");

            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }

    }

    protected void Consultar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String inicio = request.getParameter("inicio") != null ? request.getParameter("inicio") : "";
        String fin = request.getParameter("fin") != null ? request.getParameter("fin") : "";

        ArrayList<Venta> lista;
        if (!inicio.equals("") && !fin.equals("")) {
            lista = ventaDao.ConsultarPorFechas(inicio, fin);
        } else {
            lista = new ArrayList<>();
        }

        request.setAttribute("inicio", inicio);
        request.setAttribute("fin", fin);
        request.setAttribute("lista", lista);
        request.getRequestDispatcher("ConsultaVentas.jsp").forward(request, response);

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
