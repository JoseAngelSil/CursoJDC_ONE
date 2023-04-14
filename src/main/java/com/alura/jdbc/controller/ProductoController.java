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

		final Connection con = new ConnectionRefactory().recuperarConexionDB();
		try (con) {
			final PreparedStatement statement = con.prepareStatement(
					"update productos set " + "nombre = ?" + ", descripcion = ?" + ", cantidad = ? " + "where id = ?");
			try (statement) {
				statement.setString(1, nombre);
				statement.setString(2, descripcion);
				statement.setInt(3, cantidad);
				statement.setInt(4, id);
				statement.execute();
				int updateCount = statement.getUpdateCount();
				return updateCount;
			}
		}
	}

	public Integer eliminar(Integer id) throws SQLException {

		final Connection con = new ConnectionRefactory().recuperarConexionDB();

		try (con) {
			final PreparedStatement statement = con.prepareStatement("DELETE FROM productos" + " where id = ?");
			try (statement) {
				statement.setInt(1, id);
				statement.execute();

				return statement.getUpdateCount();
			}
		}

	}

	public List<Map<String, String>> listar() throws SQLException {

		final Connection con = new ConnectionRefactory().recuperarConexionDB();

		try (con) {
			final PreparedStatement statement = con
					.prepareStatement("SELECT ID, NOMBRE" + ", DESCRIPCION" + ", CANTIDAD" + " FROM productos");

			try (statement) {
				statement.execute();

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

				return resultado;
			}
		}
	}

	public void guardar(Map<String, String> producto) throws SQLException {

		String nombre = producto.get("NOMBRE");
		String descripcion = producto.get("DESCRIPCION");
		Integer cantidad = Integer.valueOf(producto.get("CANTIDAD"));
		Integer cantidadMaxStock = 50;

		final Connection con = new ConnectionRefactory().recuperarConexionDB();

		try (con) { // es un try-whit-resources
			con.setAutoCommit(false);// tomar el control de las trasacciones de ejecucion y evitar errores

			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO PRODUCTOS" + "(nombre, descripcion, cantidad) " + " values (?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			try (statement) {

				do {
					int cantidadAguardar = Math.min(cantidad, cantidadMaxStock);
					execGuardar(nombre, descripcion, cantidadAguardar, statement);
					cantidad -= cantidadMaxStock;
				} while (cantidad > 0);

				con.commit(); // confirmar la ejecucion de guardar cuando no hay problemas

			} catch (Exception e) {
				con.rollback();

				/*
				 * se realiza este rollback, por lo que deshace los cambios realizados en la
				 * trasaccion actual con la conexion a la base de datos
				 */
			}
			// mantener mas control de este objeto de ejecucion de SQL cerrado con el
			// resultset, pero tambien no es necesario ejecutarlo
			// ya no es necesario cerrar la conecion, con el try-whit-reources lo hace
		}
	}

	private void execGuardar(String nombre, String descripcion, Integer cantidad, PreparedStatement statement)
			throws SQLException {

		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3, cantidad);
		statement.execute();

		final ResultSet resultSet = statement.getGeneratedKeys();
		try (resultSet) {
			while (resultSet.next()) {
				//System.out.printf("Fue insertado el producto con ID: %d \n", resultSet.getInt(1));

			}
		}
	}
}
