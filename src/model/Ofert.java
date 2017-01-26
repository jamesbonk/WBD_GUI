package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 * Created by Tomasz Motyka on 2017-01-26.
 */
public class Ofert {

    private final SimpleIntegerProperty ID_OFERTY;
    private final SimpleStringProperty STATUS;
    private final SimpleStringProperty RODZAJ;
    private final SimpleIntegerProperty CENA;
    private final SimpleStringProperty MIASTO;
    private final SimpleStringProperty ULICA;
    private final SimpleStringProperty TYP;
    private final SimpleIntegerProperty POWIERZCHNIA;
    private final SimpleIntegerProperty PIETRO;
    private final SimpleIntegerProperty POKOJE;


    public Ofert(int id, String stat, String rodz, int cena, String miasto, String ul, String typ, int pow, int pietro, int pok ) {

        this.ID_OFERTY = new SimpleIntegerProperty(id);
        this.STATUS = new SimpleStringProperty(stat);
        this.RODZAJ = new SimpleStringProperty(rodz);
        this.CENA = new SimpleIntegerProperty(cena);
        this.MIASTO = new SimpleStringProperty(miasto);
        this.ULICA = new SimpleStringProperty(ul);
        this.TYP = new SimpleStringProperty(typ);
        this.POWIERZCHNIA = new SimpleIntegerProperty(pow);
        this.PIETRO = new SimpleIntegerProperty(pietro);
        this.POKOJE = new SimpleIntegerProperty(pok);

    }

    public void setID_OFERTY(int p) {
        ID_OFERTY.set(p);
    }
    public void setSTATUS(String p) {
        STATUS.set(p);
    }
    public void setRODZAJ(String p) { RODZAJ.set(p); }
    public void setCENA(int p) {
        CENA.set(p);
    }
    public void setMIASTO(String p) { MIASTO.set(p); }
    public void setULICA(String p) { ULICA.set(p); }
    public void setTYP(String p) { TYP.set(p); }
    public void setPOWIERZCHNIA(int p) {
        POWIERZCHNIA.set(p);
    }
    public void setPIETRO(int p) {
        PIETRO.set(p);
    }
    public void setPOKOJE(int p) {
        POKOJE.set(p);
    }


    public Integer getID_OFERTY() {
        return ID_OFERTY.get();
    }
    public SimpleIntegerProperty getID_OFERTYProperty() {
        return ID_OFERTY;
    }

    public String getSTATUS() {
        return STATUS.get();
    }
    public SimpleStringProperty getSTATUSProperty() {
        return STATUS;
    }

    public String getRODZAJ() {return RODZAJ.get(); }
    public SimpleStringProperty getRODZAJProperty() {
        return RODZAJ;
    }

    public Integer getCENA() {
        return CENA.get();
    }
    public SimpleIntegerProperty getCENAProperty() {
        return CENA;
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

    public String getTYP() {
        return TYP.get();
    }
    public SimpleStringProperty getTYPProperty() {
        return TYP;
    }

    public Integer getPOWIERZCHNIA() {
        return POWIERZCHNIA.get();
    }
    public SimpleIntegerProperty getPOWIERZCHNIAProperty() {
        return POWIERZCHNIA;
    }

    public Integer getPIETRO() {
        return PIETRO.get();
    }
    public SimpleIntegerProperty getPIETROProperty() {
        return PIETRO;
    }

    public Integer getPOKOJE() {
        return POKOJE.get();
    }
    public SimpleIntegerProperty getPOKOJEProperty() {
        return POKOJE;
    }

}
