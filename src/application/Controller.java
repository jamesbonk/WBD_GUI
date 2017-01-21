package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

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


    public Controller(){

        this.dbConnection = new DatabaseConnection();
        this.connection = dbConnection.GetConnection();
    }

    @FXML
    public void initialize()
    {
        Statement stmt = null;
        String query = "select IMIE, NAZWISKO from KLIENCI";
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

    @FXML public int getClient()
    {


        return  0;
    }

    @FXML public void getOffers()
    {
        int clientId = getClient();






    }





}
