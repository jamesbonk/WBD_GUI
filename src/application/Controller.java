package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import javafx.application.Platform;
import java.sql.ResultSet;
import java.util.Comparator;

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


    public Controller(){

        this.dbConnection = new DatabaseConnection();
        this.connection = dbConnection.GetConnection();
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

}
