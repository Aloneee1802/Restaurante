package modeloDAO;

import config.*;
import java.sql.*;
import java.util.*;
import modelo.*;

public class ProductoDAO {

    private Connection cn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public ArrayList<Producto> ListarTodos() {
        ArrayList<Producto> lista = new ArrayList<>();

        try {
            cn = Conexion.getConexion();
            String sql = "select * from Producto where eliminado = 0";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProd(rs.getInt("id_prod"));
                p.setNombre(rs.getString("nombre"));
                p.setStock(rs.getInt("stock"));
                p.setPrecio(rs.getDouble("precio"));
                p.setPorcDesc(rs.getDouble("porc_desc"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setImagen(rs.getString("imagen"));
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

    public ArrayList<Producto> ListarDisponibles() {
        ArrayList<Producto> lista = new ArrayList<>();

        try {
            cn = Conexion.getConexion();
            String sql = "select * from Producto  where eliminado = 0 and stock > 0";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProd(rs.getInt("id_prod"));
                p.setNombre(rs.getString("nombre"));
                p.setStock(rs.getInt("stock"));
                p.setPrecio(rs.getDouble("precio"));
                p.setPorcDesc(rs.getDouble("porc_desc"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setImagen(rs.getString("imagen"));
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
    
     public ArrayList<Producto> ListarDisponiblesCnDescuento() {
        ArrayList<Producto> lista = new ArrayList<>();

        try {
            cn = Conexion.getConexion();
            String sql = "select * from Producto  "
                    + " where eliminado = 0 and stock > 0 and porc_desc > 0";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProd(rs.getInt("id_prod"));
                p.setNombre(rs.getString("nombre"));
                p.setStock(rs.getInt("stock"));
                p.setPrecio(rs.getDouble("precio"));
                p.setPorcDesc(rs.getDouble("porc_desc"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setImagen(rs.getString("imagen"));
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

    public Producto BuscarPorId(int id) {
        Producto p = null;

        try {
            cn = Conexion.getConexion();
            String sql = "select * from Producto  "
                    + " where id_prod= ?";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new Producto();
                p.setIdProd(rs.getInt("id_prod"));
                p.setNombre(rs.getString("nombre"));
                p.setStock(rs.getInt("stock"));
                p.setPrecio(rs.getDouble("precio"));
                p.setPorcDesc(rs.getDouble("porc_desc"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setImagen(rs.getString("imagen"));
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

    public String Guardar(Producto obj) {
        String msg = "";
        try {
            cn = Conexion.getConexion();
            String sql = "insert into Producto(nombre,stock,precio,descripcion,imagen,porc_desc) values(?,?,?,?,?,?)";
            ps = cn.prepareStatement(sql);
            ps.setString(1, obj.getNombre());
            ps.setInt(2, obj.getStock());
            ps.setDouble(3, obj.getPrecio());
            ps.setString(4, obj.getDescripcion());
            ps.setString(5, obj.getImagen());
            ps.setDouble(6, obj.getPorcDesc());

            msg = ps.executeUpdate() > 0 ? "OK" : "No se pudieron guardar datos";
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

    public String Editar(Producto obj) {
        String msg = "";
        try {
            cn = Conexion.getConexion();
            String sql = "update Producto set nombre=?,stock=?,precio=?,descripcion=?,imagen=?,porc_desc=? "
                    + " where id_prod=?";

            ps = cn.prepareStatement(sql);
            ps.setString(1, obj.getNombre());
            ps.setInt(2, obj.getStock());
            ps.setDouble(3, obj.getPrecio());
            ps.setString(4, obj.getDescripcion());
            ps.setString(5, obj.getImagen());
            ps.setDouble(6, obj.getPorcDesc());
            ps.setInt(7, obj.getIdProd());
            msg = ps.executeUpdate() > 0 ? "OK" : "No se pudieron editar datos";
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

    public String Eliminar(int id) {
        String msg = "";
        try {
            cn = Conexion.getConexion();
            String sql = "update Producto set eliminado = 1 where id_prod=?";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            msg = ps.executeUpdate() > 0 ? "OK" : "No se pudo eliminar registro";
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

      public int CantidadProductos() {
        int res = 0;

        try {
            cn = Conexion.getConexion();
            String sql = "select count(1) from Producto";
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
