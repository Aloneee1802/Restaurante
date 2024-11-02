package controlador;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import modelo.*;
import modeloDAO.*;
import security.Encriptador;
import utils.*;

public class ClienteControlador extends HttpServlet {

    private UsuarioDAO usuDao = new UsuarioDAO();
    private ClienteDAO cliDao = new ClienteDAO();

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
        Cliente obj = new Cliente();
        obj.setIdCliente(Integer.parseInt(request.getParameter("id")));

        String msg = cliDao.Mantenimiento(obj, Constantes.ACCION_ELIMINAR);
        out.print(msg);
    }

    protected void Guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Cliente obj = new Cliente();
        obj.setIdUsuario(Integer.parseInt(request.getParameter("id_usuario").trim()));
        obj.setIdCliente(Integer.parseInt(request.getParameter("id_cliente").trim()));
        obj.setCorreo(request.getParameter("correo").trim());

        obj.setEstado(Integer.parseInt(request.getParameter("estado").trim()));
        obj.setNombres(request.getParameter("nombres").trim());
        obj.setApellidos(request.getParameter("apellidos").trim());
        obj.setTipoDoc(request.getParameter("tipoDoc").trim());
        obj.setNroDoc(request.getParameter("nroDoc").trim());
        obj.setTelefono(request.getParameter("telefono").trim());

        String msg = "";

        if (usuDao.ExisteCorreo(obj.getCorreo(), obj.getIdUsuario()) == 0) {
            if (obj.getIdCliente() == 0) {
                String password = request.getParameter("password") == null ? obj.getNroDoc():  request.getParameter("password").trim() ;
                obj.setPassword(Encriptador.Cifrar(password));
                msg = cliDao.Mantenimiento(obj, Constantes.ACCION_GUARDAR);

                // Envio correo de la nueva contraseña
                if (msg.equals("OK")) {
                    obj.setPassword(password);
                    String asunto = "Bienvenido(a) a la familia LITTLE CAESARS";
                    String cuerpo = PlantillaCorreo.EnvioContraseñaNuevoCliente(obj);

                    Correo objCorreo = new Correo();
                    objCorreo.EnviarCorreo(asunto, cuerpo, obj.getCorreo());
                }
            } else {
                msg = cliDao.Mantenimiento(obj, Constantes.ACCION_EDITAR);
            }
        } else {
            msg = "El correo " + obj.getCorreo() + " ya se encuentra en uso";
        }

        out.print(msg);
    }

    protected void Listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ArrayList<Cliente> lista = cliDao.ListarTodos();

        request.setAttribute("lista", lista);
        request.getRequestDispatcher("pages/ListarGestCliente.jsp").forward(request, response);
    }

    protected void Buscar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();

        int id = Integer.parseInt(request.getParameter("id"));
        Cliente obj = cliDao.BuscarPorId(id);

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
