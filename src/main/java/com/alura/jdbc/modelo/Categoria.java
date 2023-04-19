package com.alura.jdbc.modelo;

public class Categoria {
	private int id;
	private String nombre;

	public Categoria(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	
	public int getId() {
		return id;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.nombre;
	}

}
