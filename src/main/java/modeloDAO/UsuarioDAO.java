package modeloDAO;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.*;
import security.Encriptador;
import utils.Constantes;

public class UsuarioDAO {

    private Connection cn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public int ExisteCorreo(String correo, int id) {
        int res = 0;
        try {
            cn = Conexion.getConexion();
            String sql = "select count(1) from Usuario"
                    + " where correo = ? and (?=0 or id_usuario !=?)";
            ps = cn.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setInt(2, id);
            ps.setInt(3, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
            }
        }
        return res;
    }

    public Usuario Login(String correo, String password) {
        Usuario p = null;

        try {
            cn = Conexion.getConexion();
            String sql = "select * from Usuario  "
                    + " where correo = ? and password = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, Encriptador.Cifrar(password));
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new Usuario();
                p.setIdUsuario(rs.getInt("id_usuario"));
                p.setCorreo(rs.getString("correo"));
                p.setEstado(rs.getInt("estado"));
                p.setIdPerfil(rs.getInt("id_perfil"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
            }
        }
        return p;
    }

    public Usuario BuscarPorCorreo(String correo) {
        Usuario p = null;

        try {
            cn = Conexion.getConexion();
            String sql = "select nombres , apellidos, correo,u.id_usuario from usuario u inner join cliente c on c.id_usuario = u.id_usuario \n"
                    + "where u.correo = ? "
                    + "union "
                    + "select nombres , apellidos, correo,u.id_usuario from usuario u inner join empleado e on e.id_usuario = u.id_usuario \n"
                    + "where u.correo = ? ";
            ps = cn.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, correo);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new Usuario();
                p.setNombresUsu(rs.getString("nombres"));
                p.setApellidosUsu(rs.getString("apellidos"));
                p.setCorreo(rs.getString("correo"));
                p.setIdUsuario(rs.getInt("id_usuario"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
            }
        }
        return p;
    }

    public int CrearCodigoRecuperacion(Usuario obj) {
        int res = 0;
        try {
            cn = Conexion.getConexion();
            String sql = "update usuario set fecha_recuperacion = DATE_ADD(NOW(), INTERVAL ? MINUTE) , codigo_recuperacion = ?"
                    + " where id_usuario = ?";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, Constantes.MIN_RECUPERAR_PASSWORD);
            ps.setInt(2, obj.getCodigo());
            ps.setInt(3, obj.getIdUsuario());
            res = ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
            }
        }
        return res;
    }

    public String ValidarCodigo(Usuario obj) {
        String msg = "";

        try {
            cn = Conexion.getConexion();
            String sql = "{call SP_Validar_CodigoRec(?,?)}";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdUsuario());
            ps.setInt(2, obj.getCodigo());
            rs = ps.executeQuery();

            if (rs.next()) {
                msg = rs.getString(1);
            }
        } catch (Exception ex) {
            msg = ex.getMessage();
            ex.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
            }
        }
        return msg;
    }

    public int CambiarPassword(Usuario obj) {
        int res = 0;
        try {
            cn = Conexion.getConexion();
            String sql = "update usuario set password = ? , codigo_recuperacion = null , fecha_recuperacion = null "
                    + " where id_usuario = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, Encriptador.Cifrar(obj.getPassword()));
            ps.setInt(2, obj.getIdUsuario());
            res = ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
            }
        }
        return res;
    }

}
