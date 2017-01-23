package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public  class Negotiation{
	private final SimpleIntegerProperty ID_BUDYNKU;
	private final SimpleStringProperty DEWELOPER;
	private final SimpleStringProperty PRACOWNIK;
	private final SimpleStringProperty STAN;
	private final SimpleStringProperty TYPNIERUCHOMOSCI;
	private final SimpleIntegerProperty ILOSC;
	private final SimpleIntegerProperty POWIERZCHNIA;
	private final SimpleStringProperty MIASTO;
	private final SimpleStringProperty ULICA;
	private final SimpleIntegerProperty NR_BUDYNKU;
	
	public Negotiation(int ID_BUDYNKU, String NAZWA, String PRACOWNIK, String STAN, String TYPNIERUCHOMOSCI, int ILOSC, int POWIERZCHNIA, String MIASTO, String ULICA, int NR_BUDYNKU){
		this.ID_BUDYNKU = new SimpleIntegerProperty(ID_BUDYNKU);
		this.DEWELOPER = new SimpleStringProperty(NAZWA);
		this.PRACOWNIK = new SimpleStringProperty(PRACOWNIK);
		this.STAN = new SimpleStringProperty(STAN);
		this.TYPNIERUCHOMOSCI = new SimpleStringProperty(TYPNIERUCHOMOSCI);
		this.ILOSC = new SimpleIntegerProperty(ILOSC);
		this.POWIERZCHNIA = new SimpleIntegerProperty(POWIERZCHNIA);
		this.MIASTO = new SimpleStringProperty(MIASTO);
		this.ULICA= new SimpleStringProperty(ULICA);
		this.NR_BUDYNKU = new SimpleIntegerProperty(NR_BUDYNKU);
		
	}

	public void setID_BUDYNKU(int p) {
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
	public void setILOSC(int p) {
		ILOSC.set(p);
	}
	
	public void setPOWIERZCHNIA(int p) {
		POWIERZCHNIA.set(p);
	}
	public void setMIASTO(String p) {
		MIASTO.set(p);
	}
	public void setULICA(String p) {
		ULICA.set(p);
	}
	public void setNR_BUDYNKU(int p) {
		NR_BUDYNKU.set(p);;
	}

	
	public Integer getID_BUDYNKU() {
		return ID_BUDYNKU.get();
	}
	
	public SimpleIntegerProperty getID_BUDYNKUProperty() {
		return ID_BUDYNKU;
	}

	public String getDEWELOPER() {
		return DEWELOPER.get();
	}
	public SimpleStringProperty getDEWELOPERProperty() {
		return DEWELOPER;
	}

	public String SimpleStringProperty() {
		return PRACOWNIK.get();
	}
	public SimpleStringProperty getPRACOWNIKProperty() {
		return PRACOWNIK;
	}

	public String getSTAN() {
		return STAN.get();
	}
	public SimpleStringProperty getSTANProperty() {
		return STAN;
	}

	public String getTYPNIERUCHOMOSCI() {
		return TYPNIERUCHOMOSCI.get();
	}
	public SimpleStringProperty getTYPNIERUCHOMOSCIProperty() {
		return TYPNIERUCHOMOSCI;
	}

	public Integer getILOSC() {
		return ILOSC.get();
	}
	public SimpleIntegerProperty getILOSCProperty() {
		return ILOSC;
	}

	public Integer getPOWIERZCHNIA() {
		return POWIERZCHNIA.get();
	}
	public SimpleIntegerProperty getPOWIERZCHNIAProperty() {
		return POWIERZCHNIA;
	}

	public String getMIASTO() {
		return MIASTO.get();
	}
	public SimpleStringProperty getMIASTOProperty() {
		return MIASTO;
	}
	public String getULICA() {
		return ULICA.get();
	}
	public SimpleStringProperty getULICAProperty() {
		return ULICA;
	}

	public Integer getNR_BUDYNKU() {
		return NR_BUDYNKU.get();
	}
	public SimpleIntegerProperty getNR_BUDYNKUProperty() {
		return NR_BUDYNKU;
	}
	
	
	
}