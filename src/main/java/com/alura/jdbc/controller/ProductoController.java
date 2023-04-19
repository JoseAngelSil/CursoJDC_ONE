package com.alura.jdbc.controller;

import java.sql.SQLException;
import java.util.List;

import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.factory.ConnectionRefactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {
	private ProductoDAO productoDao;

	public ProductoController() {
		var factory = new ConnectionRefactory();
		try {
			this.productoDao = new ProductoDAO(factory.recuperarConexionDB());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
		return productoDao.modificar(nombre, descripcion, cantidad, id);
	}

	public int eliminar(Integer id) {
		return productoDao.eliminar(id);
	}

	public List<Producto> listar() {
		return productoDao.listar();
	}

	public void guardar(Producto producto, Integer categoriaID) {
		producto.setCategoriaID(categoriaID);
		productoDao.guardar(producto);
	}
}
