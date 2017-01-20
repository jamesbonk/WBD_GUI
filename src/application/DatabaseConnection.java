package application;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DatabaseConnection {
	
	public DatabaseConnection(){
		
	}
	
	
	private void Connect(){
		try{  
			//step1 load the driver class  
			Class.forName("oracle.jdbc.driver.OracleDriver");  
			  
			//step2 create  the connection object  
			Connection con=DriverManager.getConnection(  
			"jdbc:oracle:thin:@localhost:1521:xe","system","oracle");  
			  
			//step3 create the statement object  
			java.sql.Statement stmt=con.createStatement();  
			  
			//step4 execute query  
			ResultSet rs=((java.sql.Statement) stmt).executeQuery("select * from emp");  
			while(rs.next())  
			System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
			  
			//step5 close the connection object  
			con.close();  
			  
			}catch(Exception e){ System.out.println(e);} 
	}

}
