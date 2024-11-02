package controlador;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import modelo.*;
import modeloDAO.*;
import utils.*;

public class AuthControlador extends HttpServlet {

    private UsuarioDAO usuDao = new UsuarioDAO();
    private ApiResponse api = new ApiResponse();
    private Gson gson = new Gson();
    private String pagIndex = "Login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion") == null ? "" : request.getParameter("accion");

        switch (accion.toLowerCase()) {
            case "login":
                Login(request, response);
                break;
            case "generar_codigo":
                GenerarCodigoRecuperacion(request, response);
                break;
            case "validar_codigo":
                ValidarCodigoRecuperacion(request, response);
                break;
            case "actualizar_password":
                ActualizarPassword(request, response);
                break;
            case "logout":
                Logout(request, response);
                break;
        }

    }

    protected void ActualizarPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Usuario obj = new Usuario();
            obj.setPassword(request.getParameter("nueva"));
            obj.setIdUsuario(Integer.parseInt(request.getParameter("id")));

            int res = usuDao.CambiarPassword(obj);

            if (res > 0) {
                api.setMsg("OK");
            } else {
                api.setMsg("No se pudo cambiar contraseña :( Ocurrio un error interno.");
            }
        } catch (Exception e) {
            api.setMsg(e.getMessage());
        }
        out.print(gson.toJson(api));
    }

    protected void ValidarCodigoRecuperacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Usuario obj = new Usuario();
            obj.setCodigo(Integer.parseInt(request.getParameter("codigo")));
            obj.setIdUsuario(Integer.parseInt(request.getParameter("id")));

            api.setMsg(usuDao.ValidarCodigo(obj));
        } catch (Exception e) {
            api.setMsg(e.getMessage());
        }
        out.print(gson.toJson(api));
    }

    protected void GenerarCodigoRecuperacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String correo = request.getParameter("correo");

            Usuario objUsu = usuDao.BuscarPorCorreo(correo);

            if (objUsu != null) {

                objUsu.setCodigo(Utileria.GeneraCodigo());

                int res = usuDao.CrearCodigoRecuperacion(objUsu);

                if (res > 0) {
                    String asunto = "RecuperarContraseña";
                    String cuerpo = PlantillaCorreo.RecuperarContraseña(objUsu);

                    Correo obj = new Correo();
                    obj.EnviarCorreo(asunto, cuerpo, objUsu.getCorreo());

                    api.setData(objUsu);
                    api.setMsg("OK");
                } else {
                    api.setMsg("Ha ocurrido un error al generar codigo de seguridad.");
                }

            } else {
                api.setMsg("No se pudo encontrar correo electrónico.");
            }

        } catch (Exception e) {
            api.setMsg(e.getMessage());
        }
        out.print(gson.toJson(api));
    }

    protected void Login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String correo = request.getParameter("correo").trim();
        String password = request.getParameter("password").trim();
        String ruta = "";

        Usuario obj = usuDao.Login(correo, password);

        if (obj != null) {
            if (obj.getEstado() == 1) {
                request.getSession().setAttribute("usuario", obj);

                if (obj.getIdPerfil() == Constantes.ROL_ADMIN) {
                    response.sendRedirect("Inicio?accion=principal");
                } else if (obj.getIdPerfil() == Constantes.ROL_CLIENTE) {
                    response.sendRedirect("index.jsp");
                }

                return;
            } else {
                request.setAttribute("error", "Su cuenta se encuentra inactiva. Por favor comuniquese con el administrador.");
            }
        } else {
            request.setAttribute("error", "Correo y/o contraseña incorrecto.");
        }

        request.getRequestDispatcher(pagIndex).forward(request, response);
    }

    protected void Logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.getSession().setAttribute("usuario", null);
        response.sendRedirect(pagIndex);
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
