package com.alura.jdbc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
	private int id;
	private String nombre;
	private List<Producto> productos;
 
	public Categoria(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	
	public Integer getId() {
		return id;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.nombre;
	}

	public void agregar(Producto producto) {
		// TODO Auto-generated method stub
		if(this.productos == null) {
			this.productos = new ArrayList<>();
		}
		this.productos.add(producto);
	}

	public List<Producto> getProductos() {
		// TODO Auto-generated method stub
		return this.productos;
	}

}
