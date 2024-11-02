package config;

import java.sql.*;

public class Conexion {

    public static Connection getConexion() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/bdrestaurante";
            String usr = "root";
            String psw = "";
            con = DriverManager.getConnection(url, usr, psw);
            System.out.println("Conexion exitosa!");
        } catch (ClassNotFoundException ex) {
            System.out.println("No hay Driver!!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return con;
    }
}