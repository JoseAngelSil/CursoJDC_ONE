package com.alura.tests;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import com.alura.jdbc.factory.ConnectionRefactory;

public class TestPoolConexion {
	public static void main(String[] args) throws SQLException {
		ConnectionRefactory con1 = new ConnectionRefactory();
		
		for (int i =  1; i <=20; i++) {
			Connection con = con1.recuperarConexionDB();
			System.out.println("Con " + i);
		}
	}
}
