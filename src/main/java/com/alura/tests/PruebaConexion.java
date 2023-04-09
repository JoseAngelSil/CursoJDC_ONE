package com.alura.tests;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.alura.jdbc.factory.ConnectionRefactory;

public class PruebaConexion {
	public static void main(String[] args) throws SQLException {
		Connection con = new ConnectionRefactory().recuperarConexionDB();
		System.out.println("Conexion Exitosa");
		System.out.println("Cerrando conexion");
		con.close(); // cerrar conexion
	}

}
