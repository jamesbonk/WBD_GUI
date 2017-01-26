package view;

import application.DatabaseConnection;
import controller.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Negotiation;

public class NegotiationsController {
	 	@FXML
	    private TableView<Negotiation> negocjacjeTable;// = new TableView<Negotiation>();
	    @FXML
	    private TableColumn<Negotiation, Integer> idBudynkuColumn;// = new TableColumn<Negotiation, String>("ID BUDYNKU");
	    @FXML
	    private TableColumn<Negotiation, String> deweloperColumn;// = new TableColumn("DEWELOPER");
	    @FXML
	    private TableColumn<Negotiation, String> pracownikColumn;// = new TableColumn("PRACOWNIK");
	    @FXML
	    private TableColumn<Negotiation, String> stanColumn;// = new TableColumn("STAN");
	    @FXML
	    private TableColumn<Negotiation, String> typColumn;// = new TableColumn("TYP");
	    @FXML
	    private TableColumn<Negotiation, Integer> iloscColumn;// = new TableColumn("ILOSC");
	    @FXML
	    private TableColumn<Negotiation, Integer> powColumn;// = new TableColumn("POW.");
	    @FXML
	    private TableColumn<Negotiation, String> miastoColumn;// = new TableColumn("MIASTO");
	    @FXML
	    private TableColumn<Negotiation, String> ulicaColumn;// = new TableColumn("ULICA");
	    @FXML
	    private TableColumn<Negotiation, Integer> nrBudynkuColumn;// = new TableColumn("NR. BUDYNKU");
	    @FXML
	    private Button btnLoadProperties;
	     
	    
	    private Main mainApp;
	    private DatabaseConnection dbConn;
	    
	    public NegotiationsController(){
	    	
	    }
	    
	    @FXML
	    private void initialize() {
	    	idBudynkuColumn.setCellValueFactory(cellData -> cellData.getValue().getID_BUDYNKUProperty().asObject());
	    	deweloperColumn.setCellValueFactory(cellData -> cellData.getValue().getDEWELOPERProperty());
	    	pracownikColumn.setCellValueFactory(cellData -> cellData.getValue().getPRACOWNIKProperty());
	    	stanColumn.setCellValueFactory(cellData -> cellData.getValue().getSTANProperty());
	    	typColumn.setCellValueFactory(cellData -> cellData.getValue().getTYPNIERUCHOMOSCIProperty());
	    	iloscColumn.setCellValueFactory(cellData -> cellData.getValue().getILOSCProperty().asObject());
	    	powColumn.setCellValueFactory(cellData -> cellData.getValue().getPOWIERZCHNIAProperty().asObject());
	    	miastoColumn.setCellValueFactory(cellData -> cellData.getValue().getMIASTOProperty());
	    	ulicaColumn.setCellValueFactory(cellData -> cellData.getValue().getULICAProperty());
	    	nrBudynkuColumn.setCellValueFactory(cellData -> cellData.getValue().getNR_BUDYNKUProperty().asObject());
	       
	    }
	    
	    public void setMain(Main mainApp) {
	        this.mainApp = mainApp;

	        // Add observable list data to the table
	        negocjacjeTable.setItems(mainApp.getNegotiationsData());

	    }
	    
	    public void setDbConnection(DatabaseConnection dbConn) {
	        this.dbConn = dbConn;
	    }
	    
	    @FXML
	    private void handleLoadProperties() {
	        for(Negotiation n : negocjacjeTable.getItems()){
	        if(n.getSTAN().equals("Zakonczono")){
	        	this.dbConn.loadProperties(n);
	        }
	        }
	        this.mainApp.negotiationsData.clear();
			this.mainApp.loadNegotiations();
	    }

}
