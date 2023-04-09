package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionRefactory;

import java.sql.Statement;

public class ProductoController {
	public int modificar(String nombre, String descripcion,Integer cantidad,Integer id) throws SQLException {
		// TODO
		Connection con = new ConnectionRefactory().recuperarConexionDB();
		Statement statement =  (Statement) con.createStatement();
		String exec = String.format("update productos set nombre = '%s'"
				+ ", descripcion = '%s'"
				+ ", cantidad = %s"
				+ " where id = %s", nombre, descripcion, String.valueOf(cantidad), String.valueOf(id));
		statement.execute(exec);
		int updateCount = statement.getUpdateCount();
		con.close();
		return updateCount;
	}

	public Integer eliminar(Integer id) throws SQLException {
		// TODO
		Connection con = new ConnectionRefactory().recuperarConexionDB();
		Statement statement =  (Statement) con.createStatement();
		String exe = String.format("delete from productos where ID = %d", id);
		boolean result = statement.execute(exe);
		
		return statement.getUpdateCount();
		
	}

	public List<Map<String,String>> listar()throws SQLException {
		// TODO
		Connection con = new ConnectionRefactory().recuperarConexionDB();
		Statement statement =  (Statement) con.createStatement();
		boolean result = statement.execute("select ID, NOMBRE, DESCRIPCION,CANTIDAD from productos");
		
		ResultSet resultSet = statement.getResultSet();
		List<Map<String,String>> resultado = new ArrayList<>();
		while(resultSet.next()) {
			Map<String, String> fila = new HashMap<>();
			fila.put("ID", String.valueOf(resultSet.getInt("ID")));
			fila.put("NOMBRE", String.valueOf(resultSet.getString("NOMBRE")));
			fila.put("DESCRIPCION", String.valueOf(resultSet.getString("DESCRIPCION")));
			fila.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));
			resultado.add(fila);
		}
		
		
		con.close();
		return resultado;
	}

    public void guardar(Map<String,String> producto) throws SQLException {
		// TODO
    	Connection con = new ConnectionRefactory().recuperarConexionDB();
     	Statement statement = con.createStatement();
     	String insert = String.format("INSERT INTO PRODUCTOS (nombre, descripcion, cantidad) "
     			+ "VALUES('%s' , '%s', %s)", 
     			producto.get("NOMBRE"), producto.get("DESCRIPCION"), producto.get("CANTIDAD"));
     	boolean exe = statement.execute(insert, Statement.RETURN_GENERATED_KEYS);
     	ResultSet resultSet = statement.getGeneratedKeys();
     	while(resultSet.next()) {
     		System.out.printf("Fue insertado el producto con ID: %d \n", resultSet.getInt(1));
     		
     	}
     	
    }
}
