package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Categoria;

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
						Categoria getCategoria = new Categoria((Integer) exeQuery.getInt("id"),(String) exeQuery.getString("nombre"));
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


}
