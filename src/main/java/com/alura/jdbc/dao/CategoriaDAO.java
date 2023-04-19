package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;

public class CategoriaDAO {
	final Connection con;

	public CategoriaDAO(Connection con) {
		// TODO Auto-generated constructor stub
		this.con = con;
	}

	public List<Categoria> listar() {
		List<Categoria> resultado = new ArrayList<Categoria>();

		try {
			final PreparedStatement statement = this.con.prepareStatement("Select id, nombre from categoria");
			try (statement) {
				final ResultSet exeQuery = statement.executeQuery();
				try (exeQuery) {
					while (exeQuery.next()) {
						Categoria getCategoria = new Categoria((Integer) exeQuery.getInt("id"),
								(String) exeQuery.getString("nombre"));
						resultado.add((Categoria) getCategoria);
					}
				}
			}

		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}

		return resultado;
	}

	public List<Categoria> listarConProductos() {
		List<Categoria> resultado = new ArrayList<Categoria>();

		try {
			final PreparedStatement statement = this.con
					.prepareStatement("Select c.id, c.nombre, p.id , p.nombre, p.cantidad from categoria as c"
							+ " inner join productos p on c.id = p.categoria_id");
			try (statement) {
				final ResultSet exeQuery = statement.executeQuery();
				try (exeQuery) {
					while (exeQuery.next()) {
						Integer idCategoria = exeQuery.getInt("id");
						String nombreCategoria = exeQuery.getString("nombre");
						var getCategoria = resultado.stream().filter(cat -> cat.getId().equals(idCategoria)).findAny()
								.orElseGet(() -> {
									Categoria cate = new Categoria(idCategoria, nombreCategoria);
									resultado.add(cate);
									return cate;
								});
						Producto producto = new Producto(exeQuery.getInt("p.id"), exeQuery.getString("p.nombre"),
								exeQuery.getInt("p.cantidad"));
						
						getCategoria.agregar(producto);
					}
				}
			}

		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}

		return resultado;
	}

}
