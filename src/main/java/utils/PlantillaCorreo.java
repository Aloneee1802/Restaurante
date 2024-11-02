package utils;

import java.util.ArrayList;
import modelo.Cliente;
import modelo.Producto;
import modelo.Usuario;

public class PlantillaCorreo {

    public static String RecuperarContraseña(Usuario usuario) {

        String cuerpo = " <html lang='en'>\n"
                + "<head>\n"
                + "    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />\n"
                + "</head>\n"
                + "<body>\n"
                + "    <center>\n"
                + "        <div style='text-align: left; color:black; width: 670px; box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);transition: 0.3s'>\n"
                + "            <div style='padding: 2px 16px;'>\n"
                + "                <div style='background: #121618 !important; padding: 10px; '>\n"
                + "                    <table>\n"
                + "                        <tr>\n"
                + "                            <td>\n"
                + "                                <img style='width: 70px; height: 50px;' src='https://img.freepik.com/vector-premium/pizza-logo-vector_25327-119.jpg'>\n"
                + "                             \n"
                + "                            </td>\n"
                + "                            <td>\n"
                + "                                <span style='font-size: 24px; \n"
                + "                                font-family:LucidaGrande,tahoma,verdana,arial,sans-serif;\n"
                + "                                color: white;'>Pizza Little Caesars</span>\n"
                + "                            </td>\n"
                + "                        </tr>\n"
                + "                    </table>\n"
                + "                </div>\n"
                + "       \n"
                + "                <p>Hola, @@_NOMBRES_COMPLETOS_@@:</p>\n"
                + "                <span style='text-align: justify;'>Recibimos una solicitud para restablecer tu contraseña de\n"
                + "                    Pizza LITTLE CAESARS. Ingresa el siguiente código para restablecer la contraseña:</span><br />\n"
                + "                </span>\n"
                + "              <p style='font-size: 20px; font-weight: bold; width: 30px; font-family:LucidaGrande,tahoma,verdana,arial,sans-serif;padding:10px 29px 12px 10px;background-color:#f2f2f2;border-left:1px solid #ccc;border-right:1px solid #ccc;border-top:1px solid #ccc;border-bottom:1px solid #ccc;text-align:center;border-radius:7px;display:block;border:1px solid #1877f2;background:#e7f3ff'>"
                + "                 @@_CODIGO_@@</p>\n"
                + "                 <p style='font-style: italic;'>Nota: Recuerda que el código solo es valido por los proximos @@_MINUTES_RECUPERAR_@@ minutos. </p>\n"
                + "                <strong>¿No solicitaste este cambio?</strong>\n"
                + "                <p>Si no solicitaste una nueva contraseña, <span  style='color: #1877f2;'>avísanos.</span></p>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "    </center>\n"
                + "</body>\n"
                + "</html>;";

        cuerpo = cuerpo.replaceAll("@@_NOMBRES_COMPLETOS_@@", usuario.getNombresUsu() + " " + usuario.getApellidosUsu());
        cuerpo = cuerpo.replaceAll("@@_CODIGO_@@", String.valueOf(usuario.getCodigo()));
        cuerpo = cuerpo.replaceAll("@@_MINUTES_RECUPERAR_@@", String.valueOf(Constantes.MIN_RECUPERAR_PASSWORD));

        return cuerpo;
    }

