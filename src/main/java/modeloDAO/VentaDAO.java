package modeloDAO;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.*;
import utils.Constantes;

public class VentaDAO {

    private Connection cn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public String ProcesarVenta(Venta obj) {
        String msg = "";
        int res = 0;

        try {
            String sql = "insert into Venta(id_cliente,fecha,total,estado_entrega) values(?,NOW(),?,?) ";
            cn = Conexion.getConexion();

            ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, obj.getCliente().getIdCliente());
            ps.setDouble(2, obj.getTotal());
            ps.setString(3, Constantes.ESTADO_ENTREGADO);
            res = ps.executeUpdate();

            if (res > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    ps = cn.prepareStatement("{call SP_AGREG_DET_VENTA(?,?,?,?)}");
                    for (DetalleVenta carrito : obj.getDetalles()) {
                        ps.setInt(1, id);
                        ps.setInt(2, carrito.getProducto().getIdProd());
                        ps.setInt(3, carrito.getCantidad());
                        ps.setDouble(4, carrito.getProducto().getPrecio());
                        res += ps.executeUpdate();
                    }

                }

                msg = "OK";
            } else {
                msg = "No se pudo procesar venta";
            }

        } catch (Exception ex) {
            msg = ex.getMessage();
            ex.printStackTrace();
        }
        return msg;
    }
    
      public ArrayList<Venta> BuscarPorCliente(int idCliente) {
        ArrayList<Venta> lista = new ArrayList<>();

        try {
            cn = Conexion.getConexion();
            String sql = "select * from venta v inner join cliente c"
                    + " on c.id_cliente = v.id_cliente"
                    + " where  c.id_cliente  = ? "
                    + " order by fecha desc";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idCliente);
            rs = ps.executeQuery();
            while (rs.next()) {
                Venta v = new Venta();
                Cliente p = new Cliente();
                p.setIdCliente(rs.getInt("id_cliente"));
                p.setNombres(rs.getString("nombres"));
                p.setApellidos(rs.getString("apellidos"));

                v.setNroVenta(rs.getInt("nro_venta"));
                v.setFecha(rs.getString("fecha"));
                v.setTotal(rs.getDouble("total"));
                v.setDireccionEnvio(rs.getString("direccion_envio"));
                v.setEstadoEntrega(rs.getString("estado_entrega"));
                v.setCliente(p);
                v.setDetalles(ListarDetalles(v.getNroVenta()));
                lista.add(v);
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


    public ArrayList<Venta> ConsultarPorFechas(String inicio, String fin) {
        ArrayList<Venta> lista = new ArrayList<>();

        try {
            cn = Conexion.getConexion();
            String sql = "select * from venta v inner join cliente c"
                    + " on c.id_cliente = v.id_cliente"
                    + " where DATE_FORMAT(fecha,'%Y-%m-%d') between ? and ? "
                    + " order by fecha desc";
            ps = cn.prepareStatement(sql);
            ps.setString(1, inicio);
            ps.setString(2, fin);
            rs = ps.executeQuery();
            while (rs.next()) {
                Venta v = new Venta();
                Cliente p = new Cliente();
                p.setIdCliente(rs.getInt("id_cliente"));
                p.setNombres(rs.getString("nombres"));
                p.setApellidos(rs.getString("apellidos"));

                v.setNroVenta(rs.getInt("nro_venta"));
                v.setFecha(rs.getString("fecha"));
                v.setTotal(rs.getDouble("total"));
                v.setDireccionEnvio(rs.getString("direccion_envio"));
                v.setEstadoEntrega(rs.getString("estado_entrega"));
                v.setCliente(p);
                v.setDetalles(ListarDetalles(v.getNroVenta()));
                lista.add(v);
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

    public ArrayList<DetalleVenta> ListarDetalles(int nroVenta) {
        ArrayList<DetalleVenta> lista = new ArrayList<>();

        try {
            Connection cn = Conexion.getConexion();
            String sql = "select nombre , descripcion , cantidad , d.precio\n"
                    + "from Producto p inner join detalle_venta d\n"
                    + "on d.id_prod = p.id_prod\n"
                    + "where nro_venta = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, nroVenta);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetalleVenta v = new DetalleVenta();
                Producto p = new Producto();
                p.setDescripcion(rs.getString("descripcion"));
                p.setNombre(rs.getString("nombre"));
                v.setCantidad(rs.getInt("cantidad"));
                v.setPrecio(rs.getDouble("precio"));
                v.setProducto(p);
                lista.add(v);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public double TotalGananciaDelMes() {
        double res = 0;

        try {
            cn = Conexion.getConexion();
            String sql = "select sum(total) from venta"
                    + " where YEAR(fecha) = YEAR(NOW()) AND MONTH(fecha) = MONTH(NOW()) ";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                res = rs.getDouble(1);
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

    public int TotalVentasDelMes() {
        int res = 0;

        try {
            cn = Conexion.getConexion();
            String sql = "select count(1) from venta"
                    + " where YEAR(fecha) = YEAR(NOW()) AND MONTH(fecha) = MONTH(NOW()) ";
            ps = cn.prepareStatement(sql);
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
}
