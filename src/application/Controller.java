package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.sql.Statement;
import java.sql.Connection;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tomasz Motyka on 2017-01-20.
 */
public class Controller {

	private DatabaseConnection dbConnection;
	Connection connection;

	@FXML
	private ComboBox<String> preferencjeSelectBox;
	@FXML
	private ChoiceBox<String> klientSelectBox;
	@FXML
	private TableView negocjacjeTable = new TableView();
	@FXML
	TableColumn idBudynkuColumn = new TableColumn("ID BUDYNKU");
	@FXML
	TableColumn deweloperColumn = new TableColumn("DEWELOPER");
	@FXML
	TableColumn pracownikColumn = new TableColumn("PRACOWNIK");
	@FXML
	TableColumn stanColumn = new TableColumn("STAN");
	@FXML
	TableColumn typColumn = new TableColumn("TYP");
	@FXML
	TableColumn iloscColumn = new TableColumn("ILOSC");
	@FXML
	TableColumn powColumn = new TableColumn("POW.");
	@FXML
	TableColumn miastoColumn = new TableColumn("MIASTO");
	@FXML
	TableColumn ulicaColumn = new TableColumn("ULICA");
	@FXML
	TableColumn nrBudynkuColumn = new TableColumn("NR. BUDYNKU");
	

	public Controller() {

		this.dbConnection = new DatabaseConnection();
		this.connection = dbConnection.GetConnection();
		negocjacjeTable.setEditable(true);
		negocjacjeTable.getColumns().addAll(idBudynkuColumn, deweloperColumn, pracownikColumn,stanColumn,typColumn,iloscColumn,powColumn,miastoColumn,ulicaColumn,nrBudynkuColumn);
		idBudynkuColumn.setCellValueFactory(new PropertyValueFactory<>("ID_BUDYNKU"));
		 deweloperColumn.setCellFactory(new PropertyValueFactory<>("DEWELOPER"));
	}

