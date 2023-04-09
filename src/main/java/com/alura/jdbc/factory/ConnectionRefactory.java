package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionRefactory {
	public Connection recuperarConexionDB() throws SQLException {
		return DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/control_de_stock?useTimeZone=true&serverTimeZone=UTC",
				"root",
				"");
	}
}
