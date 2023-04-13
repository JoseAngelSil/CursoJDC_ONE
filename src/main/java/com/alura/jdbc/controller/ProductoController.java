package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		PreparedStatement statement = con.prepareStatement("update productos set "
				+ "nombre = ?"
				+ ", descripcion = ?"
				+ ", cantidad = ? "
				+ "where id = ?");
		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3, cantidad);
		statement.setInt(4, id);
		statement.execute();
		
		int updateCount = statement.getUpdateCount();
		con.close();
		return updateCount;
	}

	public Integer eliminar(Integer id) throws SQLException {
		// TODO
		Connection con = new ConnectionRefactory().recuperarConexionDB();
		/**
		 * Se usa prepated statement para tener mas seguridad en el control de los
		 * querys enviados al la base de datos
		 */
		PreparedStatement statement = con.prepareStatement("DELETE FROM productos"
				+ " where id = ?");
		statement.setInt(1, id);
		boolean result = statement.execute();
		
		return statement.getUpdateCount();
		
	}

	public List<Map<String,String>> listar()throws SQLException {
		// TODO
		Connection con = new ConnectionRefactory().recuperarConexionDB();
		PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE"
				+ ", DESCRIPCION"
				+ ", CANTIDAD"
				+ " FROM productos");
		boolean result = statement.execute();
		
		
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
    	PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTOS"
    			+ "(nombre, descripcion, cantidad) "
    			+ " values (?,?,?)",Statement.RETURN_GENERATED_KEYS);
    	statement.setString(1, producto.get("NOMBRE"));
    	statement.setString(2, producto.get("DESCRIPCION"));
    	statement.setInt(3, Integer.valueOf(producto.get("CANTIDAD")));
   
     	boolean exe = statement.execute();
     	ResultSet resultSet = statement.getGeneratedKeys();
     	while(resultSet.next()) {
     		System.out.printf("Fue insertado el producto con ID: %d \n", resultSet.getInt(1));
     		
     	}
     	
    }
}
