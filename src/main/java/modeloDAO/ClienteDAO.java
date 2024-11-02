package modeloDAO;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Cliente;
import security.Encriptador;

public class ClienteDAO {

    private Connection cn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public String Mantenimiento(Cliente obj, int accion) {
        String msg = "";
        try {
            cn = Conexion.getConexion();
            String sql = "{call SP_MANT_Cliente(?,?,?,?,?,?,?,?,?,?)}";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdCliente());
            ps.setString(2, obj.getCorreo());
            ps.setString(3, obj.getPassword() );
            ps.setInt(4, obj.getEstado());
            ps.setString(5, obj.getNombres());
            ps.setString(6, obj.getApellidos());
            ps.setString(7, obj.getTipoDoc());
            ps.setString(8, obj.getNroDoc());
            ps.setString(9, obj.getTelefono());
            ps.setInt(10, accion);
            rs = ps.executeQuery();

            if (rs.next()) {
                msg = rs.getInt(1) > 0 ? "OK" : "error";
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
            } catch (SQLException ex) {
            }
        }
        return msg;
    }

    public ArrayList<Cliente> ListarTodos() {
        ArrayList<Cliente> lista = new ArrayList<>();

        try {
            cn = Conexion.getConexion();
            String sql = "select * from Cliente c inner join Usuario u on u.id_usuario = c.id_usuario";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente p = new Cliente();
                p.setIdCliente(rs.getInt("id_cliente"));
                p.setIdUsuario(rs.getInt("id_usuario"));
                p.setNombres(rs.getString("nombres"));
                p.setApellidos(rs.getString("apellidos"));
                p.setTipoDoc(rs.getString("tipo_doc"));
                p.setNroDoc(rs.getString("nro_doc"));
                p.setTelefono(rs.getString("telefono"));
                p.setCorreo(rs.getString("correo"));
                p.setEstado(rs.getInt("estado"));
                lista.add(p);
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
        return lista;
    }

    public Cliente BuscarPorId(int id) {
        Cliente p = null;

        try {
            cn = Conexion.getConexion();
            String sql = "select * from Cliente c inner join Usuario u "
                    + " on u.id_usuario = c.id_usuario"
                    + " where c.id_cliente = ?";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new Cliente();
                p.setIdCliente(rs.getInt("id_cliente"));
                p.setIdUsuario(rs.getInt("id_usuario"));
                p.setNombres(rs.getString("nombres"));
                p.setApellidos(rs.getString("apellidos"));
                p.setTipoDoc(rs.getString("tipo_doc"));
                p.setNroDoc(rs.getString("nro_doc"));
                p.setTelefono(rs.getString("telefono"));
                p.setCorreo(rs.getString("correo"));
                p.setEstado(rs.getInt("estado"));
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
    
     public Cliente BuscarPorIdUsuario(int id) {
        Cliente p = null;

        try {
            cn = Conexion.getConexion();
            String sql = "select * from Cliente c inner join Usuario u "
                    + " on u.id_usuario = c.id_usuario"
                    + " where c.id_usuario = ?";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new Cliente();
                p.setIdCliente(rs.getInt("id_cliente"));
                p.setIdUsuario(rs.getInt("id_usuario"));
                p.setNombres(rs.getString("nombres"));
                p.setApellidos(rs.getString("apellidos"));
                p.setTipoDoc(rs.getString("tipo_doc"));
                p.setNroDoc(rs.getString("nro_doc"));
                p.setTelefono(rs.getString("telefono"));
                p.setCorreo(rs.getString("correo"));
                p.setEstado(rs.getInt("estado"));
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

    public int CantidadClientes() {
        int res = 0;

        try {
            cn = Conexion.getConexion();
            String sql = "select count(1) from Cliente";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
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

}
