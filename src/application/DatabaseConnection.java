package application;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
	
	public DatabaseConnection(){
		
	}


	public Connection GetConnection() {
		Connection connection = null;

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}

		try
		{
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "jamesbonk", "tona101");

		}
		catch(SQLException e)
		{
			System.out.println("Connection failed");
			e.printStackTrace();
			return null;
		}

		return connection;
	}

}
