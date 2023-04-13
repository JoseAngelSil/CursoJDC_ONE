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
	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
		// TODO
		Connection con = new ConnectionRefactory().recuperarConexionDB();
		PreparedStatement statement = con.prepareStatement(
				"update productos set " + "nombre = ?" + ", descripcion = ?" + ", cantidad = ? " + "where id = ?");
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
		PreparedStatement statement = con.prepareStatement("DELETE FROM productos" + " where id = ?");
		statement.setInt(1, id);
		boolean result = statement.execute();

		return statement.getUpdateCount();

	}

	public List<Map<String, String>> listar() throws SQLException {
		// TODO
		Connection con = new ConnectionRefactory().recuperarConexionDB();
		PreparedStatement statement = con
				.prepareStatement("SELECT ID, NOMBRE" + ", DESCRIPCION" + ", CANTIDAD" + " FROM productos");
		boolean result = statement.execute();

		ResultSet resultSet = statement.getResultSet();
		List<Map<String, String>> resultado = new ArrayList<>();
		while (resultSet.next()) {
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

	public void guardar(Map<String, String> producto) throws SQLException {
		String nombre = producto.get("NOMBRE");
		String descripcion = producto.get("DESCRIPCION");
		Integer cantidad = Integer.valueOf(producto.get("CANTIDAD"));
		Integer cantidadMaxStock = 50;

		// TODO
		Connection con = new ConnectionRefactory().recuperarConexionDB();
		con.setAutoCommit(false);// tomar el control de las trasacciones de ejecucion y evitar errores
		PreparedStatement statement = con.prepareStatement(
				"INSERT INTO PRODUCTOS" + "(nombre, descripcion, cantidad) " + " values (?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
		try {
			do {
				int cantidadAguardar = Math.min(cantidad, cantidadMaxStock);
				execGuardar(nombre, descripcion, cantidadAguardar, statement);
				cantidad -= cantidadMaxStock;
			} while (cantidad > 0);
			con.commit(); // confirmar la ejecucion de guardar cuando no hay problemas
			System.out.println("commit");
		} catch (Exception e) {
			con.rollback(); 
			System.out.println("rollback");
			/*
			 * se realiza este rollback, por lo que deshace los cambios realizados en la trasaccion actual
			 * con la conexion a la base de datos
			 */
		}
		statement.close(); // mantener mas control de este objeto de ejecucion de SQL cerrado con el resultset
		con.close();

	}

	private void execGuardar(String nombre, String descripcion, Integer cantidad, PreparedStatement statement)
			throws SQLException {
		if (cantidad < 50)
			throw new RuntimeException("Ocurrio un error");
		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3, cantidad);

		boolean exe = statement.execute();
		ResultSet resultSet = statement.getGeneratedKeys();
		while (resultSet.next()) {
			System.out.printf("Fue insertado el producto con ID: %d \n", resultSet.getInt(1));

		}
		resultSet.close(); 
		/*
		 * Libera la base de datos y los recursos JDBC de este objeto ResultSet 
		 * inmediatamente en lugar de esperar a que esto ocurra cuando se cierra automáticamente.
		 */
	}
}
