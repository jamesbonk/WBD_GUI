package application;

import java.sql.Statement;
import java.sql.Types;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.Main;
import model.Negotiation;
import oracle.jdbc.internal.OracleTypes;

public class DatabaseConnection {

	private Main mainApp;

	public DatabaseConnection() {

	}

	public void loadProperties(Negotiation n) {
		Statement stmt = null;
		String query = "{call update_NIERUCHOMOSCI(?)}";
		CallableStatement callStmt = null;

		try {
			 callStmt = this.mainApp.getConnection().prepareCall(query);
			 callStmt.setInt(1,n.getID_BUDYNKU());
			 callStmt.execute();


		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				callStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		   }
	}
	public void setMain(Main mainApp) {
		this.mainApp = mainApp;
	}

	public Connection GetConnection() {
		Connection connection = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "jamesbonk", "tona101");

		} catch (SQLException e) {
			System.out.println("Connection failed");
			e.printStackTrace();
			return null;
		}

		return connection;
	}

}