	@FXML
	public void initialize() {
		Statement stmt = null;
		String query = "select IMIE, NAZWISKO from KLIENCI";
		try {
			stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {

				this.klientSelectBox.getItems().addAll(rs.getNString("IMIE") + " " + rs.getString("NAZWISKO"));
			}
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

		fillNegotiationsTable();
	}

	@FXML
	public int getClient() {

		return 0;
	}

	@FXML
	public void getOffers() {
		int clientId = getClient();

	}

	private void fillNegotiationsTable() {
		Statement stmt = null;
		String query = "select  B.ID_BUDYNKU, D.NAZWA, (P.IMIE|| ' '|| P.NAZWISKO) AS PRACOWNIK, N.STAN,B.TYPNIERUCHOMOSCI,B.ILOSC,B.POWIERZCHNIA,B.MIASTO,B.ULICA,B.NR_BUDYNKU from BUDYNKI B, NEGOCJACJE N, PROJEKTY P, DEWELOPERZY D,PRACOWNICY P where N.ID_BUDYNKU = B.ID_BUDYNKU and N.ID_PRACOWNIKA = P.ID_PRACOWNIKA";
		try {
			stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ObservableList<Negotiation> data = FXCollections.observableArrayList();
			negocjacjeTable.getColumns().clear();
			while (rs.next()) {

				//this.preferencjeSelectBox.getItems().addAll(rs.getNString("STAN"));
				// + " " + rs.getString("NAZWA"));
				data.add(new Negotiation(rs.getString("ID_BUDYNKU"),rs.getString("NAZWA"),rs.getString("PRACOWNIK"),rs.getString("STAN"),rs.getString("TYPNIERUCHOMOSCI"),rs.getString("ILOSC"),rs.getString("POWIERZCHNIA"),rs.getString("MIASTO"),rs.getString("ULICA"),rs.getString("NR_BUDYNKU")));
//				allData.add(rs.getString("ID_BUDYNKU"));
//				allData.add(rs.getString("NAZWA"));
//				allData.add(rs.getString("PRACOWNIK"));
//				allData.add(rs.getString("STAN"));
//				allData.add(rs.getString("TYPNIERUCHOMOSCI"));
//				allData.add(rs.getString("ILOSC"));
//				allData.add(rs.getString("POWIERZCHNIA"));
//				allData.add(rs.getString("MIASTO"));
//				allData.add(rs.getString("ULICA"));
//				allData.add(rs.getString("NR_BUDYNKU"));
			}
			negocjacjeTable.setItems(data);
			negocjacjeTable.getColumns().addAll(idBudynkuColumn,deweloperColumn,pracownikColumn,stanColumn,typColumn,iloscColumn,powColumn,miastoColumn,ulicaColumn,nrBudynkuColumn);
			
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static class Negotiation{
		private final SimpleStringProperty ID_BUDYNKU;
		private final SimpleStringProperty DEWELOPER;
		private final SimpleStringProperty PRACOWNIK;
		private final SimpleStringProperty STAN;
		private final SimpleStringProperty TYPNIERUCHOMOSCI;
		private final SimpleStringProperty ILOSC;
		private final SimpleStringProperty POWIERZCHNIA;
		private final SimpleStringProperty MIASTO;
		private final SimpleStringProperty ULICA;
		private final SimpleStringProperty NR_BUDYNKU;
		
		private Negotiation(String ID_BUDYNKU, String NAZWA, String PRACOWNIK, String STAN, String TYPNIERUCHOMOSCI, String ILOSC, String POWIERZCHNIA, String MIASTO, String ULICA, String NR_BUDYNKU){
			this.ID_BUDYNKU = new SimpleStringProperty(ID_BUDYNKU);
			this.DEWELOPER = new SimpleStringProperty(NAZWA);
			this.PRACOWNIK = new SimpleStringProperty(PRACOWNIK);
			this.STAN = new SimpleStringProperty(STAN);
			this.TYPNIERUCHOMOSCI = new SimpleStringProperty(TYPNIERUCHOMOSCI);
			this.ILOSC = new SimpleStringProperty(ILOSC);
			this.POWIERZCHNIA = new SimpleStringProperty(POWIERZCHNIA);
			this.MIASTO = new SimpleStringProperty(MIASTO);
			this.ULICA= new SimpleStringProperty(ULICA);
			this.NR_BUDYNKU = new SimpleStringProperty(NR_BUDYNKU);
			
		}

		public void setID_BUDYNKU(String p) {
			ID_BUDYNKU.set(p);
		}
		public void setDEWELOPER(String p) {
			DEWELOPER.set(p);
		}
		public void setPRACOWNIK(String p) {
			PRACOWNIK.set(p);
		}
		public void setSTAN(String p) {
			STAN.set(p);
		}

		public void setTYPNIERUCHOMOSCI(String p) {
			TYPNIERUCHOMOSCI.set(p);
		}
		public void setILOSC(String p) {
			ILOSC.set(p);
		}
		
		public void setPOWIERZCHNIA(String p) {
			POWIERZCHNIA.set(p);
		}
		public void setMIASTO(String p) {
			MIASTO.set(p);
		}
		public void setULICA(String p) {
			ULICA.set(p);
		}
		public void setNR_BUDYNKU(String p) {
			NR_BUDYNKU.set(p);;
		}

		
		public String getID_BUDYNKU() {
			return ID_BUDYNKU.get();
		}

		public String getDEWELOPER() {
			return DEWELOPER.get();
		}

		public String getPRACOWNIK() {
			return PRACOWNIK.get();
		}

		public String getSTAN() {
			return STAN.get();
		}

		public String getTYPNIERUCHOMOSCI() {
			return TYPNIERUCHOMOSCI.get();
		}

		public String getILOSC() {
			return ILOSC.get();
		}

		public String getPOWIERZCHNIA() {
			return POWIERZCHNIA.get();
		}

		public String getMIASTO() {
			return MIASTO.get();
		}

		public String getULICA() {
			return ULICA.get();
		}

		public String getNR_BUDYNKU() {
			return NR_BUDYNKU.get();
		}
		
		
		
	}

}
