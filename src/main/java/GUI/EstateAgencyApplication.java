package GUI;
import DatabaseConnection.OracleConnection;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.Connection;


public class EstateAgencyApplication extends Application{
	public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		OracleConnection orclConnection = new OracleConnection();
		Connection conn = orclConnection.GetConnection();

		if(conn != null)
			System.out.print("udalo sie");
			conn.close();
		
	}

}