    public static String EnvioContraseñaNuevoCliente(Cliente objCliente) {

        String cuerpo = " <html lang='en'>\n"
                + "<head>\n"
                + "    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />\n"
                + "</head>\n"
                + "<body>\n"
                + "    <center>\n"
                + "        <div style='text-align: left; color:black; width: 670px; box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);transition: 0.3s'>\n"
                + "            <div style='padding: 2px 16px;'>\n"
                + "                <div style='background: #121618 !important; padding: 10px; '>\n"
                + "                    <table>\n"
                + "                        <tr>\n"
                + "                            <td>\n"
                + "                                <img style='width: 70px; height: 50px;' src='https://img.freepik.com/vector-premium/pizza-logo-vector_25327-119.jpg'>\n"
                + "                             \n"
                + "                            </td>\n"
                + "                            <td>\n"
                + "                                <span style='font-size: 24px; \n"
                + "                                font-family:LucidaGrande,tahoma,verdana,arial,sans-serif;\n"
                + "                                color: white;'>Pizza Little Caesars</span>\n"
                + "                            </td>\n"
                + "                        </tr>\n"
                + "                    </table>\n"
                + "                </div>\n"
                + "       \n"
                + "                <p>Hola, @@_NOMBRES_COMPLETOS_@@:</p>\n"
                + "                <span style='text-align: justify;'>Te damos la Bienvenida a la familia Pizza LITTLE CAESARS.Te hacemos presente por este medio tus credenciales con el cual podras acceder a nuestro sistema y generar tus ordenes:</span><br />\n"
                + "                </span>\n"
                + "                <p>Correo: @@_CORREO_@@</p>\n"
                + "                <p>Contraseña: @@_PASSWORD_@@</p>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "    </center>\n"
                + "</body>\n"
                + "</html>;";

        cuerpo = cuerpo.replaceAll("@@_NOMBRES_COMPLETOS_@@", objCliente.getNombres() + " " + objCliente.getApellidos());
        cuerpo = cuerpo.replaceAll("@@_CORREO_@@", objCliente.getCorreo());
        cuerpo = cuerpo.replaceAll("@@_PASSWORD_@@", objCliente.getPassword());
        return cuerpo;
    }
    
     public static String EnviarPromociones(ArrayList<Producto> lista) {

         
         
        String cuerpo = " <html lang='en'>\n"
                + "<head>\n"
                + "    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />\n"
                + "</head>\n"
                + "<body>\n"
                + "    <center>\n"
                + "        <div style='text-align: left; color:black; width: 670px; box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);transition: 0.3s'>\n"
                + "            <div style='padding: 2px 16px;'>\n"
                + "                <div style='background: #121618 !important; padding: 10px; '>\n"
                + "                    <table>\n"
                + "                        <tr>\n"
                + "                            <td>\n"
                + "                                <img style='width: 70px; height: 50px;' src='https://img.freepik.com/vector-premium/pizza-logo-vector_25327-119.jpg'>\n"
                + "                             \n"
                + "                            </td>\n"
                + "                            <td>\n"
                + "                                <span style='font-size: 24px; \n"
                + "                                font-family:LucidaGrande,tahoma,verdana,arial,sans-serif;\n"
                + "                                color: white;'>Pizza Little Caesars</span>\n"
                + "                            </td>\n"
                + "                        </tr>\n"
                + "                    </table>\n"
                + "                </div>\n"
                + "       \n"
                + "                <p>Hola, @@_NOMBRES_COMPLETOS_@@:</p>\n"
                + "                <span style='text-align: justify;'>Recibimos una solicitud para restablecer tu contraseña de\n"
                + "                    Pizza LITTLE CAESARS. Ingresa el siguiente código para restablecer la contraseña:</span><br />\n"
                + "                </span>\n"
                + "              <p style='font-size: 20px; font-weight: bold; width: 30px; font-family:LucidaGrande,tahoma,verdana,arial,sans-serif;padding:10px 29px 12px 10px;background-color:#f2f2f2;border-left:1px solid #ccc;border-right:1px solid #ccc;border-top:1px solid #ccc;border-bottom:1px solid #ccc;text-align:center;border-radius:7px;display:block;border:1px solid #1877f2;background:#e7f3ff'>"
                + "                 @@_CODIGO_@@</p>\n"
                + "                 <p style='font-style: italic;'>Nota: Recuerda que el código solo es valido por los proximos @@_MINUTES_RECUPERAR_@@ minutos. </p>\n"
                + "                <strong>¿No solicitaste este cambio?</strong>\n"
                + "                <p>Si no solicitaste una nueva contraseña, <span  style='color: #1877f2;'>avísanos.</span></p>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "    </center>\n"
                + "</body>\n"
                + "</html>;";

        
        return cuerpo;
    }
}
