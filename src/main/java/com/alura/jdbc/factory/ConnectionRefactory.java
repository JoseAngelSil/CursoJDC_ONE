package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionRefactory {
	private DataSource dataSource;
	
	public ConnectionRefactory() {
		// manejo de pool de conexiones y datasource para la conexion
		var poolDataSource = new ComboPooledDataSource();
		poolDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/control_de_stock?useTimeZone=true&serverTimeZone=UTC");
		poolDataSource.setUser("root");
		poolDataSource.setPassword("");
		poolDataSource.setMaxPoolSize(10); // establecer 10 conexiones como maximo
		this.dataSource = poolDataSource;
	}
	
	public Connection recuperarConexionDB() throws SQLException {
		return this.dataSource.getConnection();
	}
}
