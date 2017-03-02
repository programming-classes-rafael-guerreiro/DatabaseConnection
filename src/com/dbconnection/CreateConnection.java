package com.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class CreateConnection {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("Unable to locate mysql driver. " + e.getMessage());
			return;
		}

		System.out.println("Driver set up.");

		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_connection_aula2",
				"root", "")) {

			System.out.println("Connected!!");

			PreparedStatement sql = connection.prepareStatement("select * from appliances order by status");
			ResultSet result = sql.executeQuery();

			ResultSetMetaData metadata = result.getMetaData();
			System.out.println("Select contains " + metadata.getColumnCount() + " of columns.");

			for (int column = 1; column <= metadata.getColumnCount(); column++) {
				System.out.println("Column: " + metadata.getColumnLabel(column) + " at index " + column);
			}

			while (result.next()) {
				String name = result.getString("name");
				int status = result.getInt("status");

				System.out.println("Found " + result.getRow() + " line: " + name + " - " + status);
			}
		} catch (SQLException e) {
			System.out.println("Unable to connect to database. " + e.getMessage());
		}
	}
}
