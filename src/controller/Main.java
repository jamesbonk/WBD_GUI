package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.DatabaseConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Negotiation;
import view.*;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	
	private Stage primaryStage;
	private TabPane rootLayout;
	private AnchorPane offersLayout;
	private AnchorPane negotiationsLayout;
	public ObservableList<Negotiation> negotiationsData = FXCollections.observableArrayList();
	private ObservableList<Negotiation> offersData = FXCollections.observableArrayList();
	private DatabaseConnection dbConnection;
	private Connection connection;
	
	private final int NEGOTIATIONS_NUMBER = 1;
	private final int OFFERS_NUMBER = 0;

	public Main() {
		dbConnection = new DatabaseConnection();
		connection = dbConnection.GetConnection();
		dbConnection.setMain(this);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Agency Management");
			initRootLayout();
			loadNegotiations();
			showNegotiationsOverwiew();
			showOffersOverwiew();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public Connection getConnection() {
		return connection;
	}

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../view/RootLayout.fxml"));
			rootLayout = (TabPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void loadNegotiations() {
		Statement stmt = null;
		String query = "select  B.ID_BUDYNKU, D.NAZWA, (P.IMIE|| ' '|| P.NAZWISKO) AS PRACOWNIK, N.STAN,B.TYPNIERUCHOMOSCI,B.ILOSC,B.POWIERZCHNIA,B.MIASTO,B.ULICA,B.NR_BUDYNKU from BUDYNKI B, NEGOCJACJE N, PROJEKTY P, DEWELOPERZY D,PRACOWNICY P where N.ID_BUDYNKU = B.ID_BUDYNKU and N.ID_PRACOWNIKA = P.ID_PRACOWNIKA";
		try {
			stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			List<Negotiation> result = new ArrayList<Negotiation>();
			while (rs.next()) {

				result.add(new Negotiation(rs.getInt("ID_BUDYNKU"), rs.getString("NAZWA"), rs.getString("PRACOWNIK"),
						rs.getString("STAN"), rs.getString("TYPNIERUCHOMOSCI"), rs.getInt("ILOSC"),
						rs.getInt("POWIERZCHNIA"), rs.getString("MIASTO"), rs.getString("ULICA"),
						rs.getInt("NR_BUDYNKU")));
			}
			negotiationsData.addAll(result);
			stmt.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
	}

	public ObservableList<Negotiation> getNegotiationsData() {
		return negotiationsData;
	}

	public void showNegotiationsOverwiew() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../view/AkwizycjaOfert.fxml"));
		try {
			AnchorPane negotiationsOverview = (AnchorPane) loader.load();
			rootLayout.getTabs().get(this.NEGOTIATIONS_NUMBER).setContent(negotiationsOverview);
			NegotiationsController controller = loader.getController();
			controller.setMain(this);
			controller.setDbConnection(this.dbConnection);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showOffersOverwiew() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../view/DopasujOferty.fxml"));
		try {
			AnchorPane offersOverview = (AnchorPane) loader.load();
			rootLayout.getTabs().get(this.OFFERS_NUMBER).setContent(offersOverview);
			OffersController controller = loader.getController();
			controller.setMain(this);
			controller.setDbConnection(this.dbConnection);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
