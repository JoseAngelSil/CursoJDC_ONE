package com.alura.tests;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.alura.jdbc.factory.ConnectionRefactory;

public class PruebaDelete {
	public static void main(String[] args) throws SQLException 	 {
		Connection con = new ConnectionRefactory().recuperarConexionDB();
		Statement statement =  (Statement) con.createStatement();
		String exe = String.format("delete from productos where ID = %d", 99);
		boolean result = statement.execute(exe);
		System.out.println(statement.getUpdateCount());
	}
}
