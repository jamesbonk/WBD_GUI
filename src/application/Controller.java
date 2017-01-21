package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.sql.PreparedStatement;
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

    @FXML private ComboBox<String> preferencjeSelectBox;
    @FXML private ChoiceBox<String> klientSelectBox;
    @FXML private CheckBox rodzaj;
    @FXML private CheckBox typ;
    @FXML private CheckBox miasto;
    @FXML private CheckBox dzielnica;
    @FXML private CheckBox pow_od;
    @FXML private CheckBox pow_do;
    @FXML private CheckBox pietro_od;
    @FXML private CheckBox pietro_do;
    @FXML private CheckBox pokoje_od;
    @FXML private CheckBox pokoje_do;


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
    public void initialize()
    {
        Statement stmt = null;
        String query = "select IMIE, NAZWISKO from KLIENCI";
        DisablePreferences();
        try {
            stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){

                this.klientSelectBox.getItems().addAll(rs.getNString("IMIE") + " " + rs.getString("NAZWISKO"));
            }
        }
        catch(java.sql.SQLException e){
            e.printStackTrace();
        }

        fillNegotiationsTable();
    }

    public int getClient()
    {
        String client = this.klientSelectBox.getValue();
        int id = 0;
        String query = "select ID_KLIENTA from KLIENCI WHERE IMIE = ? AND NAZWISKO = ?";
        PreparedStatement getClient = null;
        try {
            getClient = this.connection.prepareStatement(query);
            getClient.setString(1, client.split(" ",2)[0]);
            getClient.setString(2, client.split(" ",2)[1]);
            ResultSet rs = getClient.executeQuery();
            while (rs.next()){
                id = rs.getInt("ID_KLIENTA");
            }
        }
        catch(java.sql.SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    @FXML public void showPreferences(){

        String query = "select NAZWA from WARTOSCI_PREFERENCJI w join PREFERENCJE p on p.ID_PREFERENCJI = w.ID_PREFERENCJI WHERE ID_KLIENTA = ?";
        PreparedStatement getPreferences = null;
        int id = getClient();
        DisablePreferences();

        try {
            getPreferences = this.connection.prepareStatement(query);
            getPreferences.setInt(1, id);
            ResultSet rs = getPreferences.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString("NAZWA"));
                String a = rs.getString("NAZWA");
                switch (a) {
                    case "RODZAJ_OFERTY":
                        this.rodzaj.setDisable(false);
                        break;
                    case "TYP_NIERUCHOMOSCI":
                        this.typ.setDisable(false);
                        break;
                    case "MIASTO":
                        this.miasto.setDisable(false);
                        break;
                    case "NAZWA_DZIELNICY":
                        this.dzielnica.setDisable(false);
                        break;
                    case "POWIERZCHNIA_OD":
                        this.pow_od.setDisable(false);
                        break;
                    case "POWIERZCHNIA_DO":
                        this.pow_do.setDisable(false);
                        break;
                    case "PIETRO_OD":
                        this.pietro_od.setDisable(false);
                        break;
                    case "PIETRO_DO":
                        this.pietro_do.setDisable(false);
                        break;
                    case "POKOJE_OD":
                        this.pokoje_od.setDisable(false);
                        break;
                    case "POKOJE_DO":
                        this.pokoje_do.setDisable(false);
                        break;
                }
            }
        }
        catch(java.sql.SQLException e){
            e.printStackTrace();
        }
    }

    public void DisablePreferences()
    {
        this.rodzaj.setDisable(true);
        this.typ.setDisable(true);
        this.miasto.setDisable(true);
        this.dzielnica.setDisable(true);
        this.pow_od.setDisable(true);
        this.pow_do.setDisable(true);
        this.pietro_od.setDisable(true);
        this.pietro_do.setDisable(true);
        this.pokoje_od.setDisable(true);
        this.pokoje_do.setDisable(true);
    }

    @FXML public void getOffers()
    {
        int clientId = getClient();
        String query = "SELECT o.ID_OFERTY, o.STATUS, o.RODZAJ, o.CENA, n.MIASTO, n.ULICA, n.TYP, n.POWIERZCHNIA, n.PIETRO, n.POKOJE FROM OFERTY o JOIN NIERUCHOMOSCI n ON o.ID_NIERUCHOMOSCI = n.ID_NIERUCHOMOSCI WHERE ";
        PreparedStatement getOffers = null;
        ResultSet rs = null;
        int a = 0;
        if(this.rodzaj.isSelected())
        {
            a++;
            if (a != 1)
                query = query + " AND ";
            query = query + "o.RODZAJ = (SELECT WARTOSC FROM WARTOSCI_PREFERENCJI WHERE ID_PREFERENCJI = (SELECT ID_PREFERENCJI FROM PREFERENCJE WHERE NAZWA = 'RODZAJ_OFERTY') AND ID_KLIENTA = ?)";
        }
        if(this.typ.isSelected()) {
            a++;
            if (a != 1)
                query = query + " AND ";
            query = query + "n.TYP = (SELECT WARTOSC FROM WARTOSCI_PREFERENCJI WHERE ID_PREFERENCJI = (SELECT ID_PREFERENCJI FROM PREFERENCJE WHERE NAZWA = 'TYP_NIERUCHOMOSCI') AND ID_KLIENTA = ?)";
        }
        if(this.miasto.isSelected()) {
            a++;
            if (a != 1)
                query = query + " AND ";
            query = query + "n.MIASTO = (SELECT WARTOSC FROM WARTOSCI_PREFERENCJI WHERE ID_PREFERENCJI = (SELECT ID_PREFERENCJI FROM PREFERENCJE WHERE NAZWA = 'MIASTO') AND ID_KLIENTA = ?)";
        }
        /*if(this.dzielnica.isSelected())
            query = query + "n.DZIELNICA = (SELECT WARTOSC FROM WARTOSCI_PREFERENCJI WHERE ID_PREFERENCJI = (SELECT ID_PREFERENCJI FROM PREFERENCJE WHERE NAZWA = 'DZIELNICA') AND ID_KLIENTA = ?)";
        if(this.pow_od.isSelected() && this.pow_od.isSelected())
            query = query + "n.POWIERZCHNIA= (SELECT WARTOSC FROM WARTOSCI_PREFERENCJI WHERE ID_PREFERENCJI = (SELECT ID_PREFERENCJI FROM PREFERENCJE WHERE NAZWA = 'MIASTO') AND ID_KLIENTA = ?)";
        if(this.pietro_od.isSelected() && this.pietro_do.isSelected())
            query = query + "n.PIETRO BETWEEN \n" +
                    "(SELECT WARTOSC FROM WARTOSCI_PREFERENCJI WHERE ID_PREFERENCJI = \n" +
                    "(SELECT ID_PREFERENCJI FROM PREFERENCJE WHERE NAZWA = 'PIETRO_OD') AND ID_KLIENTA = ?) AND  \n" +
                    "(SELECT WARTOSC FROM WARTOSCI_PREFERENCJI WHERE ID_PREFERENCJI = \n" +
                    "(SELECT ID_PREFERENCJI FROM PREFERENCJE WHERE NAZWA = 'PIETRO_DO') AND ID_KLIENTA = ?)";
        if(this.pokoje_od.isSelected() && this.pokoje_do.isSelected())
            query = query + "o.RODZAJ = (SELECT WARTOSC FROM WARTOSCI_PREFERENCJI WHERE ID_PREFERENCJI = (SELECT ID_PREFERENCJI FROM PREFERENCJE WHERE NAZWA = 'MIASTO') AND ID_KLIENTA = ?)";
        */
        System.out.println(query);
        try
        {
            getOffers = this.connection.prepareStatement(query);
            for (int i = 1; i <= a; i++){
                getOffers.setInt(i, clientId);
                System.out.println(i) ;
            }
            rs = getOffers.executeQuery();

            while(rs.next())
            {

                // TO DO.... ZCZYTYWANIE Z rs WIERSZY I WYSWIETLENIE W TABELI

            }
        }
        catch(java.sql.SQLException e)
        {
            e.printStackTrace();
        }

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
