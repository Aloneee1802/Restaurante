package controlador;

import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Cliente;
import modelo.Usuario;
import modelo.Venta;
import modeloDAO.ClienteDAO;
import modeloDAO.UsuarioDAO;
import service.PaymentService;
import utils.Carrito;

public class TransacionControlador extends HttpServlet {

    private UsuarioDAO usuDao = new UsuarioDAO();
    private ClienteDAO cliDao = new ClienteDAO();
    private Carrito carrito = new Carrito();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion") == null ? "" : request.getParameter("accion");

        switch (accion.toLowerCase()) {
            case "autorizar":
                PagoAutorizar(request, response);
                break;
            case "procesar":
                PagoProcesar(request, response);
                break;
        }
    }

    protected void PagoProcesar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("PayerID");

        try {
            PaymentService paymentServices = new PaymentService();
            Payment payment = paymentServices.executePayment(paymentId, payerId);

            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);

            request.setAttribute("payer", payerInfo);
            request.setAttribute("transaction", transaction);

          //  request.getRequestDispatcher("Error.jsp").forward(request, response);
          response.sendRedirect("Venta?accion=procesar");
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("Error.jsp").forward(request, response);
        }
    }

    protected void PagoAutorizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            Usuario usu = (Usuario) request.getSession().getAttribute("usuario");

            if (usu != null) {
                Cliente objCli = cliDao.BuscarPorIdUsuario(usu.getIdUsuario());
                Venta objVenta = new Venta();
                objVenta.setCliente(objCli);
                objVenta.setDetalles(carrito.ObtenerSesion(request));
                objVenta.setTotal(carrito.TotalPagar(objVenta.getDetalles()));

                PaymentService paymentServices = new PaymentService();
                String approvalLink = paymentServices.authorizePayment(objVenta, request);

                response.sendRedirect(approvalLink);
            }else{
                 request.setAttribute("error", "Debe iniciar sesión para poder generar compra.");
                 
                 request.getRequestDispatcher("Login.jsp").forward(request, response);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("Error.jsp").forward(request, response);
        }
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
