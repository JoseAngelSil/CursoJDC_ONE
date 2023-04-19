package com.alura.jdbc.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.dao.CategoriaDAO;
import com.alura.jdbc.factory.ConnectionRefactory;
import com.alura.jdbc.modelo.Categoria;

public class CategoriaController {
	
	private CategoriaDAO categoriaDAO;
	
	public CategoriaController() {
		// TODO Auto-generated constructor stub
		var factory = new ConnectionRefactory();
		try {
			this.categoriaDAO = new CategoriaDAO(factory.recuperarConexionDB());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	public List<Categoria> listar() {
		
		return categoriaDAO.listar();
	}

    public List<?> cargaReporte() {
        // TODO
        return new ArrayList<>();
    }
}
