package function.Table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by BELYANIN on 01.02.2018.
 */
public class inputsignalTable {

    private final StringProperty pageleft;
    private final StringProperty pageleftno;
    private final StringProperty signalcodein;
    private final StringProperty telegramin;
    private final IntegerProperty abonent_id;
    private final StringProperty page_kks;
    private final StringProperty page_no;


    public inputsignalTable(String pageleft, String pageleftno, String signalcodein, String telegramin, int abonent_id, String page_kks, String page_no) {
        this.pageleft = new SimpleStringProperty(pageleft);
        this.pageleftno = new SimpleStringProperty(pageleftno);
        this.signalcodein = new SimpleStringProperty(signalcodein);
        this.telegramin = new SimpleStringProperty(telegramin);
        this.abonent_id = new SimpleIntegerProperty(abonent_id);
        this.page_kks = new SimpleStringProperty(page_kks);
        this.page_no = new SimpleStringProperty(page_no);
    }


    public String getPageleft() {
        return pageleft.get();
    }

    public String getPageleftno() {
        return pageleftno.get();
    }

    public String getSignalcodein() {
        return signalcodein.get();
    }

    public String getTelegramin() {
        return telegramin.get();
    }

    public int getAbonent_id() {
        return abonent_id.get();
    }

    public String getPage_kks() {
        return page_kks.get();
    }

    public String getPage_no() {
        return page_no.get();
    }

    public void setPageleft(String pageleft) {
        this.pageleft.set(pageleft);
    }

    public void setPageleftno(String pageleftno) {
        this.pageleftno.set(pageleftno);
    }

    public void setSignalcodein(String signalcodein) {
        this.signalcodein.set(signalcodein);
    }

    public void setTelegramin(String telegramin) {
        this.telegramin.set(telegramin);
    }

    public void setAbonent_id(int abonent_id) {
        this.abonent_id.set(abonent_id);
    }

    public void setPage_kks(String page_kks) {
        this.page_kks.set(page_kks);
    }

    public void setPage_no(String page_no) {
        this.page_no.set(page_no);
    }

    public StringProperty pageleftProperty() {
        return pageleft;
    }

    public StringProperty pageleftnoProperty() {
        return pageleftno;
    }

    public StringProperty signalcodeinProperty() {
        return signalcodein;
    }

    public IntegerProperty abonent_idProperty() {
        return abonent_id;
    }

    public StringProperty telegraminProperty() {
        return telegramin;
    }

    public StringProperty page_kksProperty() {
        return page_kks;
    }

    public StringProperty page_noProperty() {
        return page_no;
    }
}
