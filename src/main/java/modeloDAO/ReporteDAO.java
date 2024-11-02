package modeloDAO;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Producto;
import modelo.Reporte;

public class ReporteDAO {
    private Connection cn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public ArrayList<Reporte> TotalDelMes() {
        ArrayList<Reporte> lista = new ArrayList<>();

        try {
            cn = Conexion.getConexion();
            String sql = "select month(fecha) , sum(total)\n" +
                                "from venta " +
                                "where  YEAR(fecha) = YEAR(NOW()) " +
                                "group by month(fecha);";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Reporte p = new Reporte();
                p.setNroMes(rs.getInt(1));
                p.setTotal(rs.getDouble(2));
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

}
