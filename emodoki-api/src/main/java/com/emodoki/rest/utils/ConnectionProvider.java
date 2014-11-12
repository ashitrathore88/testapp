package com.emodoki.rest.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
	 public Connection getConnection() {
		  Connection con = null;
		  try {
		   Class.forName("org.postgresql.Driver");
		   con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/emodoki", "postgres", "postgres");
		   return con;

		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		  return con;
		 }

		 public static void closeConnection(Connection connection) {
		  try {
		   connection.close();
		  } catch (SQLException e) {
		   e.printStackTrace();
		  }
		 }
}
